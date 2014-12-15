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
var AutheticationService = angular.module('AutheticationService',['ipCookie']);

//Utility URLS, such as home etc
URLS.home=URLS.base;

//this service will be responsible for authentication and also saving and redirecting to the attempt url when logging in
AutheticationService.factory('UIMAuthServ', function ($location,  ipCookie, UIMAuthApi, $window) {
	return {
		authenticate: function (cred) {
			//Write to cookie and return true, thats for latter and process registration
			return UIMAuthApi.authenticate(cred);
		},
		isLoggedIn: function () {
			var loggedIn = false;
			var token = ipCookie('_uim_tkn');
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
			this.redirectToUrl(URLS.redirectAfterLogin || null);
		},
		writeAuthToCookie: function(token){
			console.log(token);
//			var cookieConf = {path: '/', expires: 7, expirationUnit: 'days', domain: 'uimirror.com'};
			var cookieConf = {path: '/', expires: 7, expirationUnit: 'days'};
			ipCookie('_uim_tkn', token, cookieConf);
		}
	};
});

AutheticationService.factory('UIMAuthApi', function ($http, $q) {
	var validation_err = {_code: '403', _msg: ''};
	var isValidCred =  function(cred) {
		var valid = true;
		if(!cred || !cred.UserName || !cred.Password){
			validation_err._msg= 'Invalid Identity.';
			valid = false;
		}
		return valid ? valid : validation_err;
	};
	return {
		authenticateFromCookie: function (token) {
			if(token){
				$http({
	                method: "post",
	                url: URLS.logincookieservice,
	                //transformRequest: transformRequestAsFormPost,
	                data: {
	                	token: token
	                },
	                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
	                transformRequest: function(obj) {
	                    var str = [];
	                    for(var p in obj)
	                    	str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
	                    return str.join("&");
	                }
	            }).then(function(response) {
	                if (typeof response.data === 'object') {
	                	var rs = response.data;
	                	if(rs.token)
	                		return true;
	                	else
	                		return false;
	                } else {
	                    // invalid response
	                	return false;
	                }

	            }, function(response) {
	                return false;
	        	});
			}else{
				return false;
			}
		},
		authenticate: function (cred) {
			var validation_msg = isValidCred(cred);
			if(validation_msg != true){
				return $q.reject(validation_msg);
			}
			return $http({
                method: "post",
                url: URLS.loginservice,
                //transformRequest: transformRequestAsFormPost,
                data: {
                	uid: cred.UserName,
                	pwd: cred.Password
                },
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                transformRequest: function(obj) {
                    var str = [];
                    for(var p in obj)
                    	str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                    return str.join("&");
                }
            }).then(function(response) {
                if (typeof response.data === 'object') {
                	var rs = response.data;
                	if(rs.token)
                		return rs;
                	else{
                		return $q.reject({"_msg":rs.error});
                	}
                } else {
                    // invalid response
                    return $q.reject(response.data);
                }

            }, function(response) {
                return $q.reject(response.data);
        	});
		},
		refreshToken: function (token){
			//Write to cookie and return true, thats for latter and process registration
			return true;
		}
	};
});