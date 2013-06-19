# grails-filter-stackoverflow #


Bare grails 2.2.2 app to demonstrate stackoverflows when filters applied to urlmappings that point to views

## Info ##

An minimal grails app with 3 http request mappings and a single filter that is apploed to all incomming requests via: 
	all(uri: '/**')
	
The expected behavior is with a request to any of the 3  url mappings, the filter should kick in and redirect to grails.org.

Here are the relevant url mappings: 

	class UrlMappings {
		static mappings = {
	     	"/"(controller: 'foo', action: 'bar')
		"/allGood"(controller:"foo", action:"bar")
		"/stackOverflow" (view:'/foo/bar')
	
	        "400"(controller: 'foo', action: 'bar')
	        "403"(controller: 'foo', action: 'bar')
	        "404"(controller: 'foo', action: 'bar')
	        "500"(controller: 'foo', action: 'bar')
	
		}
	}
	
### Configuration  ###

* Required to have the apps context root set to / via Config.groovy grails.app.context = "/"
* Filter must return false after a redirect (as per documentation) so no other filters kick in - see:  
http://grails.org/doc/2.2.2/guide/single.html#filters

_Return false to indicate that the response has been handled that that all future 
filters and the action should not execute_



### Basic Filter ###

	class SomeFilterFilters {
	    def filters = {
	        all(uri: '/**') {
	            before = {
	                redirect(url: "http://grails.org")
	                return false // May have multiple filters so return false after a redirect
	            }
	        }
	    }
	}


## Actual Behavior ## 

> grails run-app

1. http://localhost:8080/
	Successful redirect to grails.org


2. http://localhost:8080/allGood
	Successful redirect to grails.org

3. http://localhost:8080/stackOverflow
	Infinite filter and eventually a StackOverflow. Notiice how the url mapping is configured with a view as opposed to a controler/action


## Debugging ##

* Culprit seems to be org.codehaus.groovy.grails.web.mapping.filter.UrlMappingsFilter - renderViewForUrlMappingInfo 

		
	if (viewName == null || viewName.endsWith(GSP_SUFFIX) || viewName.endsWith(JSP_SUFFIX)) {
		if (info.isParsingRequest()) {
		    webRequest.informParameterCreationListeners();
		}
		String forwardUrl = WebUtils.forwardRequestForUrlMappingInfo(request, response, info);
		if (LOG.isDebugEnabled()) {
		    LOG.debug("Matched URI [" + uri + "] to URL mapping [" + info + "], forwarding to [" + forwardUrl + "] with response [" + response.getClass() + "]");
		}
	}
	else {
		if (!renderViewForUrlMappingInfo(request, response, info, viewName)) {
		    dispatched = false;
		}
	}
    
* Not returning false, after the redirect, in the filter prevents the StackOverflow

