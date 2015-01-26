**ws-resource-security**
============


**Goal**
=============
- To provide common Api and abstraction for protecting the endpoints.
- It has support for Default access token validation using the webservice call or implementor have to implement the AccessTokenProcessor to validate the access token 

1. **com.uimirror.core.ws.security**
====================
- Basic Annotations and authentication contracts


**Usage**
===============
- Include it as dependency:
<pre>compile group: 'com.uimirror.api', name: 'ws-resource-security', version: '1.0'</pre>

- **Code usage**
- Use the default access token processor or use the custom one.
- For default Access Token Processor, define '''auth.server''' and point to the auth server
- use the '''DefautResourceSecurityBean''' in your spring config
- In case of custom processor, give implementation for '''AccessTokenProcessor''' similar to '''DefaultAccessTokenProcessor''' and define this bean in your spring config.
- Annotate the endpoint with <pre>@PreAuthorize</pre>

