package com.mengxuegu.security.authentication.mobile;

public interface SmsSend {

    boolean sendSms(String mobile, String content);
}
