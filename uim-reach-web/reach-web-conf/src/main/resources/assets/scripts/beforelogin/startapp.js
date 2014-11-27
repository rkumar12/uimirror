'use strict';

/**
 * @ngdoc overview
 * @name uimReachWebApp
 * @description it's the main module for the reach application before login functionality.
 * # uimReachWebApp
 *
 * Main module of the application.
 */

var UIMReachBeforeLoginApp = angular.module('UIMReachBeforeLoginApp', 
		['UIMReachBeforeLoginCtrls', 'ngMessages', 'ui.bootstrap', 'cgNotify','sharedServices']);


var sharedServicesModule = angular.module('sharedServices',['ngCookies']);
//where we will store the attempted url
sharedServicesModule.value('redirectToUrlAfterLogin', { url: '/' });
sharedServicesModule.value('redirectToVerifyPage', { url: 'verify' });

//this service will be responsible for authentication and also saving and redirecting to the attempt url when logging in

sharedServicesModule.factory('UIMRegister', function ($location,  $cookieStore, UIMRegisterApi, redirectToVerifyPage, $window, $rootScope) {
	var isValid =  function(user) {
		if(user.lasName)
			return true; //TODO convert value to bool
		return false
	};
	var redirectToVerifyPage = function() {
		console.log('redirecting'+URL);
		//$location.path('/uim/reach/verify').replace();
		//$rootScope.$apply();
		$window.location.href='/uim/reach/verify';
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

sharedServicesModule.factory('UIMRegisterApi', function ($http) {
	  return {
		  register: function (user) {
			  //Write to cookie and return true, thats for latter and process registration
			  return true;
		  }
	  };
});