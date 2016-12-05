'use strict';

var app = angular.module('app', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'ui.router',
    'app.dashboard',
    'app.tripadvisor',
    'ngTagCloud',
    'datatables'
  ])
  .run(
  [          '$rootScope', '$state', '$stateParams',
    function ($rootScope,   $state,   $stateParams) {
        $rootScope.$state = $state;
        $rootScope.$stateParams = $stateParams;
    }
  ]
)
  .config(
  [          '$stateProvider', '$urlRouterProvider', '$controllerProvider', '$compileProvider', '$filterProvider', '$provide',
    function ($stateProvider,   $urlRouterProvider,   $controllerProvider,   $compileProvider,   $filterProvider,   $provide) {
	  app.controller = $controllerProvider.register;
      app.directive  = $compileProvider.directive;
      app.filter     = $filterProvider.register;
      app.factory    = $provide.factory;
      app.service    = $provide.service;
      app.constant   = $provide.constant;
      app.value      = $provide.value;

	  $urlRouterProvider.otherwise('/app/dashboard');
	  $stateProvider
	      .state('app', {
	    	  abstract: true,
	          url: '/app',
	          templateUrl: 'views/app.html'
	      })
	      .state('app.dashboard',{
	    	  url: '/dashboard',
	    	  templateUrl: 'views/dashboard.html',
	    	  controller: 'DashboardCtrl',
	    	  data: {
	    		  title: 'Dashboard'
	    	  }
          })
        .state('app.taskdetail', {
          url: '/detail/:city/:hotel',
          templateUrl: 'views/taskdetail.html',
          controller: 'TaskDetailCtrl',
          data: {
            title: 'Detail'
          }
        })
        .state('app.yelp',{
	    	  url: '/yelp',
	    	  templateUrl: 'views/yelp.html',
	    	  controller: 'DashboardCtrl',
	    	  data: {
	    		  title: 'Yelp'
	    	  }
        })
        .state('app.tripadvisor',{
	    	  url: '/tripadvisor',
	    	  templateUrl: 'views/tripadvisor.html',
	    	  controller: 'TripAdvisorCtrl',
	    	  data: {
	    		  title: 'TripAdvisor'
	    	  }
        });
  }]);
