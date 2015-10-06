app.controller('StatsCtrl', function($scope, $rootScope, $http, $location) {
	$scope.hostname = host;
	$rootScope.stats = false;
	$scope.errorS = false;
	$scope.lsurlForStats = {};
	$scope.lsurl = {};
	$scope.infoBtn = "More Country Info";
	$scope.legendBtn = "Show Legend";
	$scope.unshowableCountries = false;
	
	/**
	 * Funzione per l'ottenimento delle statistiche relative ad uno short url
	 */
	$scope.inspectUrl = function() {
		$scope.errorS = false;
		$scope.stats = false;
		if ($scope.lsurlForStats.short == "" || $scope.lsurlForStats.short == null) {
			return; //Campo non avvalorato
		} else {
			$scope.fixShort($scope.lsurlForStats.short);
			$http.post('/api/v1/inspectUrl', $scope.lsurlForStats).success(function(data) {
				$scope.lsurl.longUrl = data.long;
				$scope.lsurl.shortUrl = data.short;
				$scope.lsurl.totVisits = $scope.parseVisits(data.tot_visits.toString());
				$scope.lsurl.uniqueVisits = $scope.parseVisits(data.unique_visits.toString());
				$scope.lsurl.creationDate = data.create_date;
				$scope.lsurl.coutries = data.countries;
				$scope.genWorld(data.tot_visits);
				$scope.stats = true;
			}).error(function(data, status) {
				if(status == 501){
					$scope.textError = "Short URL not found.";
				}
				$scope.errorS = true;
			});
		}
	}
	
	$scope.genWorld = function(totVisits) {
		
		//costruzione nuova mappa
		
		var name, visits, acronym;
		$scope.unshowableCountries = false;
		
		var world = '{ "map" : { "name" : "world_countries", '+
							'"defaultArea" : {"attrs" : {"fill" : "#0F80A6", "stroke" : "#ced8d0"}, "attrsHover" : {"fill": "#55ee11"}}},'+
							'"legend" : {"area" : {"title" : "none", "mode" : "horizontal",'+
							' "slices" : [{"min" : 1 , "max": "100" , "attrs" : {"fill" : "#FFFF00"} , "label" : ""} '+
										', {"min" : 100 , "max": 1000 , "attrs" : {"fill" : "#FFBB00"} , "label" : ""}'+
										', {"min" : 1000 , "max": 10000 , "attrs" : {"fill" : "#FF9900"} , "label" : ""}'+
										', {"min" : 10000 , "max": 100000 , "attrs" : {"fill" : "#FF6600"} , "label" : ""}'+
										', {"min" : 100000, "max": 1000000  , "attrs" : {"fill" : "#FF3300"} , "label" : ""}'+
										', {"min" : 1000000, "max": 5000000  , "attrs" : {"fill" : "#DD0000"} , "label" : ""}'+
										', {"min" : 5000000, "attrs" : {"fill" : "#990000"} , "label" : ""}'+
										']}},"areas" : {';
		var infos = '';
							
		for(var i in $scope.lsurl.coutries){
			
			name = stateAcronyms[$scope.lsurl.coutries[i].name];
			visits = $scope.lsurl.coutries[i].visits;
			acronym = $scope.lsurl.coutries[i].name;
			
			if(stateAcronyms[$scope.lsurl.coutries[i].name] != undefined){
				
				world += '"'+$scope.lsurl.coutries[i].name+'" : {"value" : '+visits+', "tooltip" : {"content" : "<span style=\'font-weight:bold;\'>'+name+'<\/span><br \/>Clicks : '+visits+'"}},';
				
			}
			
			var perc = ((visits*100)/totVisits).toFixed(2);
			
			if(stateAcronyms[$scope.lsurl.coutries[i].name] != undefined){
				name = stateAcronyms[$scope.lsurl.coutries[i].name];
				infos += '<tr><td>'+acronym+'</th><td>'+name+'</th><td>'+visits+'</td><td>'+perc+'%</td></tr>';
			}else{
				$scope.unshowableCountries = true;
				name = stateAcronymsServer[$scope.lsurl.coutries[i].name];
				infos += '<tr><td><u>'+acronym+'</u></td><td><u>'+name+'</u></th><td><u>'+visits+'</u></td><td><u>'+perc+'%</u></td></tr>';
			}
			
			if(i == ($scope.lsurl.coutries.length - 1)){
				
			}
	
		}
		
		if(world.slice(-1) == ","){
			world = world.substring(0, world.length - 1);
		}
		world += '}}';
		
		//clear del contenuto della vecchia mappa
		
		$("#mapael").html('<div class="row"><div class="col-md-12"><div class="map"><span>Loadin map ...</span></div></div></div>'
				+'<div class="row"><div class="col-md-8 col-md-offset-2"><h4 align="center">Click a color to hide relative countries :</h4></div></div><div class="row">'
				+'<div class="col-md-8 col-md-offset-2"><div class="areaLegend" align="center"><span>Loding legend ...</span></div></div></div>');
		//inserimento della nuova mappa
		$(".mapaelContainer").mapael(JSON.parse(world));
		
		//carico una tabella di info
		$("#worldStatsContent").html(infos);
		
		if($("#worldStatsContent tr").length == 0){
			$("#worldStatsContent").html('<tr><td id="emptyTable" colspan="4">Still no clicks</td>');
		}
		
	}
	
	$scope.pressInfo = function() {
		if($scope.infoBtn === ("More Country Info")){
			$scope.infoBtn = "Less Country Info";
		}else{
			$scope.infoBtn = "More Country Info";
		}
	}
	
	$scope.pressLegend = function() {
		if($scope.legendBtn === ("Show Legend")){
			$scope.legendBtn = "Hide Legend";
		}else{
			$scope.legendBtn = "Show Legend";
		}
	}
	
	$scope.parseVisits = function(visits) {
		
		var visitsRev = visits.split('').reverse().join('');
		var visitsSplit = visitsRev.match(/.{1,3}/g);
		var visitsParsedRev ="";
		for(var i = 0; i < visitsSplit.length; i++){
			visitsParsedRev += visitsSplit[i]+".";
		}
		visitsParsedRev = visitsParsedRev.substring(0, visitsParsedRev.length - 1);
		
		return visitsParsedRev.split('').reverse().join('');
	}
	
	$scope.fixShort = function(shortURL) {
		var proxyHostLength = ("http://"+$scope.hostname).length;
		var hostLength = $scope.hostname.length;
		
		if(shortURL.indexOf("http://"+$scope.hostname) > -1){
			$scope.lsurlForStats.short = shortURL.substring(proxyHostLength);
		} else if(shortURL.indexOf($scope.hostname) > -1){
			$scope.lsurlForStats.short = shortURL.substring(hostLength);
		} else {
			$scope.lsurlForStats.short = shortURL;
		}
	}
	
});