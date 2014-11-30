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
		['UIMReachVerifyCtrls', 'ngMessages', 'ui.bootstrap', 'cgNotify','AutheticationService', 'sharedServices']);


//When the app loads
AutheticationService.run(function ($location, UIMAuthServ) {
  if (UIMAuthServ.isLoggedIn()) {
	  UIMAuthServ.redirectToUrl();
  }
});

//Verify module has two service, 1- change email and 2- verify account
var sharedServicesModule = angular.module('sharedServices',['ipCookie']);

//this service will be responsible for authentication and also saving and redirecting to the attempt url when logging in

sharedServicesModule.factory('UIMTempUser', function ($location,  ipCookie, $window) {
	var user = {};
	var redirectToHomePage = function() {
		console.log('redirecting'+URL);
		$window.location.href=URLS.base;
	};
	return {
		getUser: function () {
			if(!user.email)
				user = ipCookie('user');
			return user; 
		},
		replcaeUserCookie: function(email){
			if(!user.email){
				user = this.getUser();
			}
			user.email = email;
			ipCookie('user', user);
		},
		redirectToLogin: function(){
			$window.location.href=URLS.base+'login';
		}
	};
});

sharedServicesModule.factory('UIMVerifyApi', function ($http, ipCookie) {
	var formatData =  function(code) {
		var _data = {};
		_data.code = code;
		_data.token = ipCookie('_uim_tmp_tkn');
		return _data;
	};
	var formatDataForNewEmail =  function(newEmail) {
		var _data = {};
		_data.code = code;
		_data.email = newEmail;
		return _data;
	};
	return {
		verify: function (code) {
			return $http({
                method: "get",
                url: "http://uimirror.com",
                //transformRequest: transformRequestAsFormPost,
                data: formatData(code)
            }).then(function(response) {
                if(typeof response.data === 'object') {
                    return response.data;
                }else {
                    // invalid response
                    return $q.reject(response.data);
                }
            },function(response) {
            	return {token:'1'};
                //TODO uncomment latter
                //return $q.reject(response.data);
        	});  
		},
		changeEmail: function(newEmail){
			return $http({
                method: "get",
                url: "http://uimirror.com",
                //transformRequest: transformRequestAsFormPost,
                data: formatDataForNewEmail(newEmail)
            }).then(function(response) {
                if(typeof response.data === 'object') {
                    return response.data;
                }else {
                    // invalid response
                    return $q.reject(response.data);
                }
            },function(response) {
            	return {status:'success'};
                //TODO uncomment latter
                //return $q.reject(response.data);
        	});
		},
		deleteTempCookie: function(){
			ipCookie.remove('_uim_tmp_tkn');
		}
	};
});