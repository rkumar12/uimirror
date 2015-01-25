**uimirror**
========

**Goal**
=============
- Set up core API library for the applications getting delivered.
- The main core modules are uim-core, uim-account, uim-ouath2, ws-security
  
**Instructions To Build**
=============================
- Check out the project
- Navigate to the uimirror->build.gradle, <pre>run clean build publishToMavenLocal</pre>
  					
  					
**Does**
======
- Take updates always and check the release version and notes
- Do a careful analysis on the version and its feature before changing the depedancy versions

**Don't**
=======
- Never check in any file which is not compling.
- Never commit anything from bin and build folder.

**Modules**
=====
**uim_core**
======
**Features**
- Basic Abstraction of user
- Basic abstraction of Mongo document


