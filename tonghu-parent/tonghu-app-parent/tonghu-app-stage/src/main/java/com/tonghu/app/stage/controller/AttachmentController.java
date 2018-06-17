package com.tonghu.app.stage.controller;

import com.tonghu.app.stage.common.service.AttachmentService;
import com.tonghu.app.stage.config.InputConfig;
import com.tonghu.app.stage.config.WebConfig;
import com.tonghu.app.stage.service.SystemConstant;
import com.tonghu.pub.common.constant.Constant;
import com.tonghu.pub.common.utils.*;
import com.tonghu.pub.common.web.PubModelMap;
import com.tonghu.pub.model.common.po.Attachment;
import com.tonghu.pub.model.security.po.Users;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Description: 附件上传 下载  MVC控制层web入口
 * @author liangyongjian
 * @Version V1.0
 * @date 2017-10-13 下午05:19:03
 */
@Controller
public class AttachmentController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentController.class);

	@Resource
	private WebConfig webConfig;

	@Resource
	private InputConfig inputConfig;
	
	@Resource
	private SystemConstant systemConstant;

	@Resource
	private AttachmentService attachmentService;
	
	/**
	 * 上传附件
	 * @param multipartRequest
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(method = {RequestMethod.POST}, value = "/fileUpload.do")
    public void fileUpload(MultipartHttpServletRequest multipartRequest,
		    HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		PubModelMap modelMap = new PubModelMap(request);
		Users user = (Users)request.getSession().getAttribute("CURRENT_USER_INFO");
		if (multipartRequest == null || multipartRequest.getFileNames() == null || user == null) {
			LOGGER.error("上传附件操作异常，附件为空或者用户信息为空");
			modelMap.put("status", "exception");
			modelMap.put("data", "上传附件操作异常，附件为空，请重新上传！");
		} else {
			try {
				LOGGER.info("开始上传附件");
				for (Iterator<String> it = multipartRequest.getFileNames(); it.hasNext();) {
			        String key = (String) it.next();
			        MultipartFile file = multipartRequest.getFile(key);
			        //校验附件名称
			        if (file.getOriginalFilename().length() > 0) {
			        	String originalFilename = file.getOriginalFilename();
			        	String fileExtName = FileUtils.getFileExtName(originalFilename, webConfig.getFileSuffix());
			        	//校验附件后缀名
			        	if (fileExtName != null && !fileExtName.equals("")) {
			        		//校验附件大小
			        		if (this.checkImportFileSize(file)) {
					        	
					        	String path = webConfig.getAppBasePath() + webConfig.getUploadPath();
						        FolderUtils.createFolder(path);
						        LOGGER.info("附件上传路径：{}", path);
						        
						        LOGGER.info("原文件名称：{}", originalFilename);
						        
						        String fileName = CreateGUID.getGUID() + "." + fileExtName;
						        LOGGER.info("新文件名称：{}", fileName);
						        File targetFile = new File(path, fileName);  
						        if(!targetFile.exists()){  
						            targetFile.mkdirs();  
						        }
						        //保存  
						        file.transferTo(targetFile);
						        //附件信息保存到数据库
						        Attachment attachment = new Attachment();
						        attachment.setFileName(MyStringUtils.cutStr(fileName,
										inputConfig.getFileNameLength()));

						        attachment.setFilePath(MyStringUtils.cutStr(
						        		webConfig.getUploadPath(), inputConfig.getFilePathLength()));

						        attachment.setFileType(MyStringUtils.cutStr(file.getContentType(),
										inputConfig.getFileTypeLength()));

						        attachment.setFileSize(file.getSize());

						        attachment.setOriginalFileName(MyStringUtils.cutStr(originalFilename,
										inputConfig.getOriginalFileNameLength()));

						        attachment.setCreateUserId(user.getCreateUserId());

						        attachmentService.addNewAttachment(attachment);
						        modelMap.put("status", "success");
						        modelMap.put("data", attachment.getId());
						        modelMap.put("fileName", originalFilename);
						        modelMap.put("fileExtName", fileExtName);
								modelMap.put("fileUrl", fileExtName);
					        } else {
					        	LOGGER.error("所上传的附件大小超过限制：{}M，附件大小为：{}M",
										systemConstant.getMaxImportFileSize(),
					        			file.getSize() / 1024 / 1024);
								modelMap.put("status", "error");
								modelMap.put("data", "所上传的附件大小超过限制：" +
										systemConstant.getMaxImportFileSize() + "M");
					        } 
			        	} else {
			        		LOGGER.error("附件的后缀名不在允许范围之内：{}", originalFilename);
							modelMap.put("status", "error");
							modelMap.put("data", "附件的后缀名不在允许范围之内！");
			        	}
			        	
			        } else {
						LOGGER.error("所上传的附件原始名称为空");
						modelMap.put("status", "error");
						modelMap.put("data", "所上传的附件原始名称为空，请重新上传");
			        }
			    }
			} catch(Exception e) {
				LOGGER.error("上传附件的操作出现异常：{}", e);
				modelMap.put("status", "exception");
				modelMap.put("data", "系统异常，请刷新页面重新请求！");
			}
		}
		response.setCharacterEncoding(Constant.DEFAULT_ENCODE.STRING_UTF8);
		response.setContentType(Constant.DEFAULT_CONTENT_TYPE.CONTENT_TYPE_UTF8);
		response.getWriter().write(this.transferMapToJson(modelMap.getModelMap()));
        return ;
    }

	/**
	 * 上传图片
	 * @param multipartRequest
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(method = {RequestMethod.POST}, value = "/fileImageUpload.do")
	public void fileImageUpload(MultipartHttpServletRequest multipartRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PubModelMap modelMap = new PubModelMap(request);
		Users user = (Users)request.getSession().getAttribute("CURRENT_USER_INFO");
		if (multipartRequest == null || multipartRequest.getFileNames() == null || user == null) {
			LOGGER.error("上传附件操作异常，附件为空或者用户信息为空");
			modelMap.put("status", "exception");
			modelMap.put("data", "上传附件操作异常，附件为空，请重新上传！");
		} else {
			try {
				LOGGER.info("开始上传图片");
				for (Iterator<String> it = multipartRequest.getFileNames(); it.hasNext();) {
					String key = (String) it.next();
					MultipartFile file = multipartRequest.getFile(key);
					//校验附件名称
					if (file.getOriginalFilename().length() > 0) {
						String originalFilename = file.getOriginalFilename();
						String fileExtName = FileUtils.getFileExtName(originalFilename,
								webConfig.getImageSuffix());
						//校验附件后缀名
						if (fileExtName != null && !fileExtName.equals("")) {
							//校验附件大小
							if (this.checkUploadImageSize(file)) {

								String todayYMDStr = DateTimeUtils.getDateTime(DateTimeUtils.YMD);

								String path = webConfig.getAppBasePath() + webConfig.getImagePath() +
										DateTimeUtils.getDateTime(DateTimeUtils.YMD);
								FolderUtils.createFolder(path);
								LOGGER.info("图片上传路径：{}", path);

								LOGGER.info("原图片名称：{}", originalFilename);

								String fileName = CreateGUID.getGUID() + "." + fileExtName;
								LOGGER.info("新图片名称：{}", fileName);
								File targetFile = new File(path, fileName);
								if(!targetFile.exists()){
									targetFile.createNewFile();
								}
								//保存
								file.transferTo(targetFile);
								//附件信息保存到数据库
								Attachment attachment = new Attachment();
								attachment.setFileName(MyStringUtils.cutStr(fileName,
										inputConfig.getFileNameLength()));
								attachment.setFilePath(MyStringUtils.cutStr(
										webConfig.getImagePath() + todayYMDStr + "/",
										inputConfig.getFilePathLength()));
								attachment.setFileType(MyStringUtils.cutStr(file.getContentType(),
										inputConfig.getFileTypeLength()));
								attachment.setFileSize(file.getSize());
								attachment.setImgMeasure(FileUtils.getImgMeasure(targetFile.getPath()));
								attachment.setOriginalFileName(MyStringUtils.cutStr(originalFilename,
										inputConfig.getOriginalFileNameLength()));
								attachment.setCreateUserId(user.getCreateUserId());
								attachmentService.addNewAttachment(attachment);
								modelMap.put("status", "success");
								modelMap.put("data", attachment.getId());
								modelMap.put("imgOriginalName", originalFilename);
								if (!StringUtils.isEmpty(webConfig.getFileServerName())) {
									modelMap.put("imgRelativeUrl", "/" + webConfig.getFileServerName() +
											webConfig.getImagePath() + todayYMDStr + "/" + fileName);
								} else {
									modelMap.put("imgRelativeUrl",
											webConfig.getImagePath().substring(
													1, webConfig.getImagePath().length())
													+ todayYMDStr + "/" + fileName);
								}

								modelMap.put("imgMeasure", attachment.getImgMeasure());
							} else {
								LOGGER.error("所上传的图片大小超过限制：{}M，附件大小为：{}M",
										systemConstant.getMaxUploadImageSize(),
										file.getSize() / 1024 / 1024);
								modelMap.put("status", "error");
								modelMap.put("data", "所上传的图片大小超过限制：" +
										systemConstant.getMaxUploadImageSize() + "M");
							}
						} else {
							LOGGER.error("图片的后缀名不在允许范围之内：{}", originalFilename);
							modelMap.put("status", "error");
							modelMap.put("data", "图片的后缀名不在允许范围之内！");
						}

					} else {
						LOGGER.error("所上传的图片原始名称为空");
						modelMap.put("status", "error");
						modelMap.put("data", "所上传的图片原始名称为空，请重新上传");
					}
				}
			} catch(Exception e) {
				LOGGER.error("上传图片的操作出现异常：{}", e);
				modelMap.put("status", "exception");
				modelMap.put("data", "系统异常，请刷新页面重新请求！");
			}
		}
		response.setCharacterEncoding(Constant.DEFAULT_ENCODE.STRING_UTF8);
		response.setContentType(Constant.DEFAULT_CONTENT_TYPE.CONTENT_TYPE_UTF8);
		response.getWriter().write(this.transferMapToJson(modelMap.getModelMap()));
		return ;
	}


	/**
	 * 生成json字符串
	 * @param map
	 * @return String
	 * @throws
	 * @author liangyongjian
	 * @date 2017-10-08 上午12:48:28
	 * @version V1.0
	 */
	private String transferMapToJson(Map<String, Object> map){
		JSONObject jsonObject  = new JSONObject();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			jsonObject.put(entry.getKey(), entry.getValue());
		}
		return jsonObject.toString();
	}

	/**
	 * 判断附件大小是否超过限制
	 * @param file
	 * @return boolean
	 * @throws
	 * @author liangyongjian
	 * @date 2017-10-08 上午12:29:24
	 * @version V1.0
	 */
	private boolean checkImportFileSize(MultipartFile file) {
		//获取表单项中的文件信息
		if(Integer.parseInt(String.valueOf(file.getSize())) > webConfig.getMaxFileSize() * 1024 * 1024)
			return false;
		return true;
	}

	private boolean checkUploadImageSize(MultipartFile file) {
		//获取表单项中的文件信息
		if(Integer.parseInt(String.valueOf(file.getSize())) > systemConstant.getMaxUploadImageSize() * 1024 * 1024)
			return false;
		return true;
	}
	
	
	/**
	 * 下载用户所上传的附件
	 * @param request
	 * @param response
	 * @return void
	 * @throws
	 * @author liangyongjian
	 * @date 2017-11-21 上午01:06:13
	 * @version V1.0
	 */
	@RequestMapping(method = {RequestMethod.POST}, value = "/fileDownload.do")
	public void downloadAttachment(Long fileId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OutputStream os = response.getOutputStream();
		try {
			if (fileId == null) {
				throw new Exception("传递来的附件参数有误！");
			}
			Attachment attachment = attachmentService.getAttachmentById(fileId);
			if(attachment == null) {
				throw new Exception("附件被删除或者参数有误！");
			}
			//获取文件信息
			File downFile = new File(webConfig.getAppBasePath() +
					attachment.getFilePath() + attachment.getFileName());
			String originalFileName = attachment.getOriginalFileName();
			//判断文件是否存在
			if(!downFile.exists()) {
				originalFileName = "您所下载的文件不存在.txt";
				downFile = new File(webConfig.getAppBasePath() +
						attachment.getFilePath() + originalFileName);
			}
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition","attachment; filename="
					+ new String(originalFileName.getBytes(),"ISO_8859_1") + "");
			InputStream fis = new BufferedInputStream(new FileInputStream(downFile));
			response.setContentLength(fis.available());
	        byte[] buffer = new byte[fis.available()];
	        fis.read(buffer);
	        fis.close();
	        os.write(buffer);
	        os.flush();
		} catch(Exception e) {
			Map<String,Object> message = new HashMap<String,Object>(2);
			message.put("success", false);
			message.put("msg", e.getMessage());
			JSONObject jsonObject = JSONObject.fromObject(message);
			response.setCharacterEncoding(Constant.DEFAULT_ENCODE.STRING_UTF8);
			response.setContentType(Constant.DEFAULT_CONTENT_TYPE.CONTENT_TYPE_UTF8);
			os.write(jsonObject.toString().getBytes(Constant.DEFAULT_ENCODE.STRING_UTF8));
		} finally {
			if(os == null) os=response.getOutputStream();
			os.close();
		}
	}

