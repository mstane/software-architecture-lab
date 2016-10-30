var bookFactories = angular.module('bookFactories', ['ngResource']);

bookFactories.factory("BookFactory", function ($resource) {
    return $resource(URLS.booksRest, {id: "@id"}, {
        update: { method: 'PUT' },
        query:  { method: 'GET', isArray: false} //override default query method to be able to work with page
    });
});

bookFactories.service("BookService", function() {
	
	var searchString;
	var searchGenre;
	
	this.setSearchString = function(newSearchString) {
		searchString = newSearchString;
	};
	
	this.getSearchString = function() {
		return searchString;
	};
 
	this.setSearchGenre = function(newSearchGenre) {
		searchGenre = newSearchGenre;
	};
	
	this.getSearchGenre = function() {
		return searchGenre;
	};
 
});

