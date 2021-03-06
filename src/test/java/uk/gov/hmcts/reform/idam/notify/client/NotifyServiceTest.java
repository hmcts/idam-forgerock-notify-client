package uk.gov.hmcts.reform.idam.notify.client;

import com.sun.identity.authentication.spi.AuthLoginException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.gov.service.notify.NotificationClientApi;
import uk.gov.service.notify.NotificationClientException;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static uk.gov.hmcts.reform.idam.notify.client.NotifyService.OTP_CODE_PARAM;

@RunWith(MockitoJUnitRunner.class)
public class NotifyServiceTest {

    private static final String AN_EMAIL = "john@doe.com";
    private static final String CODE = "12345";
    private static final String TEMPLATE_ID = "67ace669-93b5-4055-88b2-c136c63c5511";
    private final Map<String, String> params = new HashMap<String, String>() {{
        put(OTP_CODE_PARAM, CODE);
    }};

    @Mock
    private NotificationClientApi notificationClient;

    private NotifyService notifyService;

    @Before
    public void setUp() {
        notifyService = new NotifyService(TEMPLATE_ID, notificationClient);
    }

    /**
     * @verifies send email
     * @see NotifyService#sendEmail(String, String, String, String, String, java.util.Map)
     */
    @Test
    public void sendEmail_shouldSendEmail() throws Exception {
        notifyService.sendEmail(null, AN_EMAIL, TEMPLATE_ID, "", CODE, null);
        verify(notificationClient).sendEmail(TEMPLATE_ID, AN_EMAIL, params, "", "");
    }

    /**
     * @verifies do nothing if `to` param is null
     * @see NotifyService#sendEmail(String, String, String, String, String, java.util.Map)
     */
    @Test
    public void sendEmail_shouldDoNothingIfToParamIsNull() throws Exception {
        notifyService.sendEmail(null, null, null, "", CODE, null);
        verify(notificationClient, never()).sendEmail(TEMPLATE_ID, null, params, "", "");

    }

    /**
     * @verifies throw exception if sending email throws exception
     * @see NotifyService#sendEmail(String, String, String, String, String, java.util.Map)
     */
    @Test
    public void sendEmail_shouldThrowExceptionIfSendingEmailThrowsException() throws Exception {
        notifyService.sendEmail(null, AN_EMAIL, TEMPLATE_ID, "", CODE, null);
        verify(notificationClient).sendEmail(TEMPLATE_ID, AN_EMAIL, params, "", "");
    }

    /**
     * @verifies send email
     * @see NotifyService#sendSMSMessage(String, String, String, String, String, java.util.Map)
     */
    @Test
    public void sendSMSMessage_shouldSendEmail() throws Exception {
        notifyService.sendSMSMessage(null, AN_EMAIL, TEMPLATE_ID, "", CODE, null);
        verify(notificationClient).sendEmail(TEMPLATE_ID, AN_EMAIL, params, "", "");
    }

    /**
     * @verifies use fallback template id if subject is default value
     * @see NotifyService#sendEmail(String, String, String, String, String, Map)
     */
    @Test
    public void sendEmail_shouldUseFallbackTemplateIdIfSubjectIsDefaultValue() throws AuthLoginException, NotificationClientException {
        final NotificationClientApi clientMock = mock(NotificationClientApi.class);
        final String testTemplateId = "test";
        final NotifyService notifyService = new NotifyService(testTemplateId, clientMock);

        notifyService.sendEmail(AN_EMAIL, AN_EMAIL, NotifyService.OPENAM_DEFAULT_SUBJECT, null, CODE, null);
        verify(clientMock).sendEmail(eq(testTemplateId), eq(AN_EMAIL), eq(params), any(), any());
    }

    /**
     * @verifies use subject as template id
     * @see NotifyService#sendEmail(String, String, String, String, String, Map)
     */
    @Test
    public void sendEmail_shouldUseSubjectAsTemplateId() throws AuthLoginException, NotificationClientException {
        final NotificationClientApi clientMock = mock(NotificationClientApi.class);
        final String testTemplateId = "test";
        final NotifyService notifyService = new NotifyService(testTemplateId, clientMock);

        final String suppliedTemplateId = "actual template id";
        notifyService.sendEmail(AN_EMAIL, AN_EMAIL, suppliedTemplateId, null, CODE, null);
        verify(clientMock).sendEmail(eq(suppliedTemplateId), eq(AN_EMAIL), eq(params), any(), any());
    }
}