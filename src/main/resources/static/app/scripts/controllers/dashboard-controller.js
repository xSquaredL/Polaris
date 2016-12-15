'use strict';
function randomString(length, chars) {
     var result = '';
     for (var i = length; i > 0; --i) {
       result += chars[Math.round(Math.random() * (chars.length - 1))];
     }
      return result;
}
var objectlist = [];
var project = angular.module('app.dashboard', []);
project.controller('DashboardCtrl', ['$scope','MyYelpAPI', function ($scope, MyYelpAPI) {
    $scope.loc = '';
  // var loc = 'Urbana';

    $scope.sendPost = function() {
        MyYelpAPI.retrieveYelp($scope.loc, function(data) {
            console.log($scope.loc);
            $scope.order = "rank";
            $scope.businesses = data.businesses;
            objectlist = $scope.businesses;
            console.log($scope.businesses.length);
            console.log($scope.businesses);
        });
  };

}]).factory("MyYelpAPI", function($http) {
 return {
  "retrieveYelp": function(name, callback) {
   var method = 'GET';
   var url = 'http://api.yelp.com/v2/search';
   var params = {
     callback: 'angular.callbacks._0',
     // location: 'Urbana',
     location: name,
     oauth_consumer_key: 'qbZix6_cnnfgn19eVB6jvQ', //Consumer Key
     oauth_token: '67Z_qBJ-rsg-XPVephPQnPlkYOlAGTq_', //Token
     oauth_signature_method: "HMAC-SHA1",
     oauth_timestamp: new Date().getTime(),
     oauth_nonce: randomString(32, '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'),
     term: 'Restaurants'
    };
   var consumerSecret = 'jLUyk1kDv-Lf8M2LaqSeSQlIS6I'; //Consumer Secret
   var tokenSecret = 'AznsCLvPnMFOGShTLEqAUggdldA'; //Token Secret
   var signature = oauthSignature.generate(method, url, params, consumerSecret, tokenSecret, { encodeSignature: true});
   console.log('xixi');
    console.log(signature);
    params.oauth_signature = signature;
   $http.jsonp(url, {params: params}).success(callback);
    }
  };
});

project.controller('TaskDetailCtrl',['$scope','$stateParams','$http','$timeout','ngNotify', function($scope, $stateParams,$http,$timeout,ngNotify) {
  
//  $scope.data = [
//      {text: "crab", weight: 13},
//      {text: "wait", weight: 10.5},
//      {text: "line", weight: 9.4},
//      {text: "station", weight: 8},
//      {text: "step", weight: 6.2},
//      {text: "fresh", weight: 6.2},
//      {text: "buffet", weight: 5},
//      {text: "best", weight: 5},
//      {text: "time", weight: 5},
//      {text: "vegas", weight: 5},
//      {text: "sushi", weight: 5},
//      {text: "lamb", weight: 4},
//      {text: "good", weight: 4},
//      {text: "like", weight: 3},
//      {text: "vegetarian", weight: 3},
//      {text: "food", weight: 8},
//      {text: "vegas", weight: 7},
//      {text: "vegetarian", weight: 3},
//      {text: "expensive", weight: 2}
//    ];
  $scope.hotel = $stateParams.hotel;
  $scope.city = $stateParams.city;
  $scope.business={};
  $http({
	    url: '../api/hotels/search', 
	    method: "GET",
	    params: {"city": $stateParams.city, "hotel": $stateParams.hotel}
	 }).then(function(response){
		 $scope.business = response.data[0];
	  }, function(){
		  
	  });
  
  $scope.grades=[
	  {grade:"A",lowQualityReview:"5", class:"font-grade-a"},
	  {grade:"B",lowQualityReview:"15", class:"font-grade-b"}
//	  ,{grade:"C",lowQualityReview:"25", class:"font-grade-c"},
//	  {grade:"D",lowQualityReview:"35", class:"font-grade-d"},
//	  {grade:"F",lowQualityReview:"60", class:"font-grade-f"}
  ];
  $scope.grade = {grade:"?",lowQualityReview:"?", class:"font-grade-a"};
  
  function getRandomArbitrary(min, max) {
	    return Math.random() * (max - min) + min;
	}
  
  $scope.analyzeState = 0;
  $scope.startAnalyzing = function(){
	  $scope.analyzeState = 1;
	  $timeout( function(){ 
		  $scope.analyzeState = 2; 
		  $http({
			    url: '../api/hotels/report/review/' + $stateParams.hotel, 
			    method: "GET"
			 }).then(function(response){
				 $scope.data = [];
				  $scope.grade = $scope.grades[parseInt($stateParams.hotel) % 2];
				 $scope.report = response.data;
				 $scope.report.topics.forEach(function(e) {
					   var elm = {text: "expensive", weight: 2};
					   elm.text = e;
					   elm.weight = getRandomArbitrary(1,10);
					   $scope.data.push(elm);
				 });
				 console.log($scope.data)
			  }, function(){
				  ngNotify.set('Error submitting request. Possibly caused by spark cluster not running. Please view recent reports. ', {
					    position: 'bottom',
					    type: 'error',
					    duration: 10000
					});
			  });
		  
	  }, 3000);
  }
  
  
  
  $scope.vote = function(){
	  console.log('vote')
	  ngNotify.set('Thank you for your feedback', {
		    position: 'bottom',
		    type: 'success',
		    duration: 2000
		});
  }
}]);
