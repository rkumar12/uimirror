'use strict';

/**
 * @ngdoc overview
 * @name oauth2
 * @description
 * # oauth2-conf
 *
 * Util module of the application.
 */

var UIMUtilModule = angular.module('UIMUtil', []).service('uimUtil', function() {
	   // The API
	this.isValidForm = validateForm;
});

//UIMUtil.factory('formValidationService', ValidationService);

var validateForm = function(field, validation) {
	console.log(validation+field.$error[validation]);
	if(validation){
	     return (field.$dirty && field.$error[validation]) || field.$error[validation];
	}
	return (field.$dirty && field.$invalid) || field.$invalid;
}

