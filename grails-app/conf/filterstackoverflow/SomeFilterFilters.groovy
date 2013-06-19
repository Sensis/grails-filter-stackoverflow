package filterstackoverflow

class SomeFilterFilters {
    def filters = {
        all(uri: '/**') {
            before = {
                redirect(url: "http://grails.org")
//                return false // May have multiple filters so return false after a redirect
            }
        }
    }
}
