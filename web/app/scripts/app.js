(function () {
  'use strict';
  /**
   * Returns a random integer between min (inclusive) and max (inclusive)
   * Using Math.round() will give you a non-uniform distribution!
   */
  function getRandomInt(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
  }

  angular.module('auction', ['ngRoute', 'restangular'])
    .constant('webSocketEndPoint','ws://localhost:8080/auction_jaxrs-1.0/api/ws')
    .config(['$routeProvider', function ($routeProvider) {
      var title = function (page) {
        return page + ' | Auction';
      };

      $routeProvider
        .when('/', {
          templateUrl: 'views/home.html',
          controller: 'HomeController',
          controllerAs: 'ctrl',
          title: title('Home')
        })
        .when('/search', {
          templateUrl: 'views/search.html',
          controller: 'SearchController',
          controllerAs: 'ctrl',
          title: title('Search'),
          reloadOnSearch: false
        })
        .when('/product/:productId', {
          templateUrl: 'views/product.html',
          controller: 'ProductController',
          controllerAs: 'ctrl',
          title: title('Product Details'),
          resolve: {
            product: ['$route', 'ProductService', function ($route, productService) {
              var productId = parseInt($route.current.params.productId);
              return productService.getProductById(productId);
            }]
          }
        })
        .otherwise({
           redirectTo: '/'
         });
    }])
    .config(['RestangularProvider', function (RestangularProvider) {
      //RestangularProvider.setBaseUrl('data');
      //RestangularProvider.setBaseUrl('http://private-anon-44b976eb9-webauctionv1.apiary-mock.com');
        RestangularProvider.setBaseUrl('http://localhost:8080/auction_jaxrs-1.0/api');
      //RestangularProvider.setRequestSuffix('.json');
        //RestangularProvider.setDefaultHeaders({hahaha: "x-restangular"});
        RestangularProvider.addResponseInterceptor(function(data, operation, what, url, response, deferred) {
          var extractedData;
          if (operation === 'getList') {
            extractedData = data.items;
            extractedData.meta = data.items;
          } else {
            extractedData = data;
          }
          return extractedData;
        });

    }])
    .run(['$rootScope', 'SearchFormService', function ($rootScope, SearchFormService) {
        $rootScope.$on('$routeChangeSuccess', function (event, currentRoute) {
        $rootScope.pageTitle = currentRoute.title;
        $rootScope.USER_ID = getRandomInt(1,3);
        //register listener on params to SearchForm
        SearchFormService.applyLocationParams(currentRoute.params);
      });
    }]);
}());
