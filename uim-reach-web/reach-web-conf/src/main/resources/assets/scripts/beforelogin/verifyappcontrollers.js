'use strict';

/**
 * @ngdoc function
 * @name uim-reachWebApp.controller:UIMReachVerifyCtrls
 * @description
 * # AboutCtrl
 * Controller of the oAuth2WebApp
 */
var UIMReachVerifyCtrls = angular.module("UIMReachVerifyCtrls", []);

UIMReachVerifyCtrls.controller('ChangeEmailModalCtrl', function ($scope, $modal, $location, UIMTempUser) {
	$scope.user = {'firstName': null,
			'email'           : null,
			'sex'             : null,
			'newEmail'        : null
	};
	$scope.error = {'hasError':null,
			'msg':null};
	$scope.init = function(){
		var user = UIMTempUser.getUser();
		$scope.user.firstName = user.firstName;
		$scope.user.email = user.email;
		$scope.user.sex = user.sex;
	};
	$scope.open = function (size) {
		var modalInstance = $modal.open({
			templateUrl: URLS.base+'template/changeEmail',
			controller: 'ChangeEmailModalInstanceCtrl',
			size: size,
			backdrop: 'static',
			resolve: {
				user: function () {
					return $scope.user;
				},
				error: function(){
					return $scope.error;
				}
			}
		});

		modalInstance.result.then(function (status) {
			console.log('Verify Completed, Navigate to the Home page'+status);
			//$location.path('verify')
		}, function () {
			console.info('Modal dismissed at: ' + new Date());
			console.log('show lading end');
		});
	};
	$scope.init();
});

	// Please note that $modalInstance represents a modal window (instance) dependency.
	// It is not the same as the $modal service used above.

UIMReachVerifyCtrls.controller('ChangeEmailModalInstanceCtrl', function ($scope, $modalInstance, user, error) {
	$scope.user = user;
	$scope.error = error;

	$scope.changeEmail = function () {
		$modalInstance.close('sucess');
		console.log($scope.user.newEmail);
		$scope.user.email = $scope.user.newEmail;
		//Redirect to home page now
		//UIMRegister.register($scope.user);
//		if(!$scope.user.isAgreed){
//			$scope.error.hasError=true;
//			$scope.error.msg='Please Accept Our Temrs & Condition';
//		}else{
//			console.log('Register Completed, Navigate to the verify page'+status);
//			$modalInstance.close('sucess');
//		}
//		
//		//$modalInstance.close($scope.selected.item);
//		console.log('Register Completed'+$scope.user.isAgreed);
	};

	$scope.cancel = function () {
		$modalInstance.dismiss('cancel');
	};
	
});

UIMReachVerifyCtrls.controller('VerifyAccountCtrl', function ($scope, $window) {
	$scope.master = {};
	$scope.verify = function (cred) {
		$scope.master = angular.copy(cred);
		console.log('verified'+cred.code);
		$window.location.href='/uim/reach/home';
	};
});