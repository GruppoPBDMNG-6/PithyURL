app.controller('StatsCtrl', function($scope, $rootScope, $http, $location) {
	$scope.hostname = host;
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