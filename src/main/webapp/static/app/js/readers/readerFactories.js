var readerFactories = angular.module('readerFactories', ['ngResource']);

readerFactories.factory("ReaderFactory", function ($resource) {
    return $resource(URLS.readersRest, {id: "@id"}, {
        update: { method: 'PUT' },
        query:  { method: 'GET', isArray: false} //override default query method to be able to work with page
    });
});

readerFactories.service("ReaderService", function() {
	
	var searchString;
	
	this.setSearchString = function(newSearchString) {
		searchString = newSearchString;
	};
	
	this.getSearchString = function() {
		return searchString;
	};
 
});


