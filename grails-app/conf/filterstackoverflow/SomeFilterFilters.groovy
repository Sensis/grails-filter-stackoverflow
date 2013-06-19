package filterstackoverflow

class SomeFilterFilters {

    def filters = {
        all(uri: '/**') {
            before = {
                redirect(url: "http://grails.org")
                return false // need this to prevent other filters from executing
            }
        }
    }
}
