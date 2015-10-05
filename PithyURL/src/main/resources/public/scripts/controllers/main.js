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
		$rootScope.response = false;
		console.log($scope.lsurl);
		$http.post('/api/v1/lsurl', $scope.lsurl).success(function(data) {
			$location.path('/');
			console.log(data.id);
			$scope.createdUrl = data;
			$rootScope.response = true;
			$rootScope.error = false;
		}).error(function(data, status) {
			console.log('Error ' + data);
			if (status == 502){
				$scope.textError = "Please insert a real URL.";
			}
			$rootScope.response = true;
			$rootScope.error = true;
		})
	}

	$scope.createLsUrlCustom = function() {
		console.log("ciao");
		$scope.lsurl.custom = true;
		console.log($scope.lsurl);
		$rootScope.response = false;
		$http.post('/api/v1/lsurl', $scope.lsurl).success(function(data) {
			$location.path('/');
			$scope.createdUrl = data;
			$rootScope.response = true;
			$rootScope.error = false;
		}).error(function(data, status) {
			console.log('Error ' + data);
			if(status == 500){
				$scope.textError = "Short URL contains unaccettable words!";
			}else if (status == 502){
				$scope.textError = "Please insert a real URL.";
			}else if (status == 503){
				$scope.textError = "Short URL already exists!";
			}
			$rootScope.response = true;
			$rootScope.error = true;})
	}

	$scope.toStats = function() {
		mainView = false;
		$scope.mainView = mainView;
		$scope.resetMsgs();
		$(".nav-stats").addClass("current-nav");
		$(".nav-home").removeClass("current-nav");
	}
	
	$scope.toMain = function() {
		mainView = true;
		$scope.mainView = mainView;
		$scope.resetMsgs();
		$(".nav-stats").removeClass("current-nav");
		$(".nav-home").addClass("current-nav");
	}
	
	$scope.resetMsgs = function() {
		$rootScope.response = false;
	    $rootScope.error = false;
	    $rootScope.errorS = false;
	    $scope.lsurl.short = null;
	}

});