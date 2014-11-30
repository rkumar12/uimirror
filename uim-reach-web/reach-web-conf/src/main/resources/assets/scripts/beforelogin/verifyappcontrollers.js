'use strict';

/**
 * @ngdoc function
 * @name uim-reachWebApp.controller:UIMReachVerifyCtrls
 * @description
 * # AboutCtrl
 * Controller of the oAuth2WebApp
 */
var UIMReachVerifyCtrls = angular.module("UIMReachVerifyCtrls", []);

UIMReachVerifyCtrls.controller('ChangeEmailModalCtrl', function ($scope, $modal, UIMTempUser) {
	$scope.user = {'firstName': null,
			'email'           : null,
			'sex'             : null,
			'newEmail'        : null
	};
	$scope.error = {'hasError':null,
			'msg':null};
	$scope.init = function(){
		var user = UIMTempUser.getUser();
		if(!user){
			UIMTempUser.redirectToLogin();
		}
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
		}, function () {
			console.info('Modal dismissed at: ' + new Date());
			console.log('show lading end');
		});
	};
	$scope.init();
});

	// Please note that $modalInstance represents a modal window (instance) dependency.
	// It is not the same as the $modal service used above.

UIMReachVerifyCtrls.controller('ChangeEmailModalInstanceCtrl', function ($scope, $modalInstance, user, error, UIMVerifyApi) {
	$scope.user = user;
	$scope.error = error;
	$scope.changeEmail = function () {
		if(!$scope.user.newEmail){
			$scope.error.hasError = true;
			$scope.error.msg = 'Please give your new email';
		}else{
			UIMVerifyApi.changeEmail($scope.user.newEmail).then(function (rs) {
				$scope.user.email = $scope.user.newEmail;
				$modalInstance.close('sucess');
			}, function (error) {
				$scope.error.msg = error._msg;
			});
		}
	};
	$scope.cancel = function () {
		$modalInstance.dismiss('cancel');
	};
});

UIMReachVerifyCtrls.controller('VerifyAccountCtrl', function ($scope, UIMVerifyApi, UIMAuthServ, UIMTempUser) {
	$scope.master = {};
	$scope.error = {'hasError':null,
			'msg':null}
	$scope.verify = function (cred) {
		$scope.master = angular.copy(cred);
		UIMVerifyApi.verify($scope.master.code).then(function (rs) {
			UIMAuthServ.writeAuthToCookie(rs.token);
			UIMVerifyApi.deleteTempCookie();
			UIMTempUser.redirectToHomePage();
		}, function (error) {
			$scope.error.hasError = true;
			$scope.error.msg = error._msg;
		});
	};
});