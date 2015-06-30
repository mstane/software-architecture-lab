var profileControllers = angular.module('profileControllers', ['bookFactories']);


profileControllers.controller("ProfileController", function ($scope, $http, BookFactory, $location, $routeParams, $rootScope, NotificationService) {
	
	$scope.showView = function(id) {
		$location.path("/profiles/view/" + id);
	}
	
	function init() {
		$http.get('/rest/profiles/' + $routeParams.profileId, { }).success(function(data, status, headers, config) {
			$scope.profile = data;
		}).error(function(data, status, headers, config) {
			NotificationService.statusBarHttpError(data, status);
		});
    }
	
	$scope.update = function() {
		$http.put('/rest/profiles/', $scope.profile).success(function(data, status, headers, config) {
			$rootScope.messageSuccess = "You have successfully updated your profile.";
			$location.path("/profiles/view/" + $routeParams.profileId);
		}).error(function(data, status, headers, config) {
			NotificationService.statusBarHttpError(data, status);
		});
	}	
	
    init();
    
});

