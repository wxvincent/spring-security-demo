package com.mengxuegu.security;

import com.mengxuegu.security.authentication.mobile.SmsSend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MobileSmsCodeSender implements SmsSend {

    private static final Logger logger = LoggerFactory.getLogger(MobileSmsCodeSender.class);

    @Override
    public boolean sendSms(String mobile, String content) {
        logger.info("web应用新的短信验证码接口向手机号：" + mobile +"发送了验证码为：" + content);
        return true;
    }

}
