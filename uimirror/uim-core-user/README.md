**uim-core-user**
============


**Goal**
=============
- To Provide core API and abstractions for the User entity.
- Abstractions as in the core stores and its implementation


**Packages**
=====
1. **com.uimirror.core.user**
======
**Features**
- Has all User Entity. 

2. **com.uimirror.core.user.store**
=============
**Features**
- Persistance of User Entity such as save, update, delte and read through APIS

3. **com.uimirror.core.user.processor**
======================
- External or utility API for the user info


**Usage**
===================
Include it as dependency:
<pre>
compile group: 'com.uimirror.api', name: 'uim-core-user', version: '1.0'
</pre>