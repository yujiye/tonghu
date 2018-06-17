package com.tonghu.app.stage.controller;

import com.tonghu.pub.common.utils.WebUtils;
import com.tonghu.pub.model.security.po.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liangyongjian
 * @desc 系统首页
 * @create 2017-09-28 21:09
 **/
@Controller
public class WarningController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarningController.class);

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET}, value = "/warning.do")
    public String warning(HttpServletRequest request, ModelMap model){
        LOGGER.debug("访问工作提醒页面");
        String method = request.getMethod();
        model.addAttribute("pageHanName", "工作提醒"); //页面名称
        Users user = (Users)request.getSession().getAttribute("CURRENT_USER_INFO");
        WebUtils.WrapperModle(request, model);
        model.addAttribute("userInfo", user);
        return "manage/" + model.get("pageName");
    }

}