//	/**
//	 * 下载导出文件
//	 * @param request
//	 * @param response
//	 * @return void
//	 * @throws
//	 * @author liangyongjian
//	 * @date 2017-10-08 上午01:06:13
//	 * @version V1.0
//	 */
//	@RequestMapping(method = {RequestMethod.POST}, value = "/exportFileDownload.do")
//	public void downLoadExportFile(String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		OutputStream os = response.getOutputStream();
//		try {
//			if (fileName == null) {
//				throw new Exception("传递来的附件参数有误！");
//			}
//			String filePath = MappingConstantConfig.getValue("APP_BASE_PATH") + MappingConstantConfig.getValue("EXPORT_PATH") + fileName;
//			LOGGER.debug("导出文件的路径为：{}", filePath);
//
//			//获取文件信息
//			File downFile = new File(filePath);
//			//判断文件是否存在
//			if(!downFile.exists()) {
//				fileName = "您所下载的文件不存在.txt";
//				downFile = new File(MappingConstantConfig.getValue("APP_BASE_PATH") +
//						MappingConstantConfig.getValue("EXPORT_PATH") + fileName);
//			}
//			response.setContentType("application/force-download");
//			response.setHeader("Content-Disposition","attachment; filename="
//					+ new String(fileName.getBytes(), "ISO_8859_1") + "");
//			InputStream fis = new BufferedInputStream(new FileInputStream(downFile));
//			response.setContentLength(fis.available());
//	        byte[] buffer = new byte[fis.available()];
//	        fis.read(buffer);
//	        fis.close();
//	        os.write(buffer);
//	        os.flush();
//		} catch(Exception e) {
//			Map<String,Object> message = new HashMap<String,Object>(2);
//			message.put("success", false);
//			message.put("msg", e.getMessage());
//			JSONObject jsonObject = JSONObject.fromObject(message);
//			response.setCharacterEncoding(Constant.DEFAULT_ENCODE.STRING_UTF8);
//			response.setContentType(Constant.DEFAULT_CONTENT_TYPE.CONTENT_TYPE_UTF8);
//			os.write(jsonObject.toString().getBytes(Constant.DEFAULT_ENCODE.STRING_UTF8));
//		} finally {
//			if(os == null) os=response.getOutputStream();
//			os.close();
//		}
//	}
	


