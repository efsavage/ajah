# The Ajah Libraries #

##  Background ##
The Ajah libraries are a set of utility libraries that are intended to address common programming needs (e.g. ajah-util), streamline other APIs (e.g. ajah-servlet), and provide common functionality (e.g. ajah-user).

##  Re-inventing the wheel? ##
These libraries contain a mix of new and old ideas.  For example, you might find things in ajah-util where you could find similar functionality in Apache Commons Lang.  You might not see the benefit of using a wrapper library like ajah-amazon-s3 that relies on another library to do the real work and just attempts to streamline it.  

The functionality contained here is simply a reflection of things I have needed for a wide variety of projects.  Some is very general and likely to be found in some form in other libraries, some is very specific and unique.  Some is simply a different way of solving the same task, perhaps with fewer lines of code or more sensible defaults or more configuration options.

##  Building ##
This repository is set up as a suite of Maven projects.  The dependencies they use vary from few to many, so you may want to import them into your IDE on an as-needed basis.

##  Status & Completion ##
In the interest of only solving problems that exist, these libraries are largely built on an as-needed basis.  This may lead to some "missing" code that you might expect to be there, which basically means you're solving a problem I haven't encountered yet.  I'm happy to review pull requests for such things.

## Versioning ##
This project has not adopted any versioning strategy.  If you plan to use this code, do not expect backwards compatibility, so please make sure you're prepared to refactor and test if you decide to upgrade.  Forking the project and upgrading at your own pace is highly recommended.  

The libraries are intended to be used at a consistent version level, for example there is no guarantee that 1.0.2 of one library will operate correctly with 1.0.1 of another.

For more detailed information, see the README in each project.