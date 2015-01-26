**ws-filters**
============


**Goal**
=============
- To provide common Rest filters.
- 

1. **com.uimirror.core.ws.filter**
====================
<pre>PoweredByResponseFilter</pre>.
	- Adds the uimirror as the provider in the response
<pre>ContentTypeResponeFilter</pre>
	- Adds the content type to the response

<pre>UimCORSFilter</pre>
	- Adds cross regions to the response
	

**Usage**
========
Include it as dependency:
<pre>
compile group: 'com.uimirror.api', name: 'ws-filters', version: '1.0'
</pre>

