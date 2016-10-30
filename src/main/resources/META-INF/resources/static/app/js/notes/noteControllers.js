var noteControllers = angular.module('noteControllers', ['noteServices']);


noteControllers.controller("NoteController", function ($scope, $location, $routeParams, $rootScope, NoteService, NoteFactory, NotificationService) {

	$scope.showView = function(id) {
		$location.path("/notes/view/" + id);
	}
	
	$scope.getCurrentBook = function() {
		return NoteService.getCurrentBook();
	}
	
	function init() {
		if ($routeParams.noteId) {
			$scope.note = NoteFactory.get({id:$routeParams.noteId});
		}
    }
	
	$scope.update = function() {
        var noteFactory = new NoteFactory($scope.note);
        
        if ($routeParams.noteId) {
            noteFactory.$update({ bookId: NoteService.getCurrentBook().id }).then(function(result) {
				NotificationService.statusBarSuccessNextPage("You have successfully updated the note.");
				$location.path("/notes/view/" + $routeParams.noteId);
            }, processError);
        } else {
            noteFactory.$save({ bookId: NoteService.getCurrentBook().id }).then(function(result) {
				NotificationService.statusBarSuccessNextPage("You have successfully created the note.");
				$location.path("/notes/view/" + result.id);
            }, processError);
        }

	}	
	 
    $scope.deleteNote = function () {

        var custName = $scope.note.title;

        var modalOptions = {
            closeButtonText: 'Cancel',
            actionButtonText: 'Delete Note',
            headerText: 'Delete ' + custName + '?',
            bodyText: 'Are you sure you want to delete this note?'
        };

        NotificationService.showModal({}, modalOptions).then(function (result) {
         	var noteFactory = new NoteFactory($scope.note);
         	return noteFactory.$delete({}, function () {
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

