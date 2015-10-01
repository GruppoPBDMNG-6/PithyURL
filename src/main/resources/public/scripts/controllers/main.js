app.controller('CreateCtrl', function ($scope, $rootScope, $http, $location) {
	
	$scope.mainView = mainView;
	
	$scope.hostname = host;
	
    $scope.lsurl = {};
    
    $rootScope.response = false;
    $rootScope.error = false;
    
    $rootScope.errorS = false;

	$scope.createLsUrl = function() {
		console.log("ciao");
		$scope.lsurl.custom = false;
		$scope.lsurl.short = "not_set";
		console.log($scope.lsurl);
		$http.post('/api/v1/lsurl', $scope.lsurl).success(function(data) {
			$location.path('/');
			console.log(data.id);
			$scope.createdUrl = data;
			$rootScope.response = true;
		}).error(function(data, status) {
			console.log('Error ' + data)
			$rootScope.error = true;
		})
	}

	$scope.createLsUrlCustom = function() {
		console.log("ciao");
		$scope.lsurl.custom = true;
		console.log($scope.lsurl);
		$http.post('/api/v1/lsurl', $scope.lsurl).success(function(data) {
			$location.path('/');
			$scope.createdUrl = data;
			$rootScope.response = true;
		}).error(function(data, status) {
			console.log('Error ' + data);
			$rootScope.response = true;
			$rootScope.error = true;
			if(status == 500){
			$scope.textError = "Parola non accettabile";
			}})
	}

	$scope.toStats = function() {
		mainView = false;
		$scope.mainView = mainView;
		$scope.resetMsgs();
	}
	
	$scope.toMain = function() {
		mainView = true;
		$scope.mainView = mainView;
		$scope.resetMsgs();
	}
	
	$scope.resetMsgs = function() {
		$rootScope.response = false;
	    $rootScope.error = false;
	    $rootScope.errorS = false;
	    $scope.lsurl.short = null;
	}

});