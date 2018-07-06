package com.tonghu.app.stage.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author liangyongjian
 * @desc 静态配置资源持有器
 * @create 2017-09-29 16:00
 **/
@Getter
@Setter
@Configuration
public class WebConfig {

    /**
     * 列表的一页记录数量
     */
    @Value("#{webConfigs['MANAGE_PAGE_SIZE']}")
    private int pageSize;

    /**
     * 查询的时间范围的起始范围
     */
    @Value("#{webConfigs['START_DATE_SEARCH']}")
    private String startDateSearch;

    @Value("#{webConfigs['APP_BASE_PATH']}")
    private String appBasePath;

    @Value("#{webConfigs['EXPORT_PATH']}")
    private String exportPath;

    @Value("#{webConfigs['UPLOAD_PATH']}")
    private String uploadPath;

    @Value("#{webConfigs['IMPORT_PATH']}")
    private String importPath;

    @Value("#{webConfigs['FILE_SUFFIX']}")
    private String fileSuffix;

    @Value("#{webConfigs['IMAGE_PATH']}")
    private String imagePath;

    @Value("#{webConfigs['VIDEO_PATH']}")
    private String videoPath;

    @Value("#{webConfigs['SERVER_ADDRESS']}")
    private String serverAddress;

    @Value("#{webConfigs['FILE_SERVER_NAME']}")
    private String fileServerName;

    @Value("#{webConfigs['FRONT_IMG_SUFFIX']}")
    private String frontImgSuffix;

    @Value("#{webConfigs['MAX_FILE_SIZE']}")
    private int maxFileSize;

}
