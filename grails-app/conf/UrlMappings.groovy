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