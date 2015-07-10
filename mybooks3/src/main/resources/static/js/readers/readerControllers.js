var readerControllers = angular.module('readerControllers', ['readerFactories']);

readerControllers.controller("ReaderController", function ($scope, ReaderFactory, NotificationService, AppService, $location, $routeParams, $rootScope) {
	
	$scope.systemRoles = AppService.getSystemRoles();
	
	$scope.showView = function(id) {
		$location.path("/readers/view/" + id);
	}
	
	function init() {
		var path = $location.$$path;
		
		if (path == URLS.readersList) {
			$scope.getPage(0);
		} else if (path == URLS.readersSearch) {
						
		} else if ($routeParams.readerId) {
	    	var reader = ReaderFactory.get({id:$routeParams.readerId});
	    	$scope.reader = reader;
		}
    }

    $scope.getPage = function (pageNumber) {
    	$scope.page = ReaderFactory.query({
        		pageNumber: pageNumber, pageSize : AppService.getPageSize()
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
		
    $scope.update = function() {
        var reader = new ReaderFactory($scope.reader);
        reader.$update().then(function() {
     	   $location.path("/readers/view/" + $scope.reader.id);
     	   NotificationService.statusBarSuccessNextPage("Successfully updated the reader.");
        });
    }

	$scope.search = function() {
		if ($scope.keyword) {
			var params = { search: $scope.keyword };
			
			ReaderFactory.search(params, function(data) {
				$scope.readers = data;
        	}, function(error) {
        		NotificationService.statusBarError(error.statusText);
        	});
			
		}
	}

    $scope.deleteReader = function () {
        var custName = $scope.reader.firstName + ' ' + $scope.reader.lastName;

        var modalOptions = {
            closeButtonText: 'Cancel',
            actionButtonText: 'Delete Reader',
            headerText: 'Delete ' + custName + '?',
            bodyText: 'Are you sure you want to delete this reader with all his data?'
        };

        NotificationService.showModal({}, modalOptions).then(function (result) {
        	var readerFactory = new ReaderFactory($scope.reader);
         	return readerFactory.$delete({}, function () {
         		$location.path(URLS.readersList);
                 NotificationService.dialogBoxInfo("Successfully deleted the reader.");
             });
        });
        
    };
	
    init();
    
});





