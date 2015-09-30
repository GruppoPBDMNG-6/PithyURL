app.controller('CookiesCtrl', ['$scope', '$cookieStore', function ($scope, $cookieStore) {

	$scope.cookiePolicyAccepted = true;
	
	if($cookieStore.get('PithyUrlCookies') == undefined){
		$scope.cookiePolicyAccepted = false;
	}
	
	$scope.acceptCookiePolicy = function() {
		$cookieStore.put('PithyUrlCookies', 'true');
	}
	
}]);