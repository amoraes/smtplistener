'use strict';
/**
 * FakeWebmail UI Angular App Module
 */
angular.module('fakewebmail', 
		[ 'ngRoute', 
		  'ngSanitize',
		  'ui.bootstrap',
		  'fakewebmail.services', 
		  'fakewebmail.controllers' ])
  .config(function($routeProvider, $httpProvider){
	 $routeProvider
	 .when('/', { templateUrl : 'views/list.html', controller: 'ListController' })
	 .when('/view/:id', { templateUrl: 'views/view.html', controller: 'ViewController' })
	 .otherwise('/');
	 
	 
  });

