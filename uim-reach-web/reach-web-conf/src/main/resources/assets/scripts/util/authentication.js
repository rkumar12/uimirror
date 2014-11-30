'use strict';

/**
 * Has set of API that checks if the user is really authenticated or not,
 * This has set of API, which will be used by diffrent app, such as login page will check if user is logged in 
 * then redirect to home page.
 * and from the home page if not it will return to login page
 * @ngdoc overview
 * @name uimReachWebApp
 * @description it's the main module for the reach application to check login activity.
 * # uimReachWebApp
 *
 * Main module of the application.
 */
var AutheticationService = angular.module('AutheticationService',['ngCookies']);

//Utility URLS, such as home etc
URLS.home=URLS.base;

//this service will be responsible for authentication and also saving and redirecting to the attempt url when logging in
AutheticationService.factory('UIMAuthServ', function ($location,  $cookieStore, UIMAuthApi, redirectToVerifyPage, $window, $rootScope) {
	return {
		authenticate: function (cred) {
			//Write to cookie and return true, thats for latter and process registration
			return UIMAuthApi.authenticate(cred);
		},
		isLoggedIn: function () {
			var loggedIn = false;
			var token = $cookieStore.get('_tkn');
			if(token)
				loggedIn = UIMAuthApi.authenticateFromCookie(token);
			console.log("coming for the authentication check");
			return loggedIn;
		},
		redirectToUrl: function(loc) {
			loc = loc || URLS.home;
			$window.location.href=loc;
		},
		saveAttemptUrl: function() {
			if($location.path().toLowerCase() != '/login' || $location.path().toLowerCase() != '/index') {
				URLS.redirectAfterLogin = $location.path();
			}
		},
		redirectToAttemptedUrl: function() {
			redirectToUrl(URLS.redirectAfterLogin);
		}
	};
});

AutheticationService.factory('UIMAuthApi', function ($http, $q) {
	var validation_err = {_code: '403', _msg: 'Test'};
	var isValidCred =  function(cred) {
		var valid = false;
		if(!cred || !cred.UserName || !cred.Password){
			console.log('Validation msg');
			validation_err._msg= 'Invalid Identity.';
			valid = true;
		}
		console.log(valid);
		return valid ? valid : validation_err;
	};
	return {
		authenticateFromCookie: function (token) {
			//Write to cookie and return true, thats for latter and process registration
			return true;
		},
		authenticate: function (cred) {
			var validation_msg = isValidCred(cred);
			if(validation_msg != true){
				return $q.reject(validation_msg);
			}
			return true;
		},
		refreshToken: function (token){
			//Write to cookie and return true, thats for latter and process registration
			return true;
		}
	};
});