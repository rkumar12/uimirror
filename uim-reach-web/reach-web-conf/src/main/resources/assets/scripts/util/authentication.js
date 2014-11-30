'use strict';

/**
 * @ngdoc overview
 * @name uimReachWebApp
 * @description it's the main module for the reach application to check login activity.
 * # uimReachWebApp
 *
 * Main module of the application.
 */
var AutheticationService = angular.module('AutheticationService',['ngCookies']);
//where we will store the attempted url
AutheticationService.value('redirectToUrlAfterLogin', { url: '/' });

//this service will be responsible for authentication and also saving and redirecting to the attempt url when logging in
AutheticationService.factory('UIMRegister', function ($location,  $cookieStore, UIMRegisterApi, redirectToVerifyPage, $window, $rootScope) {
	var isValid =  function(user) {
		if(user.lasName)
			return true; //TODO convert value to bool
		return false
	};
	var redirectToVerifyPage = function() {
		console.log('redirecting'+URLS.base);
		//$location.path('/uim/reach/verify').replace();
		//$rootScope.$apply();
		//$window.location.href='/uim/reach/verify';
		//$scope.$apply();
		//$location.path(redirectToVerifyPage.url);
	};
	return {
		register: function (user) {
			console.log(user);
			delete user.password;
			delete user.confirmpassword;
			delete user.isAgreed;
			$cookieStore.put('user', user);
			redirectToVerifyPage();
			if(isValid(user)){
				if(UIMRegisterApi.register(user)){
					delete user.password;
					delete user.confirmpassword;
					delete user.isAgreed;
					$cookieStore.put('user', user);
					redirectToVerifyPage();
				}
			}
			
			var error = {'hasError':true,
					'msg':'Working'};
			return error; 
		}
		
	};
});

AutheticationService.factory('UIMAuthApi', function ($http) {
	  return {
		  authenticateFromCookie: function (user) {
			  //Write to cookie and return true, thats for latter and process registration
			  return true;
		  },
		  authenticate: function (user) {
			  //Write to cookie and return true, thats for latter and process registration
			  return true;
		  },
		  refreshToken: function (token){
			  
		  }
	  };
});