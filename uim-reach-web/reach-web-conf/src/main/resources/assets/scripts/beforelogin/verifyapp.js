'use strict';

/**
 * @ngdoc overview
 * @name uimReachWebApp
 * @description it's the main module for the reach application before login functionality.
 * # uimReachWebApp
 *
 * Main module of the application.
 */

var UIMReachVerifyApp = angular.module('UIMReachVerifyApp', 
		['UIMReachVerifyCtrls', 'ngMessages', 'ui.bootstrap', 'cgNotify','sharedServices']);


var sharedServicesModule = angular.module('sharedServices',['ngCookies']);
//where we will store the attempted url
sharedServicesModule.value('redirectToUrlAfterLogin', { url: '/' });
sharedServicesModule.value('redirectToVerifyPage', { url: 'verify' });

//this service will be responsible for authentication and also saving and redirecting to the attempt url when logging in

sharedServicesModule.factory('UIMTempUser', function ($location,  $cookieStore, UIMVerifyApi, redirectToVerifyPage, $window, $rootScope) {
	var user = {};
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
		getUser: function () {
			if(!user.email)
				user = $cookieStore.get('user');
			return user; 
		},
		replcaeUserCookie: function(user){
			$cookieStore.put('user', user);
		}
		
	};
});

sharedServicesModule.factory('UIMVerifyApi', function ($http) {
	  return {
		  verify: function (user) {
			  //Write to cookie and return true, thats for latter and process registration
			  return true;
		  }
	  };
});