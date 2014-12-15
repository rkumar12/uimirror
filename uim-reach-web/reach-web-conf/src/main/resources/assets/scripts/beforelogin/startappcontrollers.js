'use strict';

/**
 * @ngdoc function
 * @name uim-reachWebApp.controller:UIMReachBeforeLoginCtrls
 * @description
 * # AboutCtrl
 * Controller of the oAuth2WebApp
 */
var UIMReachBeforeLoginCtrls = angular.module("UIMReachBeforeLoginCtrls", []);
UIMReachBeforeLoginCtrls.controller('SignInFocusCtrl', function ($scope, focus) {
	$scope.setLoginFcous = function(){
		focus('userNameFcs');
	};
});
UIMReachBeforeLoginCtrls.controller('SignUpModalCtrl', function ($scope, $modal, UIMRegister) {
	$scope.dob={
			'date'  : null,
			'month' : null,
			'year'  : null
	};
	$scope.user = {'first_name': null,
			'last_name'        : null,
			'email'           : null,
			'dob'             : null,
			'sex'             : 'F',
			'password'        : null,
			'confirmpassword' : null,
			'isAgreed'        : true
	};
	$scope.error = {'hasError':false,
			'msg':null};

	$scope.open = function (size) {
		var modalInstance = $modal.open({
			templateUrl: URLS.base+'template/register',
			controller: 'SignUpModalInstanceCtrl',
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
			console.log('Register Completed, Navigate to the verify page'+status);
			UIMRegister.redirectToVerifyPage();
		}, function () {
			console.info('Modal dismissed at: ' + new Date());
			console.log('show lading end');
		});
	};
});

UIMReachBeforeLoginCtrls.controller('SignUpModalInstanceCtrl', function ($scope, $modalInstance, user, error, UIMRegister) {
	$scope.user = user;
	$scope.error = error;
	$scope.register = function () {
		var res = UIMRegister.register($scope.user).then(function (rs) {
			console.log(res);
			$modalInstance.close('sucess');
		}, function (error) {
			$scope.error = error;
		});
	};

	$scope.cancel = function () {
		$modalInstance.dismiss('cancel');
	};
	
});

UIMReachBeforeLoginCtrls.controller('ModalAgreementCtrl', function ($scope, $modal) {

	//$scope.user = {'isAgreed':null};
	  
	$scope.open = function (size) {
		var modalInstance = $modal.open({
			templateUrl: URLS.base+'template/register/agreement',
			controller: 'ModalAgreementInstanceCtrl',
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
			console.log('Status of agreement'+status);
			console.log('Status of agreement'+$scope.user.isAgreed);
		}, function () {
			console.info('Modal dismissed at: ' + new Date());
		});
	};
});

UIMReachBeforeLoginCtrls.controller('ModalAgreementInstanceCtrl', function ($scope, $modalInstance, user, error) {
	$scope.user = user;
	$scope.error = error;
	$scope.agreed = function () {
		$scope.user.isAgreed = true;
		$modalInstance.close('sucess');
		$scope.error.hasError= false;
	};

	$scope.disagree = function () {
		$scope.user.isAgreed = false;
		$modalInstance.close('sucess');
	};
});

UIMReachBeforeLoginCtrls.controller('ModalResetPwdCtrl', function ($scope, $modal, notify) {
	$scope.user = {'email':null};
	$scope.error = {'hasError':null,
			'msg':null};
	$scope.open = function (size, errorMsg) {
		if(errorMsg){
			$scope.error.hasError = true;
			$scope.error.msg = errorMsg;
		}
		var modalInstance = $modal.open({
			templateUrl: URLS.base+'template/resetpassword',
			controller: 'ModalResetPwdInstanceCtrl',
			size: size,
			backdrop: 'static',
			resolve: {
				user: function () {
					return $scope.user;
				},
				error: function () {
					return $scope.error;
				}
			}
		});

		modalInstance.result.then(function (status) {
			console.info('Reset Password dismissed with sucess: ' + status);//From here you can invoke the alert
	    	notify({
	            message: 'Check Your Email, For the instructions',
	            classes: $scope.classes,
	            templateUrl: URLS.base+'template/notification',
	        });
	    }, function () {
	    	console.info('Modal dismissed at: ' + new Date());
	    });
	};
	  
});

UIMReachBeforeLoginCtrls.controller('ModalResetPwdInstanceCtrl', function ($scope, $modalInstance, user, error, $http) {
	  $scope.user = user;
	  $scope.error = error;
	  $scope.reset = function () {
		  console.log(user);
		  console.log(user.email);
		  //TODO write the similar kind of service for the reset password similar to the registeration
          $modalInstance.close('sucess');
      }

	  $scope.cancel = function () {
	    $modalInstance.dismiss('cancel');
	  };
	});
