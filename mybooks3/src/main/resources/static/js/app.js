var app = angular.module('hello', [ 'ngRoute', 'ngResource', 'ui.bootstrap' ]);


app.config(function($routeProvider, $httpProvider) {
			$routeProvider.when('/home', {
				templateUrl : URLS.home
			}).when('/login', {
				templateUrl : URLS.login,
				controller : 'navigation'
			}).when('/register', {
				templateUrl : URLS.register,
				controller : 'navigation'
			}).when('/forgotten_password', {
				templateUrl : URLS.forgotten_password,
				controller : 'navigation'
			}).when('/profiles/view/:profileId?', {
				templateUrl : URLS.profilesView,
				controller: 'ProfileController'
			}).when('/profiles/edit/:profileId?', {
				templateUrl : URLS.profilesEdit,
				controller: 'ProfileController'										
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





app.controller('navigation', function($rootScope, $scope, $http, $location, $route, NotificationService) {

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
				$rootScope.currentReader = data.principal;
			} else {
				$rootScope.currentReader = null;
			}
			callback && callback($rootScope.currentReader, data);
		}).error(function(data) {
			$rootScope.currentReader = null;
			callback && callback(false, data);
		});

	}

	authenticate();

	$scope.credentials = {};
	$scope.login = function() {
		authenticate($scope.credentials, function(currentReader, message) {
			if (currentReader) {
				console.log("Login succeeded");
				$location.path("/");
			} else {
				console.log("Login failed");
				$location.path("/login");
				if (message) {
					NotificationService.statusBarError(message);
				} else {
					NotificationService.statusBarError("There was a problem logging in. Please try again.");
				}
				$rootScope.currentReader = null;
			}
		})
	};

	$scope.logout = function() {
		$http.post('logout', {}).success(function() {
			$rootScope.currentReader = null;
			$location.path("/");
		}).error(function(data) {
			console.log("Logout failed");
			$rootScope.currentReader = null;
		});
	}
	
	$scope.forgottenPasswordSend = function() {
		$http.get('/forgotten_password_send', { params : { emailOrUsername : $scope.emailOrUsername } }).success(function(data, status, headers, config) {
			NotificationService.statusBarSuccess(data.message);
			console.log("Forgotten password succeeded");
		}).error(function(data, status, headers, config) {
			NotificationService.statusBarError(data.message);
			console.log("Forgotten password failed");
		});
	}
	
	$scope.register = function() {
		$http.post('/register_reader', $scope.reader).success(function(data, status, headers, config) {
			$location.path("/");
			NotificationService.dialogBoxInfo("You have successfully registered.");
		}).error(function(data, status, headers, config) {
			NotificationService.statusBarError(data.message);
		});
	}
	

});


app.controller("ProfileController", function ($scope, $http, BookFactory, $location, $routeParams, $rootScope, NotificationService) {
	
	$scope.showView = function(id) {
		$location.path("/profiles/view/" + id);
	}
	
	function init() {
		$http.get('/rest/profiles/' + $routeParams.profileId, { }).success(function(data, status, headers, config) {
			$scope.profile = data;
		}).error(function(data, status, headers, config) {
			NotificationService.statusBarError(data.message);
		});
    }
	
	$scope.update = function() {
		$http.put('/rest/profiles/', $scope.profile).success(function(data, status, headers, config) {
			$rootScope.messageSuccess = "You have successfully updated your profile.";
			$location.path("/profiles/view/" + $routeParams.profileId);
		}).error(function(data, status, headers, config) {
			NotificationService.statusBarError(data.message);
		});
	}	
	
    init();
    
});



