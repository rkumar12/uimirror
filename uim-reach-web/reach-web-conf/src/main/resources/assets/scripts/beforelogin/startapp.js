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
			console.log(rs);
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
URLS.service='http://127.0.0.1:8080/uim/reach';
URLS.loginservice='http://127.0.0.1:8080/uim/reach/login1';
URLS.logincookieservice='http://127.0.0.1:8080/uim/reach/login/cookie';

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
		formatedUser.first_name = user.first_name;
		if(user.last_name)
			formatedUser.last_name = user.last_name;
		formatedUser.email = user.email;
		formatedUser.dob = user.dob.year+'-'+user.dob.month+'-'+user.dob.date;
		formatedUser.password = user.password;
		formatedUser.gender = user.sex;
		var tz = jstz.determine();
		if(tz)
			formatedUser.tz=tz.name();
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
			$window.location.href=URLS.verifyPage;
		}
	};
});

UIMRegisterServModule.factory('UIMRegisterApi', function ($http, $q) {
	  return {
		  register: function (user) {
			  return $http({
	                method: "post",
	                url: URLS.service+"/user?tz="+user.tz,
	                //transformRequest: transformRequestAsFormPost,
	                data: user,
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
	                    if(rs.token){
	                    	console.log(rs.token);
	                    	return rs;
	                    }else if(rs.error){
	                    	return $q.reject(rs.error);
	                    }
	                    //TODO take care about error
	                } else {
	                    // invalid response
	                    return $q.reject('OOps Something Went wrong!!!');
	                }
	            }, function(response) {
	            	return response.token;
	                //return $q.reject(response.data);
	        	});
		  }
	  };
});