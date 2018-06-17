package com.tonghu.app.stage.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author liangyongjian
 * @desc 页面中input控件长度控制
 * @create 2017-09-30 13:56
 **/
@Configuration
public class InputConfig {

    @Value("#{inputConfigCons['PROJECT_NAME_LENGTH']}")
    private int projectNameLength;

    @Value("#{inputConfigCons['COUNTY_LEVEL_CITY_LENGTH']}")
    private int countyLevelCityLength;

    @Value("#{inputConfigCons['DESCRIPTION_LENGTH']}")
    private int descriptionLength;

    @Value("#{inputConfigCons['BRANCH_NAME_LENGTH']}")
    private int branchNameLength;

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

    @Value("#{inputConfigCons['PROVINCE_LENGTH']}")
    private int provinceLength;

    @Value("#{inputConfigCons['CITY_LENGTH']}")
    private int cityLength;

    @Value("#{inputConfigCons['PROJECT_TYPE_NAME_LENGTH']}")
    private int projectTypeNameLength;

    @Value("#{inputConfigCons['PROJECT_STATUS_NAME_LENGTH']}")
    private int projectStatusNameLength;

    @Value("#{inputConfigCons['PROJECT_FORMAT_NAME_LENGTH']}")
    private int projectFormatNameLength;

    @Value("#{inputConfigCons['PROJECT_SHOW_PASS_LENGTH']}")
    private int projectShowPassLength;

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

    @Value("#{inputConfigCons['FRONT_USER_NAME_LENGTH']}")
    private int frontUserNameLength;

    @Value("#{inputConfigCons['FRONT_USER_TRUE_NAME_LENGTH']}")
    private int frontUserTrueNameLength;

    @Value("#{inputConfigCons['REGION_COMPANY_NAME_LENGTH']}")
    private int regionCompanyNameLength;

    @Value("#{inputConfigCons['CITY_COMPANY_NAME_LENGTH']}")
    private int cityCompanyNameLength;


    public int getProjectNameLength() {
        return projectNameLength;
    }

    public int getCountyLevelCityLength() {
        return countyLevelCityLength;
    }

    public int getDescriptionLength() {
        return descriptionLength;
    }

    public int getBranchNameLength() {
        return branchNameLength;
    }

    public int getUserNameMinLength() {
        return userNameMinLength;
    }

    public int getUserNameMaxLength() {
        return userNameMaxLength;
    }

    public int getPasswordMinLength() {
        return passwordMinLength;
    }

    public int getPasswordMaxLength() {
        return passwordMaxLength;
    }

    public int getUserTitleLength() {
        return userTitleLength;
    }

    public int getTrueNameLength() {
        return trueNameLength;
    }

    public int getUserEmailLength() {
        return userEmailLength;
    }

    public int getUserMobileLength() {
        return userMobileLength;
    }

    public int getUserPhoneLength() {
        return userPhoneLength;
    }

    public int getRoleNameLength() {
        return roleNameLength;
    }

    public int getNoteLength() {
        return noteLength;
    }

    public int getProvinceLength() {
        return provinceLength;
    }

    public int getCityLength() {
        return cityLength;
    }

    public int getProjectTypeNameLength() {
        return projectTypeNameLength;
    }

    public int getProjectStatusNameLength() {
        return projectStatusNameLength;
    }

    public int getProjectFormatNameLength() {
        return projectFormatNameLength;
    }

    public int getProjectShowPassLength() {
        return projectShowPassLength;
    }

    public int getFileNameLength() {
        return fileNameLength;
    }

    public int getOriginalFileNameLength() {
        return originalFileNameLength;
    }

    public int getFileTypeLength() {
        return fileTypeLength;
    }

    public int getFilePathLength() {
        return filePathLength;
    }

    public int getProfileLength() {
        return profileLength;
    }

    public int getConfigValueLength() {
        return configValueLength;
    }

    public int getFrontUserNameLength() {
        return frontUserNameLength;
    }

    public int getFrontUserTrueNameLength() {
        return frontUserTrueNameLength;
    }

    public int getRegionCompanyNameLength() {
        return regionCompanyNameLength;
    }

    public int getCityCompanyNameLength() {
        return cityCompanyNameLength;
    }
}
