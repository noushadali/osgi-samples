# Introduction #

Within the enterprise we are used to deploying ear files if we need a new application or an update to an application. If we want some kind of migration we even need two separate instances of the same application to prevent class loading issues and of course the usages possibilities from the clients. This is where osgi steps in. Read more about osgi on the website [osgi](http://www.osgi.org). On this page you'll find some basics about osgi and most of all about the usage of osgi for our goal.


# Details #

We need to discuss the following characteristics of bundles:
  * interaction between components
  * exposing services
  * versioning of services and bundles
  * bundles that are more libraries than services
  * deployment of bundles (runtime, state, etc)
  * classloading
  * availability of libraries
  * difference of adding libraries to a bundle or creating bundles from libraries
  * explaining concepts of exporting packages versus exposing services.

OSGi is about interaction between components. A component, or a bundle (osgi term), provides a service to other components. These services can be called by other components. One of the properties a bundle exports is the version. So clients can ask for a specific version. The container enables deployment of multiple versions at the same time.