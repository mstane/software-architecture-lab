var bookControllers = angular.module('bookControllers', ['bookFactories']);

var genres = ["Comedy", "Drama", "Epic", "Erotic", "Lyric", "Mythopoeia", "Nonsense", "Other", "Romance", "Satire", "Tragedy", "Tragicomedy"];
var pageSize = 5;


bookControllers.controller("BookController", function ($scope, BookFactory, $location, $rootScope, NoteService, NotificationService, $routeParams) {
	
	$scope.genres = genres;
	
	function init() {
		var path = $location.$$path;
		
		if (path == URLS.booksList) {
			$scope.getPage(0);
		} else if (path == URLS.booksSearch) {
			
		} else if ($routeParams.bookId) {
	    	var book = BookFactory.get({id:$routeParams.bookId});
	    	NoteService.setCurrentBook(book);
	    	$scope.book = book;
		}
		
    }
	
    $scope.getPage = function (pageNumber) {
    	$scope.page = BookFactory.query({
        		pageNumber: pageNumber, pageSize : pageSize
        	}, function(data) {
                var pages = [];
                for(var i = 0; i <= data.totalPages - 1; i++) {
                    pages.push(i);
                }
                $scope.range = pages;
        	}, function(error) {
        		NotificationService.statusBarError(error.message);
        	});
    };
	
	$scope.showView = function(id) {
		$location.path("/books/view/" + id);
	}

	$scope.showNoteView = function(id) {
		$location.path("/notes/view/" + id);
	}
    
	
    $scope.createBook = function () {
        var book = new BookFactory($scope.book);
        book.$save({}, function() {
            $location.path("/books/list");
            NotificationService.dialogBoxInfo("Successfully created the book.");
        });
    };

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
    
    
    
	$scope.search = function() {
		if ($scope.keyword) {
			var params = { search: $scope.keyword };

			var genre = $scope.genre;
			if (genre) {
				params.genre = genre;
			}
			
			BookFactory.search(params, function(data) {
				$scope.searchItems = data;
        	}, function(error) {
        		NotificationService.statusBarError(error.statusText);
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
