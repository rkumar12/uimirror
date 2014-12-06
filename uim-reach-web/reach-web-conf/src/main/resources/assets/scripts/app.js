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
UIMReachCtrls.controller('CollapseDemoCtrl', function ($scope) {
	$scope.isCollapsed = true;
});

