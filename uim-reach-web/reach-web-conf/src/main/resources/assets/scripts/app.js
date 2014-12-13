'use strict';

/**
 * @ngdoc overview
 * @name uimReachWebApp
 * @description it's the main module for the reach application before login functionality.
 * # uimReachWebApp
 *
 * Main module of the application.
 */

var UIMReachApp = angular.module('UIMReachApp', 
		['UIMReachAppCtrls', 'ngMessages', 'ui.bootstrap']);

var UIMReachCtrls = angular.module("UIMReachAppCtrls", []);

UIMReachCtrls.controller('SubMenuCtrl', function ($scope) {
	
	$scope.keepSubMenuOpen = false;
	
	$scope.setKeepSubMenuOpen = function(value){
		$scope.keepSubMenuOpen = value;
	};
	$scope.isSubMenuOpen = function(){
		return $scope.keepSubMenuOpen;
	};
});

