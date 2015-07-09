var readerFactories = angular.module('readerFactories', ['ngResource']);

readerFactories.factory("ReaderFactory", function ($resource) {
    return $resource(URLS.readersRest, {id: "@id"}, {
        update: { method: 'PUT' },
        query:  { method: 'GET', isArray: false}, //override default query method to be able to work with page
        search: { method: 'GET', isArray: true}
    });
});

