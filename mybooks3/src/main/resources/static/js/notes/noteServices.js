var noteServices = angular.module('noteServices', []);


noteServices.service("NoteService", function($http, $rootScope, $location){
	
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

