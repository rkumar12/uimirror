'use strict';

/**
 * @ngdoc function
 * @name oAuth2WebApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the oAuth2WebApp
 */
var UIMoAuth2LoginControllers = angular.module("UIMoAuth2LoginControllers", []);

UIMoAuth2LoginControllers.controller('LoginController', 
		['$scope',
		 	function($scope){    
				$scope.master = {};                               				
				$scope.submit = function(credential){
					$scope.master = angular.copy(credential);
					alert('coming');
					return false;
				}
				 
			}]
);

//UIMoAuth2LoginControllers.controller('LoginController', 
//		['$scope', 'uimUtil',
//		 	function($scope, uimUtil){    
//				$scope.master = {};                               				
//				$scope.submit = function(credential){
//					$scope.master = angular.copy(credential);
//					uimUtil.isValidForm($scope.loginForm['username'], 'required');
//					alert('coming');
//					return false;
//				}
//				 
//			}]
//);