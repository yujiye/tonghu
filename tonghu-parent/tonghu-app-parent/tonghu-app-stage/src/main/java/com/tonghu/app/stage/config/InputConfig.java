package com.tonghu.app.stage.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author liangyongjian
 * @desc 页面中input控件长度控制
 * @create 2017-09-30 13:56
 **/
@Getter
@Configuration
public class InputConfig {

    @Value("#{inputConfigCons['USER_NAME_MIN_LENGTH']}")
    private int userNameMinLength;

    @Value("#{inputConfigCons['USER_NAME_MAX_LENGTH']}")
    private int userNameMaxLength;

    @Value("#{inputConfigCons['PASSWORD_MIN_LENGTH']}")
    private int passwordMinLength;

    @Value("#{inputConfigCons['PASSWORD_MAX_LENGTH']}")
    private int passwordMaxLength;

    @Value("#{inputConfigCons['USER_TITLE_LENGTH']}")
    private int userTitleLength;

    @Value("#{inputConfigCons['TRUE_NAME_LENGTH']}")
    private int trueNameLength;

    @Value("#{inputConfigCons['USER_EMAIL_LENGTH']}")
    private int userEmailLength;

    @Value("#{inputConfigCons['USER_MOBILE_LENGTH']}")
    private int userMobileLength;

    @Value("#{inputConfigCons['USER_PHONE_LENGTH']}")
    private int userPhoneLength;

    @Value("#{inputConfigCons['ROLE_NAME_LENGTH']}")
    private int roleNameLength;

    @Value("#{inputConfigCons['NOTE_LENGTH']}")
    private int noteLength;

    @Value("#{inputConfigCons['FILE_NAME_LENGTH']}")
    private int fileNameLength;

    @Value("#{inputConfigCons['ORIGINAL_FILE_NAME_LENGTH']}")
    private int originalFileNameLength;

    @Value("#{inputConfigCons['FILE_TYPE_LENGTH']}")
    private int fileTypeLength;

    @Value("#{inputConfigCons['FILE_PATH_LENGTH']}")
    private int filePathLength;

    @Value("#{inputConfigCons['PROFILE_LENGTH']}")
    private int profileLength;

    @Value("#{inputConfigCons['CONFIG_VALUE_LENGTH']}")
    private int configValueLength;

    @Value("#{inputConfigCons['DATA_NAME_LENGTH']}")
    private int dataNameLength;

    @Value("#{inputConfigCons['DATA_VALUE_LENGTH']}")
    private int dataValueLength;

    @Value("#{inputConfigCons['MATERIAL_NAME_LENGTH']}")
    private int materialNameLength;

}
