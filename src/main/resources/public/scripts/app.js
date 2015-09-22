var app = angular.module('pithyurl', [ 'ngCookies', 'ngResource', 'ngSanitize',
		'ngRoute' ]);

app.config(function ($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: 'views/main.html',
        controller: 'CreateCtrl'
    }).when('/stats', {
        templateUrl: 'views/stats.html',
        controller: 'StatsCtrl'
    }).when('/preview', {
        templateUrl: 'views/preview.html',
        controller: 'PreviewCtrl'
	}).otherwise({
		redirectTo : '/'
	})
});


app.controller('CreateCtrl', function ($scope, $rootScope, $http, $location) {
	
	$scope.hostname = location.hostname+(location.port ? ":" + location.port + "/" : "/");
	
    $scope.lsurl = {};
    console.log("ciaoFuoriert");
    
    $rootScope.done = false;
    $rootScope.error = false;

	$scope.createLsUrl = function() {
		console.log("ciao");
		$scope.lsurl.custom = false;
		$scope.lsurl.short = "not_set";
		console.log($scope.lsurl);
		$http.post('/api/v1/lsurl', $scope.lsurl).success(function(data) {
			$location.path('/');
			console.log(data.id);
			$scope.createdUrl = data;
			$rootScope.done = true;
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
			$rootScope.done = true;
		}).error(function(data, status) {
			console.log('Error ' + data)

			$rootScope.error = true;
		})
	}

});

app.controller('PreviewCtrl', function ($scope, $http, $location) {

});

app.controller('StatsCtrl', function($scope, $rootScope, $http, $location) {
	$rootScope.stats = true;
	$scope.lsurlForStats = {};
	$scope.lsurl = {};
	
	$scope.inspectUrl = function() {
		console.log($scope.lsurlForStats.short);
		if ($scope.lsurlForStats.short == "" || $scope.lsurlForStats.short == null) {
			return; //Campo non avvalorato
		} else {
			$http.post('/api/v1/inspectUrl', $scope.lsurlForStats).success(function(data) {
				$scope.lsurl.longUrl = data.long;
				$scope.lsurl.shortUrl = data.short;
				$scope.lsurl.totVisits = data.tot_visits;
				$scope.lsurl.creationDate = data.create_date;
				
				$rootScope.stats = true;	
			}).error(function(data) {
				console.log('Error ' + data)
				
			})
		}
	}
	
});