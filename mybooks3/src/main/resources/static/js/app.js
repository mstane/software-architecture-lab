var app = angular.module('hello', [ 'ngRoute', 'ngResource' ]);


app.config(function($routeProvider, $httpProvider) {
			$routeProvider.when('/home', {
				templateUrl : URLS.home,
				controller : 'home'
			}).when('/login', {
				templateUrl : URLS.login,
				controller : 'navigation'
			}).when('/register', {
				templateUrl : URLS.register
			}).when('/forgotten_password', {
				templateUrl : URLS.forgotten_password,
				controller : 'navigation'
			}).when('/books/list', {
				templateUrl : URLS.booksList,
				controller : 'BookController'
			}).when('/books/search', {
				templateUrl : URLS.booksSearch
			}).when('/books/view', {
				templateUrl : URLS.booksView
			}).when('/books/edit/:bookId', {
				templateUrl : URLS.booksEdit,
				controller : 'BookEditController'
			}).when('/books/create', {
				templateUrl : URLS.booksCreate,
				controller : 'BookController'
			}).when('/readers/list', {
				templateUrl : URLS.readersList					
			}).when('/readers/search', {
				templateUrl : URLS.readersSearch
			}).when('/readers/view', {
				templateUrl : URLS.readersView					
			}).when('/readers/edit', {
				templateUrl : URLS.readersEdit					
			}).when('/notes/view', {
				templateUrl : URLS.notesView					
			}).when('/notes/edit', {
				templateUrl : URLS.notesEdit					
			}).otherwise('/home');

			$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
		});





app.controller('navigation', function($rootScope, $scope, $http, $location, $route) {

	$scope.tab = function(route) {
		return $route.current && route === $route.current.controller;
	};

	var authenticate = function(credentials, callback) {

		var headers = credentials ? {
			authorization : "Basic "
					+ btoa(credentials.username + ":" + credentials.password)
		} : {};

		$http.get('user', {
			headers : headers
		}).success(function(data) {
			if (data.name) {
				$rootScope.authenticated = true;
			} else {
				$rootScope.authenticated = false;
			}
			callback && callback($rootScope.authenticated, data);
		}).error(function(data) {
			$rootScope.authenticated = false;
			callback && callback(false, data);
		});

	}

	authenticate();

	$scope.credentials = {};
	$scope.login = function() {
		authenticate($scope.credentials, function(authenticated, message) {
			if (authenticated) {
				console.log("Login succeeded")
				$location.path("/");
				$scope.error = false;
				$rootScope.authenticated = true;
			} else {
				console.log("Login failed")
				$location.path("/login");
				if (message) {
					$scope.error = message;
				} else {
					$scope.error = "There was a problem logging in. Please try again.";
				}
				$rootScope.authenticated = false;
			}
		})
	};

	$scope.logout = function() {
		$http.post('logout', {}).success(function() {
			$rootScope.authenticated = false;
			$location.path("/");
		}).error(function(data) {
			console.log("Logout failed");
			$rootScope.authenticated = false;
		});
	}
	
	$scope.forgottenPasswordSend = function() {
		$http.get('/forgotten_password_send', { params : { emailOrUsername : $scope.emailOrUsername } }).success(function() {
			console.log("Forgotten password succeeded");
		}).error(function(data) {
			console.log("Forgotten password failed");
		});
	}

});


app.controller('home', function($scope, $http) {
	$http.get('/resource/').success(function(data) {
		$scope.greeting = data;
	})
});



app.factory("BookFactory", function ($resource) {
    return $resource(URLS.booksRest, {id: "@id"}, {
        update: {
            method: 'PUT'
        }
    });
});


app.controller("BookController", function ($scope, BookFactory, $location) {
	
	$scope.genres=["Comedy", "Drama", "Epic"];
	
	function init() {
        $scope.getBooks();
    }


    $scope.getBooks = function () {
        $scope.books = BookFactory.query();
    };

    $scope.deleteBook = function (book) {
        return book.$delete({}, function () {
            $scope.books.splice($scope.books.indexOf(book), 1);
            alert("Successfully deleted.");
        });
    };

    $scope.createBook = function () {
		if($scope.book == null || $scope.book.title == null || $scope.book.author == null || $scope.book.url == null || $scope.book.genre == null){
			alert("Insufficient Data! Please provide valid values.");
		} else {
	        var book = new BookFactory($scope.book);
	        book.$save({}, function() {
	            $location.path("/books/list");
	            alert("Successfully created.");
	        });
		}    	
    };

    init();
});

app.controller("BookEditController", function ($scope, BookFactory, $location, $routeParams) {
	
	$scope.genres=["Comedy", "Drama", "Epic"];
	
    function init() {
    	$scope.book = BookFactory.get({id:$routeParams.bookId})
    }

    $scope.updateBook = function() {
       var book = new BookFactory($scope.book);
       book.$update().then(function() {
    	   $location.path("/books/list");
    	   alert("Successfully updated.");
       }) ;
    }
    init();
});


//app Directive for confirm dialog box
app.directive('ngConfirmClick', [
	function(){
         return {
             link: function (scope, element, attr) {
                 var msg = attr.ngConfirmClick || "Are you sure?";
                 var clickAction = attr.confirmedClick;
                 element.bind('click',function (event) {
                     if ( window.confirm(msg) ) {
                         scope.$eval(clickAction);
                     }
                 });
             }
         };
 }]);

