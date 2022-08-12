# HPE URL Shortener
IntelliJ Java Spring Boot project using Gradle.


### Quick Start 
* http://localhost:8080/new?fullURL=https://www.google.com
* http://localhost:8080/<Crumb>

### New End Point
Generates a new Crumb for the given URL.
* @param fullURL - The URL to shorten.
* @returns shortened URL.

Example response:\
http://localhost:8080/ZOX (Raw String)

### Root End Point
Redirects to the previously created full URL for the given Crumb.\
http://localhost:8080/<Crumb>\
If there is no previously created Crumb returns OK with empty payload.\
// TODO Redirect to local "cannot be found" page.

### Dependencies
See build.gradle for a list.

### Further Work
// TODO redirect to local not found page when crumb does not exist\
// TODO Crumbs of length too large will crash JVM. (You'd have to brute force hard for this)\
// TODO Not cryptographically secure - do we care? consider.\
// TODO Crumbs are Alphanumeric not Base64, consider using base64 for a bit more storage\
// TODO some tests need finishing\

### Discussion
Quick solution to the problem, some further considerations:\

The crumbs are stored in an in memory solution meaning we lose them all on restart.\
Using a quick access key-value database maybe a good way to support this in the future.

There are lots of issues under the surface of the way new URLs are generated,
because we're basically requesting a URL in a URL. So characters like =&? are bound to confuse
spring boot's injection routine.\
For now though given time constraints I'm choosing to let
the problems exist and in future maybe use a Post with message body to transfer the URL. 
This would hook up nicer to a front end to and preserve RESTfulness in the service.