(function () {
  'use strict';

  var navbarDirectiveFactory = function () {
    return {
      scope: false,
      restrict: 'E',
      templateUrl: 'views/partial/NavbarDirective.html',
      controller: ['$location', '$route', 'SearchFormService', function($location, $route, SearchFormService){
        this.product = "";
        this.location = $location;
        this.route = $route;
        this.SearchFormService = SearchFormService;

        this.submitBtn = function(){
          this.location.path('/search').search({}).search('product', this.product);
          this.product = "";
          this.route.reload();
        };
      }],
      controllerAs: 'navbarCtrl'
    };
  };

  angular.module('auction').directive('auctionNavbar', navbarDirectiveFactory);
}());
