'use strict';
/**
 * View E-mail Controller 
 */
var app = angular.module('fakewebmail.controllers');

app.controller('ViewController', function($scope, $rootScope, $location, $routeParams, EmailsFactory, $sanitize){
	$scope.email = EmailsFactory.get({id:$routeParams.id}, function(){
		$scope.showHtml = true;
		if($scope.email.htmlBody == null || $scope.email.htmlBody == ''){
			$scope.showHtml = false;
		}	
		$scope.showAttachments = false;
		if($scope.email.attachments != null && $scope.email.attachments.length > 0){
			$scope.showAttachments = true;	
		}
		$scope.email.$update();
	}); 
	
	
	
	$scope.switchTo = function(body){
		if(body == 'html')
			$scope.showHtml = true;
		else
			$scope.showHtml = false;
	}
	
	
	$scope.back = function(){
		$location.path('/list');
	}
	
});

