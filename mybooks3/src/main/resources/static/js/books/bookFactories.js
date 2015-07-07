var bookFactories = angular.module('bookFactories', ['ngResource']);

bookFactories.factory("BookFactory", function ($resource) {
    return $resource(URLS.booksRest, {id: "@id"}, {
        update: { method: 'PUT' },
        query:  { method: 'GET', isArray: false}, //override default query method to be able to work with page
        search: { method: 'GET', isArray: true}
    });
});

