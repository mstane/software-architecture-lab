var profileFactories = angular.module('profileFactories', ['ngResource']);

profileFactories.factory("ProfileFactory", function ($resource) {
    return $resource(URLS.profilesRest, {id: "@id"}, {
        update: { method: 'PUT' }
    });
});

