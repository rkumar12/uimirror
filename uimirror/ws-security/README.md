**ws-Security**
============


**Goal**
=============
- To provide common Api and abstraction for protecting the endpoints.
- It has support for login by user, client api key validation 

1. **com.uimirror.core.ws.filter**
====================
<pre>PoweredByResponseFilter : Adds the uimirror as the provider in the response</pre>
<pre>ContentTypeResponeFilter : Adds the content type to the response</pre>
<pre>UimCORSFilter: Adds cross regions to the response</pre>


**Usage**
===============
- Include it as dependency:
<pre>compile group: 'com.uimirror.api', name: 'ws-filters', version: '1.0'</pre>

