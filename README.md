# idam-forgerock-notify-client
A custom SMS gateway for ForgeRock. Used for multi-factor authentication. 

The Jar lives in the AM instances in tomcat/libs folder.

2 environment variables required:
* `NOTIFY_KEY` - the notification client api key.
* `NOTIFY_TEMPLATE_ID` - the notification client template id.
