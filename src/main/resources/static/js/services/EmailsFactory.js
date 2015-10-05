'use strict';
var app = angular.module('fakewebmail.services');
/**
 * Factory to get data from REST (Backend)
 */
app.factory('EmailsFactory', function($resource){
	return $resource('emails/:id', 
			{ id: '@id' },
			{ update: { method: 'PUT' }}
	);
});