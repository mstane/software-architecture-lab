var app = angular.module('app', [ 
	'ngRoute', 
	
	'sharedServices', 
	'sharedDirectives',
	'sharedControllers', 
	
	'bookControllers',
	'profileControllers',
	'readerControllers',
	'noteControllers'
	
]);


app.config(function($routeProvider, $httpProvider) {
			$routeProvider.when(URLS.home, {
				templateUrl : '/static/app/html/partial/home.html'
			}).when(URLS.login, {
				templateUrl : '/static/app/html/partial/login.html' ,
				controller : 'navigation'
			}).when(URLS.register, {
				templateUrl : '/static/app/html/partial/register.html',
				controller : 'navigation'
			}).when(URLS.forgotten_password, {
				templateUrl : '/static/app/html/partial/forgotten_password.html',
				controller : 'navigation'
			}).when(URLS.profilesView, {
				templateUrl : '/static/app/html/partial/profiles/view.html',
				controller: 'ProfileController'
			}).when(URLS.profilesEdit, {
				templateUrl : '/static/app/html/partial/profiles/edit.html',
				controller: 'ProfileController'										
			}).when(URLS.booksList, {
				templateUrl : '/static/app/html/partial/books/list.html',
				controller : 'BookController'
			}).when(URLS.booksSearch, {
				templateUrl : '/static/app/html/partial/books/search.html',
				controller: 'BookController'
			}).when(URLS.booksView, {
				templateUrl : '/static/app/html/partial/books/view.html',
				controller : 'BookController'
			}).when(URLS.booksEdit, {
				templateUrl : '/static/app/html/partial/books/edit.html',
				controller : 'BookController'
			}).when(URLS.booksCreate, {
				templateUrl : '/static/app/html/partial/books/create.html',
				controller : 'BookController'
			}).when(URLS.readersList, {
				templateUrl : '/static/app/html/partial/readers/list.html',
				controller: 'ReaderController'
			}).when(URLS.readersSearch, {
				templateUrl : '/static/app/html/partial/readers/search.html',
				controller: 'ReaderController'
			}).when(URLS.readersView, {
				templateUrl : '/static/app/html/partial/readers/view.html',
				controller: 'ReaderController'
			}).when(URLS.readersEdit, {
				templateUrl : '/static/app/html/partial/readers/edit.html',
				controller: 'ReaderController'					
			}).when(URLS.notesView, {
				templateUrl : '/static/app/html/partial/notes/view.html',
				controller: 'NoteController'					
			}).when(URLS.notesEdit, {
				templateUrl : '/static/app/html/partial/notes/edit.html',
				controller: 'NoteController'
			}).otherwise('/home');

			$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
						
		});

/*
	Error handler when server doesn't respond and for all http client and server errors
 */
app.config(['$httpProvider', function($httpProvider) {
    $httpProvider.interceptors.push(['$q', '$location', '$rootScope', function($q, $location, $rootScope) {
       return {
         responseError: function(rejection) {
               if(rejection.status == 0) {
            	   $rootScope.currentReader = null;
//                   window.location = "#/";
            	   $location.path('/');
            	   alert("Connection with the server was lost.");
                   return;
               } else if(rejection.status > 399 && rejection.status < 600) {
            	   $rootScope.currentReader = null;
            	   $location.path('/');
            	   alert(rejection.statusText);
            	   return;
               }
               return $q.reject(rejection);
           }
       };
   }]);
}]);

/*
 * Data initialization
 */
app.run(function (AppService) {
	AppService.init();
});
