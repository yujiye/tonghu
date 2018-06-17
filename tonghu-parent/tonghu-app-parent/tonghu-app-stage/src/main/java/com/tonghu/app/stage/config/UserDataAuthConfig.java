package com.tonghu.app.stage.config;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author liangyongjian
 * @desc 前端用户对数据的权限配置
 * @create 2018-01-27 16:32
 **/
//@Configuration
public class UserDataAuthConfig {

    private static final String AUTH_MAPPING_FILE = "configs/xinyuan-user-data-auth.properties";

    private static final String AUTH_MAPPING_ENCODING = "UTF-8";

    private static final Map<String, String> USER_AUTH_MAP = Maps.newHashMap();

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDataAuthConfig.class);

//    @PostConstruct
    private void init() throws UnsupportedEncodingException, IOException {
        Properties props = new Properties();
        try (InputStreamReader reader = new InputStreamReader(this.getClass()
                .getClassLoader().getResourceAsStream(AUTH_MAPPING_FILE), AUTH_MAPPING_ENCODING)) {
            props.load(reader);
        }
        Set<Map.Entry<Object, Object>> entrySet = props.entrySet();//返回的属性键值对实体
        for (Map.Entry<Object, Object> entry : entrySet) {
            USER_AUTH_MAP.put((String)entry.getKey(), (String)entry.getValue());
        }

        LOGGER.info("消息定义加载成功： {}", USER_AUTH_MAP);
    }

    public String getUserAuth(String userName) {
        return USER_AUTH_MAP.get(userName);
    }



}
