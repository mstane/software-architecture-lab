var profileControllers = angular.module('profileControllers', ['profileFactories']);


profileControllers.controller("ProfileController", function ($scope, ProfileFactory, $location, $routeParams, $rootScope, NotificationService) {
	
	$scope.showView = function(id) {
		$location.path("/profiles/view/" + id);
	}
	
	function init() {
		if ($routeParams.profileId) {
			$scope.profile = ProfileFactory.get({id:$routeParams.profileId});
		}
    }
	
	$scope.update = function() {
        var profileFactory = new ProfileFactory($scope.profile);
        profileFactory.$update().then(function(result) {
			$rootScope.messageSuccess = "You have successfully updated your profile.";
			$location.path("/profiles/view/" + $routeParams.profileId);
        }, processError);
	}
	
	
    function processError(error) {
    	console.log(error.message);
    }
	
    init();
    
});