//	/**
//	 * 下载图片
//	 * @param request
//	 * @param response
//	 * @return void
//	 * @throws
//	 * @author liangyongjian
//	 * @date 2017-10-08 上午01:06:13
//	 * @version V1.0
//	 */
//	@RequestMapping(method = {RequestMethod.GET}, value = "/downPicFile.do")
//	public void downPicFile(String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		try {
//			if (fileName == null) {
//				throw new Exception("传递来的附件参数有误！");
//			}
//			String filePath = MappingConstantConfig.getValue("APP_BASE_PATH") + MappingConstantConfig.getValue("IMAGE_PATH") + fileName;
//			LOGGER.debug("导出文件的路径为：{}", filePath);
//
//			//获取文件信息
//			File downFile = new File(filePath);
//			//判断文件是否存在
//			if(!downFile.exists()) {
//				fileName = "您所下载的文件不存在.txt";
//				downFile = new File(MappingConstantConfig.getValue("APP_BASE_PATH") +
//						MappingConstantConfig.getValue("IMAGE_PATH") + fileName);
//			}
//	        response.setContentType("text/image");
//			response.addHeader("Content-Disposition", "inline; filename=\"" + fileName + "\";");
//			response.setContentLength((int)downFile.length());
//			response.setCharacterEncoding("UTF-8");
//			writeFile(response,downFile);
//
//		} catch(Exception e) {
//			Map<String,Object> message = new HashMap<String,Object>(2);
//			message.put("success", false);
//			message.put("msg", e.getMessage());
//			response.setCharacterEncoding(Constant.DEFAULT_ENCODE.STRING_UTF8);
//			response.setContentType(Constant.DEFAULT_CONTENT_TYPE.CONTENT_TYPE_UTF8);
//		}
//	}
	
//	private void writeFile(HttpServletResponse response,File file){
//		InputStream oInput = null;
//		OutputStream oOutput = null;
//		try {
//			oInput = new FileInputStream(file);
//			oOutput = response.getOutputStream();
//
////			TipsLogger.log("Streaming to output buffer +++ START +++");
//			byte[] oBuff = new byte[1024];
//			int iSize;
//			while (-1 != (iSize = oInput.read(oBuff))) {
//				oOutput.write(oBuff, 0, iSize);
//			}
//			//TipsLogger.log("Streaming to output buffer +++ END +++");
//
//			oOutput.flush();
//		} catch (IOException ioe) {
//			LOGGER.info("",ioe);
//		} catch (Exception e){
//			LOGGER.info("",e);
//		} finally {
//			if (oInput != null) {
//				try {
//					oInput.close();
//				} catch (IOException e) {
//				}
//			}
//			if (oOutput != null){
//				try {
//					oOutput.close();
//				} catch (IOException e) {
//				}
//			}
//		}
//	}

}
