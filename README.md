# The Ajah Libraries #

##  Background ##

The Ajah libraries are a set of utility libraries that are intended to address common programming needs (e.g. ajah-util), streamline other APIs (e.g. ajah-servlet), and provide common functionality (e.g. ajah-user).

##  Building ##
This repository is set up as a suite of Maven projects.  The dependencies they use vary from few to many, so you may want to import them into your IDE on an as-needed basis.

**First Build**
If you do not have ajah-base installed, comment out the &lt;modules> portion of the pom.xml, run mvn install, then uncomment it and re-install. *(If anyone can recommend a setup that avoids this please let me know!)*

For more information, see the README in each project.