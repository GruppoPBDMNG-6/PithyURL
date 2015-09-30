app.controller('StatsCtrl', function($scope, $rootScope, $http, $location) {
	$scope.hostname = host;
	$rootScope.stats = false;
	$scope.lsurlForStats = {};
	$scope.lsurl = {};
	
	/**
	 * Funzione per l'ottenimento delle statistiche relative ad uno short url
	 */
	$scope.inspectUrl = function() {
		$rootScope.errorS = false;
		$rootScope.stats = false;
		console.log($scope.lsurlForStats.short);
		if ($scope.lsurlForStats.short == "" || $scope.lsurlForStats.short == null) {
			return; //Campo non avvalorato
		} else {
			$http.post('/api/v1/inspectUrl', $scope.lsurlForStats).success(function(data) {
				//Controllo per verificare che il link sia effettivamente presente in DB
				if(data.long == null){
					$rootScope.errorS = true;
					$scope.textError = "PithyURL non presente in Database";
				}else{
					$scope.lsurl.longUrl = data.long;
					$scope.lsurl.shortUrl = data.short;
					$scope.lsurl.totVisits = data.tot_visits;
					$scope.lsurl.uniqueVisits = data.unique_visits;
					$scope.lsurl.creationDate = data.create_date;
					$rootScope.stats = true;
				}	
			}).error(function(data) {
				console.log('Error ' + data)
				$rootScope.error = true;
			})
		}
	}
	
});