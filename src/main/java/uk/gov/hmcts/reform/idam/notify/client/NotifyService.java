package uk.gov.hmcts.reform.idam.notify.client;


import com.sun.identity.authentication.modules.hotp.SMSGateway;
import com.sun.identity.authentication.spi.AuthLoginException;
import uk.gov.service.notify.NotificationClient;
import uk.gov.service.notify.NotificationClientApi;
import uk.gov.service.notify.NotificationClientException;

import java.util.HashMap;
import java.util.Map;

public class NotifyService implements SMSGateway {

    private static final String NOTIFY_KEY = "NOTIFY_KEY";
    private static final String NOTIFY_TEMPLATE_ID = "NOTIFY_TEMPLATE_ID";
    static final String OTP_CODE_PARAM = "code";
    private final String notificationClientTemplateId;
    private final NotificationClientApi notificationClient;

    public NotifyService() {
        notificationClientTemplateId = System.getenv(NOTIFY_TEMPLATE_ID);
        notificationClient = new NotificationClient(System.getenv(NOTIFY_KEY));
    }

    public NotifyService(String notificationClientTemplateId, NotificationClientApi notificationClient) {
        this.notificationClientTemplateId = notificationClientTemplateId;
        this.notificationClient = notificationClient;
    }

    /**
     * @should send email
     */
    @Override
    public void sendSMSMessage(String from, String to, String subject, String message, String code, Map options)
            throws AuthLoginException {
        this.sendEmail(from, to, subject, message, code, options);
    }

    /**
     * @should send email
     * @should do nothing if `to` param is null
     * @should throw exception if sending email throws exception
     */
    @Override
    public void sendEmail(String from, String to, String subject, String message, String code, Map options)
            throws AuthLoginException {

        if (to != null) {
            try {
                Map<String, String> parameters = new HashMap<>();
                parameters.put(OTP_CODE_PARAM, code);
                notificationClient.sendEmail(notificationClientTemplateId, to, parameters,"","");

            } catch (NotificationClientException e) {
                throw new AuthLoginException("Failed to send OTP code to " + to, e);
            }
        }
    }
}
