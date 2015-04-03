var app = angular.module("mybooks3App", ["ui.router", "ngResource"]);

app.config(function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("home");

    $stateProvider
        .state('home', {
            url:'/home',
            templateUrl: URLS.partialsList,
            controller: 'BookController'
        })
        .state('edit', {
            url:'/edit/:bookId',
            templateUrl: URLS.partialsEdit,
            controller: 'BookEditController'
        })
        .state('create', {
            url:'/create',
            templateUrl: URLS.partialsCreate,
            controller: 'BookController'
        });
});


app.factory("BookFactory", function ($resource) {
    return $resource(URLS.books, {id: "@id"}, {
        update: {
            method: 'PUT'
        }
    });
});

app.controller("BookController", function ($scope, BookFactory, $state) {
    function init() {
        $scope.getBooks();
    }


    $scope.getBooks = function () {
        $scope.books = BookFactory.query();
    };

    $scope.deleteBook = function (book) {
        return book.$delete({}, function () {
            $scope.books.splice($scope.books.indexOf(book), 1);
        });
    };

    $scope.createBook = function () {
        var book = new BookFactory($scope.book);
        book.$save({}, function() {
            $state.transitionTo("home");
        });
    };

    init();
});

app.controller("BookEditController", function ($scope, BookFactory, $state, $stateParams) {
    function init() {
        $scope.book = BookFactory.get({id:$stateParams.bookId})
    }

    $scope.updateBook = function() {
       var book = new BookFactory($scope.book);
       book.$update().then(function() {
           $state.transitionTo("home");
       }) ;
    }
    init();
});
