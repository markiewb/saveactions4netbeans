
---

**NOTE:** This plugin is obsolete since NetBeans 7.3 - see http://netbeans.org/bugzilla/show_bug.cgi?id=140719 for the native implementation

---


Implements the eclipse-known save-actions pattern for netbeans. It invokes actions like format, fix-import on saving.

This implements the feature requests:
  * http://netbeans.org/bugzilla/show_bug.cgi?id=14202
  * http://netbeans.org/bugzilla/show_bug.cgi?id=140719

![http://saveactions4netbeans.googlecode.com/hg/doc/example.png](http://saveactions4netbeans.googlecode.com/hg/doc/example.png)
<p>
<b>Setup:</b>
<ul>
<li>Install the <b>specific (!)</b> nbm for your netbeans installation (If the plugin does not work for your Netbeans version, then follow the steps at <a href='http://code.google.com/p/saveactions4netbeans/wiki/Howtobuildforothernetbeansversions'>http://code.google.com/p/saveactions4netbeans/wiki/Howtobuildforothernetbeansversions</a> to compile it yourself (its easy))</li>
<li>Map the action "Save with save actions" to STRG-S. </li>
</ul>
</p>

<p>
<b>Configuration:</b> Use "Options -> Editor -> Save actions"<br>
</p>

<b>Hint:</b>
<ul>
<li>Use "Compi&le File" instead of "&Save" as action name. Compiling includes saving.</li>
</ul>

<p>
<b>Tested configurations (using the specific nbms): </b>
<pre>
Product Version: NetBeans IDE 7.1.1 (Build 201203012225)<br>
Java: 1.6.0_30; Java HotSpot(TM) 64-Bit Server VM 20.5-b03<br>
System: Windows 7 version 6.1 running on amd64; Cp1252; de_DE (nb)<br>
<br>
Product Version: NetBeans IDE 7.1.1 (Build 201202271535)<br>
Java: 1.7.0_03-icedtea; OpenJDK 64-Bit Server VM 22.0-b10<br>
System: Linux version 3.2.11-1-ck running on amd64; UTF-8; pl_PL (nb)<br>
<br>
Product Version: NetBeans IDE 7.1.2 (Build 201204101705)<br>
Java: 1.6.0_30; Java HotSpot(TM) 64-Bit Server VM 20.5-b03<br>
System: Windows 7 version 6.1 running on amd64; Cp1252; de_DE (nb)<br>
<br>
Product Version: NetBeans IDE 7.2 Beta (Build 201205031832)<br>
Java: 1.6.0_30; Java HotSpot(TM) 64-Bit Server VM 20.5-b03<br>
System: Windows 7 version 6.1 running on amd64; Cp1252; de_DE (nb)<br>
</pre>
</p>



&lt;hr/&gt;


<p>
<b>Copyright:</b> Code is partially based on code from Netbeans IDE (<pre>netbeans-7.1-201112071828-src\options.keymap\src\org\netbeans\modules\options\keymap\ActionsSearchProvider.java</pre> and therefor this derivate is licensed as CDDL/GNU Public License v.2 w/Classpath Exception - as stated in <a href='http://netbeans.org/cddl-gplv2.html'>http://netbeans.org/cddl-gplv2.html</a>)<br>
</p>
A patch for the netbeans community will provided soon, so that the code can be natively integrated into Netbeans IDE.