class UrlMappings {

	static mappings = {
        "/"(controller: 'foo', action: 'bar')
		"/allGood"(controller:"foo", action:"bar")
		"/stackOverflow" (view:'/foo/bar')

//        "/$controller/$action?/$id?"{
//            constraints {
//                // apply constraints here
//            }
//        }

        "400"(controller: 'foo', action: 'bar')
        "403"(controller: 'foo', action: 'bar')
        "404"(controller: 'foo', action: 'bar')
        "500"(controller: 'foo', action: 'bar')

	}
}
