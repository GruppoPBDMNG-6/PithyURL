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
					$scope.lsurl.coutries = data.countries;
					$scope.genWorld();
					$rootScope.stats = true;
				}	
			}).error(function(data) {
				console.log('Error ' + data)
				$rootScope.error = true;
			})
		}
	}
	
	$scope.genWorld = function() {
		
		console.log(stateAcronyms.RU);
		
		
		var world = '{ "map" : { "name" : "world_countries",	"defaultArea" : {"attrs" : {"fill" : "#3366cc", "stroke" : "#ced8d0"}}},'+
							'"legend" : {"area" : {"title" : "Legend",'+
							' "slices" : [ {"min" : "1" , "max": "100" , "attrs" : {"fill" : "#FF9900"} , "label" : "From 1 to 100 visits"} '+
										', {"min" : "100" , "attrs" : {"fill" : "#FF4400"} , "label" : "From 101 to 1000 visits"}'+
										']}},"areas" : {'
		
		for(var i in $scope.lsurl.coutries){
			
			if(stateAcronyms[$scope.lsurl.coutries[i].name] != undefined){
			
				world += '"'+$scope.lsurl.coutries[i].name+'" : {"value" : "'+$scope.lsurl.coutries[i].visits+'", "tooltip" : {"content" : "<span style=\'font-weight:bold;\'>'+stateAcronyms[$scope.lsurl.coutries[i].name]+'<\/span><br \/>Visits : '+$scope.lsurl.coutries[i].visits+'"}}';
				if(i < $scope.lsurl.coutries.length - 1){
					world += ',';
				}
				
			}
		}
		
		world += '}}';
		
		$(".mapaelContainer").mapael(JSON.parse(world));
		
	}
	
});