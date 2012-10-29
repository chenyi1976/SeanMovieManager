# Introduction #

SMM's plugin system is based on [Jython](www.jython.org). 

In short, it is python syntax, support all standard python module, with some extra api from SMM.

You can find all api in source code: `\source\me\chenyi\jython\jython_rules.py`.

# Steps to create plugin #

- Create a text file using any text editor.
- The plugin file name have four parts

	example plugin file name: onappstart_helloworld.py.

	1. **trigger type**: Avaiable trigger types list below, Case insensative.

		you can find these string in code: `me.chenyi.jython.ScriptTriggerType`.

		+ onappstart
		+ onappexit
		+ MenuTrigger
		+ *more to be add*

	2. **_**: this underline character is used to seperate 'trigger type' and 'plugin name'.
	3. **plungin name**: you can put any name you like
	4. **extension**: file name must use '.py' as extension
- put any script into this .py file
- copy .py file to your configuration directory.
	- for Mac OS, configuration directory is 
- start SMM, now it should works.

# Script Editor #

in SMM, there is an simple script editor intergrated.

It provide following function:

- list all plugin in system
- create/modify/delete plugin on fly.
- some syntax check function.