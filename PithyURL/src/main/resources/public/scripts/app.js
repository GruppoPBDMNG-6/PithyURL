var app = angular.module('pithyurl_main', [ 'ngCookies', 'ngResource', 'ngSanitize',
		'ngRoute', 'ngAnimate' ])

var host = location.hostname+(location.port ? ":" + location.port + "/" : "/");
var mainView = true;