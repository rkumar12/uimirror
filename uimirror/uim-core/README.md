**uim-core**
============


**Goal**
=============
- To Provide core API and abstractions for the different applications.
- 


**Packages**
=====
1. **com.uimirror.core**
======
**Features**
- Has all common used enum, constants, error codes, and default processor. 

2. **com.uimirror.core.auth**
=============
**Features**
- Authentication releated bean and constants

3. **com.uimirror.core.auth.token**
======================
- Basic implementation of the token

4. **com.uimirror.core.bean**
======================
- Core beans such as clientMeta informations

5. **com.uimirror.core.crypto**
====================
- Crypto definations such as encryption and deCryption helper

6. **com.uimirror.core.exceptions**
====================
- All the common exceptions
- SSO objects.

7. **com.uimirror.core.exceptions.db**
====================
- All the common exceptions related to DB

8. **com.uimirror.core.extra**
====================
- Validator and exception Aspects

9. **com.uimirror.core.form**
====================
- Basic common form for the REST API end points

10. **com.uimirror.core.job**
====================
- Core Job modules.
- '''SimpleJob''' bean needs to be populated for the job details to store

11. **com.uimirror.core.job.store**
====================
- Core Job modules for persisting the details.
- '''PersistedSimpleJobMongoStore''' will store the job into the job store, while creating the bean
  module should populate collection name and its sequence
  
12. **com.uimirror.core.mail**
====================
- Core mail functionality.
- '''EmailBeanInitializr''' can be used to instantiate the email beans.

13. **com.uimirror.core.mongo**
====================
- Mongo connection related objects.

14. **com.uimirror.core.mongo.feature**
====================
- Any modle which will participate in the storing process, needs to extend   '''AbstractBeanBasedDocument''' 

15. **com.uimirror.core.rest.extra**
====================
- All the API related exceptions which can be send to the caller.

16. **com.uimirror.core.service**
====================
- Common Service defination like validation, transformer.

17. **com.uimirror.core.store**
====================
- Common Store Service defination like '''BasicStore'''.
- Any Store can extend '''AbstractMongoStore''' for the common features.
 
18. **com.uimirror.core.user**
====================
- Common User bean definitions.

19. **com.uimirror.core.util**
====================
- utility functions such as DateUtils.

20. **com.uimirror.core.util.thread**
====================
- Thread related utility for async and sync jobs.

21. **com.uimirror.core.util.web**
====================
- Web Releated utils.
 

**Usage**
===================
Include it as dependency:
<pre>
compile group: 'com.uimirror.api', name: 'uim-core', version: '1.0'
</pre>



