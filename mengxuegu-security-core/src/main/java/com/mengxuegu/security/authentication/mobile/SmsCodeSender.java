package com.mengxuegu.security.authentication.mobile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmsCodeSender implements SmsSend{

    private static final Logger logger = LoggerFactory.getLogger(SmsCodeSender.class);

    @Override
    public boolean sendSms(String mobile, String content) {
        String sendContent = String.format("梦学员，验证码%s，请勿泄露", content);
        logger.info("向手机号：" + mobile + "发送的短信为：" + sendContent);
        return true;
    }
}
