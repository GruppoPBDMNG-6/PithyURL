var app = angular.module('pithyurl_main', [ 'ngCookies', 'ngResource', 'ngSanitize',
		'ngRoute' ]);

var host = location.hostname+(location.port ? ":" + location.port + "/" : "/");
var mainView = true;