app.controller("ReaderController", function ($scope, $http, BookFactory, NotificationService, $location, $routeParams, $rootScope) {
	$scope.systemRoles = ["Admin", "Common"];
	
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
				$location.path("/readers/view/" + $routeParams.readerId);
		    	NotificationService.statusBarSuccessNextPage("You have successfully updated the reader.");
			} else {
				$location.path("/readers/view/" + data.id);
				NotificationService.statusBarSuccessNextPage("You have successfully created the reader.");
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
	
    $scope.deleteReaderOld = function () {
    	var bookFactory = new BookFactory($scope.book);
        return bookFactory.$delete({}, function () {
            alert("Successfully deleted.");
        });
    };
    
    $scope.deleteReaderOld = function(id) {
		$http.delete('/rest/readers/' + id).success(function(data, status, headers, config) {
			$rootScope.messageSuccess = "You have successfully deleted the reader.";
			$location.path("/readers/list/");
		}).error(function(data, status, headers, config) {
			$rootScope.messageError = data.message;
		});
    };

    $scope.deleteReader = function () {
        var custName = $scope.reader.firstName + ' ' + $scope.reader.lastName;

        var modalOptions = {
            closeButtonText: 'Cancel',
            actionButtonText: 'Delete Reader',
            headerText: 'Delete ' + custName + '?',
            bodyText: 'Are you sure you want to delete this reader with all his data?'
        };

        NotificationService.showModal({}, modalOptions).then(function (result) {
    		$http.delete('/rest/readers/' + $scope.reader.id).success(function(data, status, headers, config) {
        		$location.path(URLS.readersList);
                NotificationService.statusBarSuccessNextPage("You have successfully deleted the reader.");
    		}).error(function(data, status, headers, config) {
    			NotificationService.statusBarError(data.message);
    		});
        });
        
    };
 
    
	
    init();
    
});


app.factory("BookFactory", function ($resource) {
    return $resource(URLS.booksRest, {id: "@id"}, {
        update: {
            method: 'PUT'
        }
    });
});


