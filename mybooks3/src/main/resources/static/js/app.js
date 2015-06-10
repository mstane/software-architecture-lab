var app = angular.module('hello', [ 'ngRoute', 'ngResource', 'ui.bootstrap' ]);


app.config(function($routeProvider, $httpProvider) {
			$routeProvider.when('/home', {
				templateUrl : URLS.home,
				controller : 'home'
			}).when('/login', {
				templateUrl : URLS.login,
				controller : 'navigation'
			}).when('/register', {
				templateUrl : URLS.register,
				controller : 'navigation'
			}).when('/forgotten_password', {
				templateUrl : URLS.forgotten_password,
				controller : 'navigation'
			}).when('/books/list', {
				templateUrl : URLS.booksList,
				controller : 'BookController'
			}).when('/books/search', {
				templateUrl : URLS.booksSearch
			}).when('/books/view/:bookId?', {
				templateUrl : URLS.booksView,
				controller : 'BookEditController'
			}).when('/books/edit/:bookId', {
				templateUrl : URLS.booksEdit,
				controller : 'BookEditController'
			}).when('/books/create', {
				templateUrl : URLS.booksCreate,
				controller : 'BookController'
			}).when('/readers/list', {
				templateUrl : URLS.readersList,
				controller: 'ReaderController'
			}).when('/readers/search', {
				templateUrl : URLS.readersSearch,
				controller: 'ReaderController'
			}).when('/readers/view/:readerId?', {
				templateUrl : URLS.readersView,
				controller: 'ReaderController'
			}).when('/readers/edit/:readerId?', {
				templateUrl : URLS.readersEdit,
				controller: 'ReaderController'					
			}).when('/notes/view/:noteId?', {
				templateUrl : URLS.notesView,
				controller: 'NoteController'					
			}).when('/notes/edit/:noteId?', {
				templateUrl : URLS.notesEdit,
				controller: 'NoteController'					
			}).otherwise('/home');

			$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
		});





app.controller('navigation', function($rootScope, $scope, $http, $location, $route) {
	
	$rootScope.messageSuccess = null;
	$rootScope.messageError = null;

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
				$rootScope.currentReader = data.principal;
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
				$rootScope.messageError = null;
				$rootScope.authenticated = true;
			} else {
				console.log("Login failed")
				$location.path("/login");
				if (message) {
					$rootScope.messageError = message;
				} else {
					$rootScope.messageError = "There was a problem logging in. Please try again.";
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
		$http.get('/forgotten_password_send', { params : { emailOrUsername : $scope.emailOrUsername } }).success(function(data, status, headers, config) {
			$rootScope.messageSuccess = data.message;
			console.log("Forgotten password succeeded");
		}).error(function(data, status, headers, config) {
			$rootScope.messageError = data.message;
			console.log("Forgotten password failed");
		});
	}
	
	$scope.register = function() {
		$http.post('/register_reader', $scope.reader).success(function(data, status, headers, config) {
			$rootScope.messageSuccess = data.message;
			console.log("Register succeeded");
			$location.path("/");
		}).error(function(data, status, headers, config) {
			$rootScope.messageError = data.message;
			console.log("Register failed");
		});
	}

});

