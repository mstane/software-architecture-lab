var noteControllers = angular.module('noteControllers', ['noteServices']);


noteControllers.controller("NoteController", function ($scope, $http, $location, $routeParams, $rootScope, NoteService, NotificationService) {

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

