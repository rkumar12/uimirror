'use strict';

/**
 * @ngdoc overview
 * @description
 * Util module of the application.
 */

var UIMUtilModule = angular.module('UIMUtil', [])

UIMUtilModule.directive('focusOn', function() {
   return function(scope, elem, attr) {
      scope.$on('focusOn', function(e, name) {
        if(name === attr.focusOn) {
          elem[0].focus();
        }
      });
   };
});

UIMUtilModule.factory('focus', function ($rootScope, $timeout) {
  return function(name) {
    $timeout(function (){
      $rootScope.$broadcast('focusOn', name);
    });
  }
});