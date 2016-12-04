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

project.controller('TaskDetailCtrl',['$scope','$routeParams', function($scope, $routeParams) {
  console.log('hehe');
  console.log($routeParams);
  $scope.objectid = $routeParams.id;
  console.log($scope.objectid);
  $scope.newobject = {};
  for(var i=0; i < objectlist.length;i++){
    if(objectlist[i].id === $scope.objectid){
      $scope.newobject = objectlist[i];
    }
  }
  console.log('detail');
  console.log($scope.newobject);
}]);
