package uk.gov.hmcts.reform.idam.notify.client;


import com.sun.identity.authentication.modules.hotp.SMSGateway;
import com.sun.identity.authentication.spi.AuthLoginException;
import uk.gov.service.notify.NotificationClient;
import uk.gov.service.notify.NotificationClientApi;
import uk.gov.service.notify.NotificationClientException;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class NotifyService implements SMSGateway {

    private static final Logger LOGGER = Logger.getLogger(NotifyService.class.toString());
    private static final String NOTIFY_KEY = "notifyApiKey";
    private static final String NOTIFY_TEMPLATE_ID = "notifyApiTemplateId";
    static final String OTP_CODE_PARAM = "code";
    static final String OPENAM_DEFAULT_SUBJECT = "OpenAM One Time Password";
    private final String notificationClientTemplateId;
    private final NotificationClientApi notificationClient;

    static {
        LOGGER.warning("Successfully loaded custom Notify client for OTP.");
    }

    public NotifyService() {
        notificationClientTemplateId = System.getProperty(NOTIFY_TEMPLATE_ID);
        notificationClient = new NotificationClient(System.getProperty(NOTIFY_KEY));
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
     * @should use fallback template id if subject is default value
     * @should use subject as template id
     */
    @Override
    public void sendEmail(String from, String to, String subject, String message, String code, Map options)
            throws AuthLoginException {

        final String languageSpecificNotificationClientTemplateId;

        LOGGER.severe(String.format("Sending OTP to: %s, subject: %s", to, subject));

        // We are hijacking the i18n mechanism here. The language-specific templateId is being passed as message subject.
        // This condition makes it backwards-compatible.
        if (OPENAM_DEFAULT_SUBJECT.equalsIgnoreCase(subject)) {
            LOGGER.warning("Language-specific Notify templateId is not available. Falling back to the one supplied in env.");
            languageSpecificNotificationClientTemplateId = notificationClientTemplateId;
        } else {
            // value set in amAuthHOTP_<locale>.properties - see: SIDM-3931
            languageSpecificNotificationClientTemplateId = subject;
        }

        if (to != null) {
            try {
                Map<String, String> parameters = new HashMap<>();
                parameters.put(OTP_CODE_PARAM, code);
                notificationClient.sendEmail(languageSpecificNotificationClientTemplateId, to, parameters, "", "");

            } catch (NotificationClientException e) {
                throw new AuthLoginException("Failed to send OTP code to " + to, e);
            }
        }
    }
}
