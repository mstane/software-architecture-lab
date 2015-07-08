var readerControllers = angular.module('readerControllers', []);

var systemRoles = ["Admin", "Common"];

var pageSize = 5;

readerControllers.controller("ReaderController", function ($scope, $http, BookFactory, NotificationService, $location, $routeParams, $rootScope) {
	$scope.systemRoles = systemRoles;
	
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
				NotificationService.statusBarError(data.message);
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
			NotificationService.statusBarError(data.message);
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
			NotificationService.statusBarError(data.message);
		});
	}
	
	$scope.search = function() {
		if ($scope.keyword) {
			$http.get('/rest/readers/search/' + $scope.keyword).success(function(data, status, headers, config) {
				$scope.readers = data;
			}).error(function (data, status, headers, config) {
				NotificationService.statusBarError(data.message);
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
			NotificationService.statusBarError(data.message);
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





