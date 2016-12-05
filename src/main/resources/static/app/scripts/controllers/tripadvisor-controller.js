'use strict';
angular.module('app.tripadvisor', ['datatables'])
  .controller('TripAdvisorCtrl', ['$scope','$http', function ($scope,$http) {
	  $scope.cities = [];
	  $http.get('../api/hotels/cities').then(function(response){
		  $scope.cities = response.data;
	  }, function(){
		  
	  });
	  
	  $scope.search = function(city){
		  console.log(city)
		  $http({
		    url: '../api/hotels/search', 
		    method: "GET",
		    params: {"city": city}
		 }).then(function(response){
			  $scope.businesses = response.data;
		  }, function(){
			  
		  });
	  }
	  
}]);
