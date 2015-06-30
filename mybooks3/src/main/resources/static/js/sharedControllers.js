var sharedControllers = angular.module('sharedControllers', []);



sharedControllers.controller('navigation', function($rootScope, $scope, $http, $location, $route, NotificationService) {

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

