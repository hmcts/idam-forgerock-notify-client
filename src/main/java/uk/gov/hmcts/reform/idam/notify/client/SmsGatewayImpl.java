package uk.gov.hmcts.reform.idam.notify.client;


import com.sun.identity.authentication.modules.hotp.SMSGateway;
import com.sun.identity.authentication.spi.AuthLoginException;

import java.util.Map;

public class SmsGatewayImpl implements SMSGateway {

    @Override
    public void sendSMSMessage(
            String s, String s1, String s2, String s3, String s4, Map map) throws AuthLoginException {

    }

    @Override
    public void sendEmail(String s, String s1, String s2, String s3, String s4, Map map) throws AuthLoginException {

    }
}
