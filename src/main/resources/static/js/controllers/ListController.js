'use strict';
/**
 * List E-mails Controller
 */
var app = angular.module('fakewebmail.controllers');

app.controller('ListController', function($scope, $rootScope, $location, $interval, EmailsFactory){
	
	$scope.listAll = function(){
		EmailsFactory.query(function(response){
				$scope.emails = response ? response : [];
		  	},
			function(error){
				$rootScope.addError(error);
			}
		);
	}
	$scope.listAll();
	$interval(function(){ $scope.listAll(); },3000);
  
	$scope.view = function(id){
		var index = 0;
		$location.path('/view/'+id);
	}

});

