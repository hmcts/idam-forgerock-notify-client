package com.sun.identity.authentication.modules.hotp;

import com.sun.identity.authentication.spi.AuthLoginException;

import java.util.Map;

public interface SMSGateway {
    void sendSMSMessage(String from, String to, String subject, String message, String code, Map options)
            throws AuthLoginException;

    void sendEmail(String from, String to, String subject, String message, String code, Map options)
            throws AuthLoginException;
}
