# idam-forgerock-notify-client
A custom SMS gateway for ForgeRock. Used for multi-factor authentication. 

## Config Notes

The Jar lives in the AM instances in the `tomcat/webapps/openam/WEB-INF/lib/` folder.

2 java opts variables required:
* `notifyApiKey` - the notification client api key.
* `notifyApiTemplateId` - the notification client template id.

You will also have to specify the Gateway Implementation Class to use this notify client, which is: `uk.gov.hmcts.reform.idam.notify.client.NotifyService`.

## Releases
* 1.0 - The initial release.
* 1.1 - User java opts rather than the system evn variables.
* 1.2 - Banish json from dependencies to stop interference with Forgerock's libs.
* 1.3 - Add release notes. Introduce dependency shadowing for a number of dependencies to reduce potential interference. 

## Distribution
Released jars of all versions are available here:
http://dl.bintray.com/hmcts/hmcts-maven/uk/gov/hmcts/reform/idam/idam-forgerock-notify-client/