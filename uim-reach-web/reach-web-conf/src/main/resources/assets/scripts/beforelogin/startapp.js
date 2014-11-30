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
		['UIMReachBeforeLoginCtrls', 'ngMessages', 'ui.bootstrap', 'cgNotify','UIMRegisterService', 'AutheticationService', 'UIMUtil']);


//When the app loads
AutheticationService.run(function ($location, UIMAuthServ) {
  if (UIMAuthServ.isLoggedIn()) {
	  UIMAuthServ.redirectToUrl();
  }
});


AutheticationService.controller('LoginCtrl', function ($scope, UIMAuthServ, $http) {
	$scope.loginForm = {UserName: "", Password: ""};
	$scope.login = function () {
		var credentials = {
				"UserName": $scope.loginForm.UserName,
				"Password": $scope.loginForm.Password
		};
		UIMAuthServ.authenticate(credentials).then(function (rs) {
			UIMAuthServ.writeAuthToCookie(rs.token);
			UIMAuthServ.redirectToAttemptedUrl();
		}, function (error) {
			$scope.ErrorMessage = error._msg;
			$scope.loginForm.Password='';
		});
	};
});


var UIMRegisterServModule = angular.module('UIMRegisterService',['ipCookie']);
URLS.verifyPage = URLS.base+'verify';
UIMRegisterServModule.factory('UIMRegister', function ($location,  ipCookie, UIMRegisterApi, $q, $window) {
	var validation_err = {'hasError':true, 'msg':null};;
	var isValid =  function(user) {
		var valid = true;
		if(!user){
			valid = false;
			validation_err.msg = 'Please provide your details.';
		}else if(user.password != user.confirmpassword){
			valid = false;
			validation_err.msg = 'Password is not matching.';
		}else if(user.isAgreed == false){
			valid = false;
			validation_err.msg = 'Please accept our policy.';
		}
		return valid ? valid : validation_err;
	};
	var formatData = function(user){
		var formatedUser = {};
		formatedUser.firstName = user.firstName;
		if(user.lastName)
			formatedUser.lastName = user.lastName;
		//TODO similarly other fields
		return formatedUser;
	};
	var writeToCookie = function(user, token){
		delete user.password;
		delete user.confirmpassword;
		delete user.isAgreed;
		ipCookie('user', user);
		ipCookie('_uim_tmp_tkn', token);
	};
	return {
		register: function (user) {
			var valid = isValid(user);
			if(valid == true){
				return UIMRegisterApi.register(formatData(user)).then(function (rs) {
					writeToCookie(user, rs.token);
					return true;
				}, function (error) {
					validation_err.msg = error;
					return $q.reject(validation_err);
				});
			}else{
				return $q.reject(valid);
			}
		},
		redirectToVerifyPage: function() {
			console.log('redirecting'+URLS.base);
			$window.location.href=URLS.verifyPage;
		}
	};
});

UIMRegisterServModule.factory('UIMRegisterApi', function ($http) {
	  return {
		  register: function (user) {
			  return $http({
	                method: "get",
	                url: "http://uimirror.com",
	                //transformRequest: transformRequestAsFormPost,
	                data: user
	            }).then(function(response) {
	                if (typeof response.data === 'object') {
	                    return response.data;
	                } else {
	                    // invalid response
	                    return $q.reject(response.data);
	                }
	            }, function(response) {
	            	return {token:'1'};
	                //TODO uncomment latter
	                //return $q.reject(response.data);
	        	});
		  }
	  };
});