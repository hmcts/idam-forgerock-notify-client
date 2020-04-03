# idam-forgerock-notify-client
A custom SMS gateway for ForgeRock. Used for multi-factor authentication. 

## Config Notes

The Jar lives in the AM instances in the `tomcat/webapps/openam/WEB-INF/lib/` folder.

The Notify client uses two template ids, one for English and one for bi-lingual, these are taken from the 
`amAuthHOTP_<locale>.properties` files found in AM's `openam-auth-hotp-6.5.2.2.jar` library located in 
the same folder as the client. In order to change these values, please make a change in the `cnp-idam-packer`
git repository. **Do not repack the file manually** as it will be overwritten by Ansible.

1 java opts variable is required:
* `notifyApiKey` - the notification client api key.

You will also have to specify the Gateway Implementation Class to use this notify client, which is: `uk.gov.hmcts.reform.idam.notify.client.NotifyService`.

## Releases
* 1.0 - The initial release.
* 1.1 - User java opts rather than the system evn variables.
* 1.2 - Banish json from dependencies to stop interference with Forgerock's libs.
* 1.3 - Add release notes. Introduce dependency shadowing for a number of dependencies to reduce potential interference. 
* 2.0 - Add support for language-specific notifications - English and bi-lingual (English and Welsh).

## Distribution
Released jars of all versions are available here:
http://dl.bintray.com/hmcts/hmcts-maven/uk/gov/hmcts/reform/idam/idam-forgerock-notify-client/