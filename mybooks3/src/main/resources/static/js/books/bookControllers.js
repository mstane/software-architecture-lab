var bookControllers = angular.module('bookControllers', ['bookFactories']);


bookControllers.controller("BookController", function ($scope, BookFactory, $location, $http, $rootScope, NotificationService) {
	
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



bookControllers.controller("BookEditController", function ($scope, BookFactory, NoteService, NotificationService, $location, $routeParams) {
	
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

