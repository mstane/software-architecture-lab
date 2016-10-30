var noteServices = angular.module('noteServices', ['ngResource']);


noteServices.service("NoteService", function() {
	
	var currentBook;
	
	this.setCurrentBook = function(book) {
		currentBook = book;
	};
	
	this.getCurrentBook = function() {
		return currentBook;
	};
 
});


noteServices.factory("NoteFactory", function ($resource) {
    return $resource(URLS.notesRest, {id: "@id"}, {
        update: { method: 'PUT' },
    });
});