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
				templateUrl : URLS.booksSearch,
				controller: 'BookController'
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
	
    $scope.deleteReader = function () {
    	var bookFactory = new BookFactory($scope.book);
        return bookFactory.$delete({}, function () {
            alert("Successfully deleted.");
        });
    };
    
    $scope.deleteReader = function(id) {
		$http.delete('/rest/readers/' + id).success(function(data, status, headers, config) {
			$rootScope.messageSuccess = "You have successfully deleted the reader.";
			$location.path("/readers/list/");
		}).error(function(data, status, headers, config) {
			$rootScope.messageError = data.message;
		});
    };

	
	
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


app.controller("BookController", function ($scope, BookFactory, $location, $http, $rootScope) {
	
	$scope.genres = ["Comedy", "Drama", "Epic", "Erotic", "Lyric", "Mythopoeia", "Nonsense", "Other", "Romance", "Satire", "Tragedy", "Tragicomedy"];
	
	function init() {
        $scope.getBooks();
        var i = 0;
    }
	
	$scope.showView = function(id) {
		$location.path("/books/view/" + id);
	}

    $scope.getBooks = function () {
        $scope.books = BookFactory.query();
    };

    $scope.createBook = function () {
        var book = new BookFactory($scope.book);
        book.$save({}, function() {
            $location.path("/books/list");
            alert("Successfully created.");
        });
    };
    
	$scope.search = function() {
		if ($scope.keyword) {
			var genre = $scope.genre;
			
			var params = genre ? { params: { genre: genre } } : {};
			
			$http.get('/rest/books/search/' + $scope.keyword, params).success(function(data, status, headers, config) {
				$scope.searchItems = data;
			}).error(function (data, status, headers, config) {
				$rootScope.messageError = data.message;
			}); 
		}
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

app.controller("BookEditController", function ($scope, BookFactory, NoteService, $location, $routeParams) {
	
	$scope.genres=["Comedy", "Drama", "Epic", "Erotic", "Lyric", "Mythopoeia", "Nonsense", "Other", "Romance", "Satire", "Tragedy", "Tragicomedy"];
	
    function init() {
    	var book = BookFactory.get({id:$routeParams.bookId});
    	NoteService.setCurrentBook(book);
    	$scope.book = book;
    }

    $scope.updateBook = function() {
       var book = new BookFactory($scope.book);
       book.$update().then(function() {
    	   $location.path("/books/list");
    	   alert("Successfully updated.");
       }) ;
    }
    
    $scope.deleteBook = function () {
    	var bookFactory = new BookFactory($scope.book);
        return bookFactory.$delete({}, function () {
            alert("Successfully deleted.");
        });
    };
    
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

app.service("NoteService", function($http, $rootScope){
	
	var currentBook;
	
	this.setCurrentBook = function(book) {
		currentBook = book;
	};
	
	this.get = function(id, callback) { 
		$http.get('/rest/notes/' + id, { }).success(function(data, status, headers, config) {
			callback && callback(data);
		}).error(function(data, status, headers, config) {
			$rootScope.messageError = data.message;
		});
    };
    
    this.update = function(idSufix, note) {
    	var request = {
    			method: 'PUT',
    			url: '/rest/notes/' + idSufix,
    			params: { bookId: currentBook.id },
    			data: note
    		}
    	
		$http(request).success(function(data, status, headers, config) {
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
    };
    
    this.delete = function(id) {
		$http.delete('/rest/notes/' + id).success(function(data, status, headers, config) {
			$rootScope.messageSuccess = "You have successfully deleted the note.";
			$location.path("/notes/view/" + $routeParams.noteId);
		}).error(function(data, status, headers, config) {
			$rootScope.messageError = data.message;
		});
    };
 
});


app.controller("NoteController", function ($scope, $http, $location, $routeParams, $rootScope, NoteService) {

	$scope.showView = function(id) {
		$location.path("/notes/view/" + id);
	}
	
	function init() {
		if ($routeParams.noteId) {
			 NoteService.get($routeParams.noteId, function(data) { 
				 $scope.note = data; 
			 });
		}
    }
	
	$scope.update = function() {
		var idSufix = $routeParams.noteId;
		if (!idSufix) idSufix = "";
		NoteService.update(idSufix, $scope.note);

	}	
	
	$scope.deleteNote = function (id) {
		NoteService.delete(id);
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
