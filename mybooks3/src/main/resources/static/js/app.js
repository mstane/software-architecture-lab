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
			$routeProvider.when('/home', {
				templateUrl : URLS.home
			}).when('/login', {
				templateUrl : URLS.login,
				controller : 'navigation'
			}).when('/register', {
				templateUrl : URLS.register,
				controller : 'navigation'
			}).when('/forgotten_password', {
				templateUrl : URLS.forgotten_password,
				controller : 'navigation'
			}).when('/profiles/view/:profileId?', {
				templateUrl : URLS.profilesView,
				controller: 'ProfileController'
			}).when('/profiles/edit/:profileId?', {
				templateUrl : URLS.profilesEdit,
				controller: 'ProfileController'										
			}).when('/books/list', {
				templateUrl : URLS.booksList,
				controller : 'BookController'
			}).when('/books/search', {
				templateUrl : URLS.booksSearch,
				controller: 'BookController'
			}).when('/books/view/:bookId?', {
				templateUrl : URLS.booksView,
				controller : 'BookController'
			}).when('/books/edit/:bookId', {
				templateUrl : URLS.booksEdit,
				controller : 'BookController'
			}).when('/books/create', {
				templateUrl : URLS.booksCreate,
				controller : 'BookController'
			}).when('/readers/list', {
				templateUrl : URLS.readersList,
				controller: 'ReaderController'
			}).when('/readers/search', {
				templateUrl : URLS.readersSearch,
				controller: 'ReaderController'
			}).when('/readers/view/:readerId?', {
				templateUrl : URLS.readersView,
				controller: 'ReaderController'
			}).when('/readers/edit/:readerId?', {
				templateUrl : URLS.readersEdit,
				controller: 'ReaderController'					
			}).when('/notes/view/:noteId?', {
				templateUrl : URLS.notesView,
				controller: 'NoteController'					
			}).when('/notes/edit/:noteId?', {
				templateUrl : URLS.notesEdit,
				controller: 'NoteController'
			}).otherwise('/home');

			$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
						
		});

/*
	Error handler when server doesn't respond
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
               }
               return $q.reject(rejection);
           }
       };
   }]);
}]);
