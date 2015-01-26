**uim-core-client**
============
Provides a common way to represent the ouath2 client process.

- Clients are reffered as the System who will use the uim API's
- Client can be browser or mobile app.
- Clients can be any third party application such as Facebook who wants to use our service.


**Goal**
=============
- To Provide A common client API and abstractions for the clients.
-

**Packages**
=====
1. **com.uimirror.core.client**
======
**Features**
- Has all Client entities. 

2. **com.uimirror.core.form**
=============
**Features**
- Client forms for the incoming REST calls.

2. **com.uimirror.core.client.processor**
=============
**Features**
- Client Processor API which will be used as endpoint.

2. **com.uimirror.core.client.store**
=============
**Features**
- Client Data store API.

2. **com.uimirror.core.client.validator**
=============
**Features**
- Client Validation API.

2. **com.uimirror.core.client.exception**
=============
**Features**
- Client Exception Mappers.

**Usage**
===================
Include it as dependency:
<pre>
compile group: 'com.uimirror.api', name: 'uim-core-client', version: '1.0'
</pre> 
 





