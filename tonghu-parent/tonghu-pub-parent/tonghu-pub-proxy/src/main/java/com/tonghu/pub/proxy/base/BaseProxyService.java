package com.tonghu.pub.proxy.base;

import com.tonghu.pub.proxy.config.OuterUrl;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import org.apache.commons.codec.Charsets;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Http调用代理基类
 */
public class BaseProxyService {

	@Autowired
	protected OuterUrl outerUrl;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseProxyService.class);
	
	protected static final String YMD_HMS = "yyyy-MM-dd/HH:mm:ss";

	private CloseableHttpClient httpclient;
	
	/**
	 * 默认超时时间 (In milliseconds)
	 */
	private int defaultTimeout;

	public void setDefaultTimeout(int defaultTimeout) {
		this.defaultTimeout = defaultTimeout;
	}

	private int maxConnections;

	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}

	private int retries;

	public void setRetries(int retries) {
		this.retries = retries;
	}

	private boolean keepAlive;

	public void setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
	}

	@PostConstruct
	private void init() {
		try {
			httpclient = HttpClients.custom().setRetryHandler(
					new DefaultHttpRequestRetryHandler(retries, false))
					.setMaxConnTotal(maxConnections).setMaxConnPerRoute(maxConnections).build();
		} catch (Exception e) {
			LOGGER.error("BaseProxyService init has exception e={}", e);
		}
	}

	@PreDestroy
	private void destroy() {
		if (httpclient != null) {
			try {
				httpclient.close();
			} catch (IOException e) {
				LOGGER.error("BaseProxyService destroy has exception e={}", e);
			}
		}
	}


	/**
	 * GET 方式调用的入口
	 * @param baseUrl
	 * @param props
	 * @param headerMap
	 * @return
	 * @throws URISyntaxException
	 */
	public String fetchContentFromUrl(String baseUrl, Map<String, String> props, Map<String, String> headerMap)
			 throws URISyntaxException {
		return fetchContentFromUrl(baseUrl, props, headerMap, defaultTimeout);
	}

	/**
	 * GET 方式调用的入口
	 * @param baseUrl
	 * @param props
	 * @param headerMap
	 * @param timeout
	 * @return
	 * @throws URISyntaxException
	 */
	public String fetchContentFromUrl(String baseUrl, Map<String, String> props, Map<String, String> headerMap,
			int timeout) throws URISyntaxException {
		return fetchContentFromUrl(baseUrl, props, headerMap, timeout, null);
	}

	/**
	 * GET 方式调用的入口
	 * @param baseUrl
	 * @param props
	 * @param headerMap
	 * @param charset
	 * @return
	 * @throws URISyntaxException
	 */
	public String fetchContentFromUrl(String baseUrl, Map<String, String> props, Map<String, String> headerMap,
			Charset charset) throws URISyntaxException {
		return fetchContentFromUrl(baseUrl, props, headerMap, defaultTimeout, charset);
	}

	/**
	 * 用post方式从url获取返回信息
	 * @param baseUrl http地址
	 * @param props get参数
	 * @return String
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public String fetchContentFromUrlPost(String baseUrl, Object props, Map<String, String> headerMap)
			throws IOException, URISyntaxException {
		return fetchContentFromUrlPost(baseUrl, props, headerMap, defaultTimeout);
	}

	/**
	 * POST 方式调用的入口
	 * @param baseUrl
	 * @param props
	 * @param headerMap
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public String fetchContentFromUrlPostBodyString(String baseUrl, String props, Map<String, String> headerMap)
			throws IOException, URISyntaxException {
		return fetchContentFromUrlPostBodyString(baseUrl, props, headerMap, defaultTimeout);
	}

	/**
	 * Post方式从url获取返回信息
	 * @param baseUrl  http地址
	 * @param props get参数
	 * @param timeout 超时时间
	 * @return String
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public String fetchContentFromUrlPost(String baseUrl, Object props, Map<String, String> headerMap, int timeout)
			throws IOException, URISyntaxException {
		Gson gson = new Gson();
		return fetchContentFromUrlPostBodyString(baseUrl, gson.toJson(props),
				headerMap, timeout);
	}

	/**
	 * GET 方式调用接口
	 * @param baseUrl
	 * @param props
	 * @param headerMap
	 * @param timeout
	 * @param charset
	 * @return String
	 * @throws URISyntaxException
	 */
	protected String fetchContentFromUrl(String baseUrl, Map<String, String> props, Map<String, String> headerMap,
			int timeout, Charset charset)
			throws URISyntaxException {

		Preconditions.checkNotNull(baseUrl);
		long start = System.currentTimeMillis();
		HttpGet getRequest = new HttpGet(buildUri(baseUrl, props));

		// 设置 header
		if (MapUtils.isNotEmpty(headerMap)) {
			if (headerMap.containsKey(HTTP.CONTENT_TYPE)) {
				getRequest.addHeader(HTTP.CONTENT_TYPE, headerMap.get(HTTP.CONTENT_TYPE));

			}
			if (headerMap.containsKey("Access-Token")) {
				getRequest.addHeader("Access-Token", headerMap.get("Access-Token"));
			}
		}

		if (!keepAlive) {
			// 使用短连接
			getRequest.addHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
		}

		getRequest.setConfig(RequestConfig.custom().setSocketTimeout(timeout)
				.setConnectTimeout(timeout).setConnectionRequestTimeout(timeout).build());

		String body = null;
		try (CloseableHttpResponse response = httpclient.execute(getRequest)) {
			if (response != null) {
				int statusCode = response.getStatusLine().getStatusCode();
				LOGGER.info("HttpRequestInfo=[{}] 接口返回状态码=[{}]", getRequest.getURI(), statusCode);

				if (statusCode >= 200 && statusCode < 400) {
					body = toString(response.getEntity(), charset);
				} else {
					LOGGER.error("HttpRequestInfo=[{}] 接口返回状态码不在正常范围内，调用失败", getRequest.getURI());
				}
			}
		} catch (Exception e) {
			LOGGER.error("HttpRequestInfo=[{}] 接口调用出现异常 e=[{}]", getRequest.getURI(), e);
		} finally {
			getRequest.releaseConnection();
		}

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("HttpRequestInfo=[{}] UseTime=[{}ms] Prams=[{}]",
					getRequest.getURI(), (System.currentTimeMillis() - start), props);
		}

		return body;
	}
	
	/**
	 * Post方式从url获取返回信息
	 * @param baseUrl http地址
	 * @param props get参数
	 * @param timeout 超时时间
	 * @return String
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	protected String fetchContentFromUrlPostBodyString(
			String baseUrl, String props, Map<String, String> headerMap, int timeout)
			throws IOException, URISyntaxException {
		LOGGER.info("Request url: {} with prams: {}", baseUrl, props);

		Preconditions.checkNotNull(baseUrl);

		long start = System.currentTimeMillis();

		HttpPost postRequest = new HttpPost(baseUrl);
		// 设置 header
		if (MapUtils.isNotEmpty(headerMap)) {
			if (headerMap.containsKey(HTTP.CONTENT_TYPE)) {
				postRequest.addHeader(HTTP.CONTENT_TYPE, headerMap.get(HTTP.CONTENT_TYPE));

			}
			if (headerMap.containsKey("Access-Token")) {
				postRequest.addHeader("Access-Token", headerMap.get("Access-Token"));
			}
		}
		StringEntity entity = new StringEntity(props, Charset.forName("UTF-8"));
		entity.setContentType("application/json");
		postRequest.setEntity(entity);
		if (!keepAlive) {
			// 使用短连接
			postRequest.addHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
		}

		postRequest.setConfig(RequestConfig.custom().setSocketTimeout(timeout).
				setConnectTimeout(timeout).setConnectionRequestTimeout(timeout).build());

		String body = null;
		String requestDateTime = date2str(new Date(), YMD_HMS);
		long startTime = System.currentTimeMillis();
		try (CloseableHttpResponse response = httpclient.execute(postRequest)) {
			if (response != null) {
				int statusCode = response.getStatusLine().getStatusCode();
				LOGGER.info("HttpRequestInfo=[{}] 接口返回状态码=[{}]", postRequest.getURI(), statusCode);

				if (statusCode >= 200 && statusCode < 400) {
					body = EntityUtils.toString(response.getEntity(), Charsets.UTF_8);
				} else {
					LOGGER.error("HttpRequestInfo=[{}] 接口返回状态码不在正常范围内，调用失败", postRequest.getURI());
				}
			}
		} catch (Exception e) {
			LOGGER.error("HttpRequestInfo=[{}] 接口调用出现异常 e=[{}]", postRequest.getURI(), e);
		} finally {
			postRequest.releaseConnection();
		}

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("HttpRequestInfo=[{}] UseTime=[{}ms] Prams=[{}]",
					postRequest.getURI(), (System.currentTimeMillis() - start), props);
		}

		return body;
	}
	
	/**
	 * 格式化请求参数
	 * 
	 * @param baseUrl
	 * @param props
	 * @return
	 * @throws URISyntaxException
	 * @throws ParseException
	 * @throws IOException
	 */
	private URI buildUri(String baseUrl, Map<String, String> props) throws URISyntaxException {
		URIBuilder uriBuilder = new URIBuilder(baseUrl);
		if (MapUtils.isNotEmpty(props)) {
			for (Entry<String, String> prop : props.entrySet()) {
				uriBuilder.addParameter(prop.getKey(), prop.getValue());
			}
		}
		return uriBuilder.build();
	}

	private static String toString(final HttpEntity entity, final Charset defaultCharset)
			throws IOException, ParseException {
		Args.notNull(entity, "Entity");
		try (final InputStream inStream = entity.getContent()) {

			if (inStream == null) {
				return null;
			}
			try {
				Args.check(entity.getContentLength() <= Integer.MAX_VALUE,
						"HTTP entity too large to be buffered in memory");
				int i = (int) entity.getContentLength();
				if (i < 0) {
					i = 4096;
				}
				Charset charset = null;
				if (defaultCharset != null) {
					charset = defaultCharset;
				} else {
					try {
						final ContentType contentType = ContentType.get(entity);
						if (contentType != null) {
							charset = contentType.getCharset();
						}
					} catch (final UnsupportedCharsetException ex) {
						throw new UnsupportedEncodingException(ex.getMessage());
					}
				}
				if (charset == null) {
					charset = Charsets.UTF_8;
				}
				try (final Reader reader = new InputStreamReader(inStream, charset)) {

					final CharArrayBuffer buffer = new CharArrayBuffer(i);
					final char[] tmp = new char[1024];
					int l;
					while ((l = reader.read(tmp)) != -1) {
						buffer.append(tmp, 0, l);
					}
					return buffer.toString();
				}
			} finally {
				inStream.close();
			}

		}
	}
	

	protected static String date2str(Date date, String format) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
		return sdf.format(date);
	}

	/**
	 * @Description: GET方式调用接口，不用BuildUri方法，可以让参数中的逗号等标点符号保持原样
	 * @param baseUrl
	 * @param props
	 * @throws URISyntaxException
	 * @return String
	 * @author liangyongjian
	 * @date 2017年1月23日 上午10:42:47
	 * @version V1.0
	 */
	protected String fetchContentFromUrlWithOutBuildUri(
			String baseUrl, Map<String, String> props) throws URISyntaxException {
		return fetchContentFromUrlWithOutBuildUri(baseUrl, props, defaultTimeout, null);
	}
	
	protected String fetchContentFromUrlWithOutBuildUri(
			String baseUrl, Map<String, String> props, int timeout ,Charset charset)
			throws URISyntaxException {
		Preconditions.checkNotNull(baseUrl);
		String status = "false";
		long start = System.currentTimeMillis();
		HttpGet getRequest = new HttpGet(packageGetUrl(baseUrl, props));
		
		if (!keepAlive) {
			// 使用短连接
			getRequest.addHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
		}

		getRequest.setConfig(RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
						.setConnectionRequestTimeout(timeout).build());

		String body = null;
		String requestDateTime = date2str(new Date(), YMD_HMS);
		long startTime = System.currentTimeMillis();
		try (CloseableHttpResponse response = httpclient.execute(getRequest)) {
			if (response != null) {
				int statusCode = response.getStatusLine().getStatusCode();
				if(statusCode != 200){

				}
				if (statusCode >= 200 && statusCode < 400) {
					body = toString(response.getEntity(), charset);
					status = "true";
				} else {
					try {

					} catch (Exception e) {
					}

				}
			}
		} catch (Exception e) {

			
		} finally {
			getRequest.releaseConnection();
		}

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("HttpRequestInfo=[{}] UseTime=[{}ms] Prams=[{}]",
					getRequest.getURI(), (System.currentTimeMillis() - start), props);
		}

		return body;
	}
	
	/**
	 * @Description: 组装GET方式的URL
	 * @param url
	 * @param params
	 * @return String
	 * @author liangyongjian
	 * @date 2017年1月23日 上午10:47:39
	 * @version V1.0
	 */
	private static String packageGetUrl(String url, Map<String, String> params) {
		String paramsUrl = "";
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if (url.indexOf("?") == -1) {
				paramsUrl = "?" + entry.getKey() + "=" + entry.getValue();
			} else {
				paramsUrl += "&" + entry.getKey() + "=" + entry.getValue();
			}
		}
		return url + paramsUrl;
	}
}
