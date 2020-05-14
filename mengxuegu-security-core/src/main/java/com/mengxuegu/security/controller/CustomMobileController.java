package com.mengxuegu.security.controller;

import com.mengxuegu.security.authentication.mobile.SmsSend;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import result.MengxueguResult;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomMobileController {

    public static final String SESSION_KEY = "SESSION_KEY_MOBILE_CODE";

    @Autowired
    private SmsSend smsSend;

    @RequestMapping("/mobile/page")
    public String toMobilePage() {
        return "login-mobile";
    }

    @RequestMapping("/code/mobile")
    @ResponseBody
    public MengxueguResult smsCode(HttpServletRequest request) {
        String code = RandomStringUtils.randomNumeric(4);
        request.getSession().setAttribute(SESSION_KEY, code);

        String mobile = request.getParameter("mobile");
        smsSend.sendSms(mobile, code);

        return MengxueguResult.ok();
    }
}