app.controller("BookController", function ($scope, BookFactory, $location, $http, $rootScope, NotificationService) {
	
	$scope.genres = ["Comedy", "Drama", "Epic", "Erotic", "Lyric", "Mythopoeia", "Nonsense", "Other", "Romance", "Satire", "Tragedy", "Tragicomedy"];
	
	var pageSize = 5;
	
	function init() {
//        $scope.getBooks();
		$scope.getPage(0);
        var i = 0;
    }
	
	$scope.getPage = function (pageNumber) {
		$http.get('/rest/books/', { params: { pageNumber: pageNumber , pageSize : pageSize} }).success(function(data, status, headers, config) {
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
	
    $scope.getBooks = function () {
        $scope.books = BookFactory.query();
    };
	
	$scope.showView = function(id) {
		$location.path("/books/view/" + id);
	}

    $scope.createBook = function () {
        var book = new BookFactory($scope.book);
        book.$save({}, function() {
            $location.path("/books/list");
            NotificationService.dialogBoxInfo("Successfully created the book.");
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

app.controller("BookEditController", function ($scope, BookFactory, NoteService, NotificationService, $location, $routeParams) {
	
	$scope.genres=["Comedy", "Drama", "Epic", "Erotic", "Lyric", "Mythopoeia", "Nonsense", "Other", "Romance", "Satire", "Tragedy", "Tragicomedy"];
	
    function init() {
    	var book = BookFactory.get({id:$routeParams.bookId});
    	NoteService.setCurrentBook(book);
    	$scope.book = book;
    }

    $scope.updateBook = function() {
       var book = new BookFactory($scope.book);
       book.$update().then(function() {
    	   $location.path("/books/view/" + $scope.book.id);
    	   NotificationService.statusBarSuccessNextPage("Successfully updated the book.");
       }) ;
    }
    
    $scope.deleteBook = function () {
        var custName = $scope.book.title;

        var modalOptions = {
            closeButtonText: 'Cancel',
            actionButtonText: 'Delete Book',
            headerText: 'Delete ' + custName + '?',
            bodyText: 'Are you sure you want to delete this book?'
        };

        NotificationService.showModal({}, modalOptions).then(function (result) {
        	var bookFactory = new BookFactory($scope.book);
        	return bookFactory.$delete({}, function () {
        		$location.path(URLS.booksList);
                NotificationService.dialogBoxInfo("You have successfully deleted the book.");
            });
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

app.service("NoteService", function($http, $rootScope, $location){
	
	var currentBook;
	
	this.setCurrentBook = function(book) {
		currentBook = book;
	};
	
	this.getCurrentBook = function() {
		return currentBook;
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
    	
		return $http(request).then(function (status) {
            return status.data;
        });
    };
    
    this.delete = function(id) {
		return $http.delete('/rest/notes/' + id)
		.then(function (status) {
            return status.data;
        });
//		.success(function(data, status, headers, config) {
//			$rootScope.messageSuccess = "You have successfully deleted the note.";
//			$location.path("/notes/view/" + $routeParams.noteId);
//		}).error(function(data, status, headers, config) {
//			$rootScope.messageError = data.message;
//		});
    };
 
});


app.controller("NoteController", function ($scope, $http, $location, $routeParams, $rootScope, NoteService, NotificationService) {

	$scope.showView = function(id) {
		$location.path("/notes/view/" + id);
	}
	
	$scope.getCurrentBook = function() {
		return NoteService.getCurrentBook();
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
		NoteService.update(idSufix, $scope.note).then(function (result) {
			if (idSufix) {
				NotificationService.statusBarSuccessNextPage("You have successfully updated the note.");
				$location.path("/notes/view/" + $routeParams.noteId);
			} else {
				NotificationService.statusBarSuccessNextPage("You have successfully created the note.");
				$location.path("/notes/view/" + result.id);
			}
        }, processError);

	}	
	 
    $scope.deleteNote = function () {

        var custName = $scope.note.title + ' ' + $scope.note.content;

        var modalOptions = {
            closeButtonText: 'Cancel',
            actionButtonText: 'Delete Note',
            headerText: 'Delete ' + custName + '?',
            bodyText: 'Are you sure you want to delete this note?'
        };

        NotificationService.showModal({}, modalOptions).then(function (result) {
        	NoteService.delete($scope.note.id).then(function () {
        		$location.path("/books/view/" + NoteService.getCurrentBook().id);
                NotificationService.dialogBoxInfo("You have successfully deleted the note.");
            }, processError);
        });
    }
    
    function processError(error) {
    	console.log(error.message);
    }
	
    init();
    
	
});


app.service('NotificationService', ['$modal', '$rootScope',

    function ($modal, $rootScope) {
        var modalDefaults = {
        		backdrop: true,
        		keyboard: true,
        		modalFade: true,
        		templateUrl: 'modal'
        };

        var modalOptions = {
        		closeButtonText: 'Close',
//        		actionButtonText: 'OK',
        		headerText: 'MyBooks3',
        		bodyText: 'Perform this action?'
        };

      this.showModal = function (customModalDefaults, customModalOptions) {
          if (!customModalDefaults) customModalDefaults = {};
          customModalDefaults.backdrop = 'static';
          return this.show(customModalDefaults, customModalOptions);
      };

      this.show = function (customModalDefaults, customModalOptions) {
          //Create temp objects to work with since we're in a singleton service
          var tempModalDefaults = {};
          var tempModalOptions = {};

          //Map angular-ui modal custom defaults to modal defaults defined in service
          angular.extend(tempModalDefaults, modalDefaults, customModalDefaults);

          //Map modal.html $scope custom properties to defaults defined in service
          angular.extend(tempModalOptions, modalOptions, customModalOptions);

          if (!tempModalDefaults.controller) {
              tempModalDefaults.controller = function ($scope, $modalInstance) {
                  $scope.modalOptions = tempModalOptions;
                  $scope.modalOptions.ok = function (result) {
                      $modalInstance.close(result);
                  };
                  $scope.modalOptions.close = function (result) {
                      $modalInstance.dismiss('cancel');
                  };
              }
          }

          return $modal.open(tempModalDefaults).result;
      };
      
      
      this.dialogBoxInfo = function (message) {
          var customModalOptions = {
          		closeButtonText: 'Close',
          		bodyText: message
          };
    	  
          return this.showModal({}, customModalOptions);
      };
                  
	  	$rootScope.$on("$routeChangeStart", function (event, next, current) {
			if ($rootScope.messageSuccessOnNextPage) {
				$rootScope.messageSuccessOnNextPage = null;
			} else {
				$rootScope.messageSuccess = null;
			}
			if ($rootScope.messageErrorOnNextPage) {
				$rootScope.messageErrorOnNextPage = null;
			} else {
				$rootScope.messageError = null;
			}
		});

	  	this.statusBarSuccess = function (message) {
	  		$rootScope.messageSuccess = message;
	  	}
	  	
	  	this.statusBarSuccessNextPage = function (message) {
	  		$rootScope.messageSuccessOnNextPage = true;
            $rootScope.messageSuccess = message;
	  	}
	  	
	  	this.statusBarError = function (message) {
	  		$rootScope.messageError = message;
	  	}
	  	
	  	this.statusBarErrorNextPage = function (message) {
	  		$rootScope.messageErrorOnNextPage = true;
            $rootScope.messageError = message;
	  	}
	  	
  }]);
          
          
          

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
