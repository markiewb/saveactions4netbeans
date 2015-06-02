# How to build for other netbeans versions

# Introduction #

This plugin has to be compiled for every netbeans version manually. Because it uses the dependency "Keymap options", which can only be accessed by friend module and this plugin is not a friend module.


# Details #

  * Check out
  * Change the netbeans plattform to the other version (Project properties->Libraries)
  * Change the "Keymap options"-dependency to use the implementation version of the other target platform