app.controller("ReaderController", function ($scope, $http, BookFactory, $location, $routeParams, $rootScope) {
	var pageSize = 5;
	
	$scope.showView = function(id) {
		$location.path("/readers/view/" + id);
	}
	
	function init() {
		var path = $location.$$path;
		
		if (path == URLS.readersList) {
			$scope.getPage(0);
		} else if ($routeParams.readerId) {
			$http.get('/rest/readers/' + $routeParams.readerId, { }).success(function(data, status, headers, config) {
				$scope.reader = data;
			}).error(function(data, status, headers, config) {
				$rootScope.messageError = data.message;
			});
		}
    }
	
	$scope.getPage = function (pageNumber) {
		$http.get('/rest/readers/', { params: { pageNumber: pageNumber , pageSize : pageSize} }).success(function(data, status, headers, config) {
			$scope.page = data;
			
            var pages = [];
            for(var i = 0; i <= data.totalPages - 1; i++) {
                pages.push(i);
            }
            $scope.range = pages;
			
		}).error(function(data, status, headers, config) {
			$rootScope.messageError = data.message;
		});
	}
	
	$scope.update = function() {
		var idSufix = $routeParams.readerId;
		if (!idSufix) idSufix = "";
		$http.put('/rest/readers/' + idSufix, $scope.reader).success(function(data, status, headers, config) {
			if (idSufix) {
				$rootScope.messageSuccess = "You have successfully updated the profile.";
				$location.path("/readers/view/" + $routeParams.readerId);
			} else {
				$rootScope.messageSuccess = "You have successfully created the profile.";
				$location.path("/readers/view/" + data.id);
			}
		}).error(function(data, status, headers, config) {
			$rootScope.messageError = data.message;
		});
	}
	
	$scope.search = function() {
		if ($scope.keyword) {
			$http.get('/rest/readers/search/' + $scope.keyword).success(function(data, status, headers, config) {
				$scope.readers = data;
			}).error(function (data, status, headers, config) {
				$rootScope.messageError = data.message;
			}); 
		}
	}
	
	
    init();
    
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
	
	$scope.genres = ["Comedy", "Drama", "Epic", "Erotic", "Lyric", "Mythopoeia", "Nonsense", "Other", "Romance", "Satire", "Tragedy", "Tragicomedy"];
	
	function init() {
        $scope.getBooks();
    }
	
	$scope.showView = function(id) {
		$location.path("/books/view/" + id);
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
        var book = new BookFactory($scope.book);
        book.$save({}, function() {
            $location.path("/books/list");
            alert("Successfully created.");
        });
    };
    
    
    
    $scope.today = function() {
        $scope.dt = new Date();
    };
    $scope.today();

    $scope.clear = function () {
      $scope.dt = null;
    };

    $scope.openStartReadingDate = function($event) {
        $event.preventDefault();
        $event.stopPropagation();

        $scope.openedStartReadingDate = true;
    };

    $scope.openEndReadingDate = function($event) {
        $event.preventDefault();
        $event.stopPropagation();

        $scope.openedEndReadingDate = true;
    };


    $scope.format = 'dd-MMMM-yyyy';

    
    
    
    init();
});

app.controller("BookEditController", function ($scope, BookFactory, $location, $routeParams) {
	
	$scope.genres=["Comedy", "Drama", "Epic", "Erotic", "Lyric", "Mythopoeia", "Nonsense", "Other", "Romance", "Satire", "Tragedy", "Tragicomedy"];
	
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
    
	$scope.showNoteView = function(id) {
		$location.path("/notes/view/" + id);
	}
    
    
    
    $scope.today = function() {
        $scope.dt = new Date();
    };
    $scope.today();

    $scope.clear = function () {
      $scope.dt = null;
    };

    $scope.openStartReadingDate = function($event) {
        $event.preventDefault();
        $event.stopPropagation();

        $scope.openedStartReadingDate = true;
    };

    $scope.openEndReadingDate = function($event) {
        $event.preventDefault();
        $event.stopPropagation();

        $scope.openedEndReadingDate = true;
    };


    $scope.format = 'dd-MMMM-yyyy';
    
    
    init();
});


app.controller("NoteController", function ($scope, $http, $location, $routeParams, $rootScope) {

	$scope.showView = function(id) {
		$location.path("/notes/view/" + id);
	}
	
	function init() {
		if ($routeParams.noteId) {
			$http.get('/rest/notes/' + $routeParams.noteId, { }).success(function(data, status, headers, config) {
				$scope.note = data;
			}).error(function(data, status, headers, config) {
				$rootScope.messageError = data.message;
			});
		}
    }
	
	$scope.update = function() {
		var idSufix = $routeParams.noteId;
		if (!idSufix) idSufix = "";
		$http.put('/rest/notes/' + idSufix, $scope.note).success(function(data, status, headers, config) {
			if (idSufix) {
				$rootScope.messageSuccess = "You have successfully updated the profile.";
				$location.path("/notes/view/" + $routeParams.noteId);
			} else {
				$rootScope.messageSuccess = "You have successfully created the profile.";
				$location.path("/notes/view/" + data.id);
			}
		}).error(function(data, status, headers, config) {
			$rootScope.messageError = data.message;
		});
	}	
	
	$scope.deleteNote = function (id) {
		$http.delete('/rest/notes/' + id).success(function(data, status, headers, config) {
			$rootScope.messageSuccess = "You have successfully deleted the note.";
			$location.path("/notes/view/" + $routeParams.noteId);
		}).error(function(data, status, headers, config) {
			$rootScope.messageError = data.message;
		});
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

app.directive('compareTo', [
	function() {
		return {
			require: "ngModel",
			scope: { otherModelValue: "=compareTo" },
      link: function(scope, element, attributes, ngModel) {
    	  		ngModel.$validators.compareTo = function(modelValue) {
    	  			return modelValue == scope.otherModelValue;
    	  		};
    	  		scope.$watch("otherModelValue", function() {
    	  			ngModel.$validate();
    	  		});
      }
    };
  }
]);
