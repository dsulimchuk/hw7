(function () {
  'use strict';

  var _this;

  var ProductService = function (Restangular, $route) {
    // Instance attributes go here:
    this.Restangular = Restangular;
    this.route = $route;
    this.lsProd =  [];
    _this = this;

  };

  /** List all dependencies required by the service. */
  ProductService.$inject = ['Restangular', '$route'];

  // Instance methods go here:
  ProductService.prototype = {
    //last searched products

    /** Returns the list of all available products on the server. */
    getProducts: function () {
      console.log('call getProd');
      return this.Restangular.all('product/featured').getList();

    },

    /** Finds products with specified criteria. */
    find: function (params) {
      console.log(params);
      return this.Restangular.all('product/search').getList(params);
    },
    /** Finds products with specified criteria. */
    findAndPopulate: function (params) {
      console.log('findAndPopulate = ' + params);
      this.find(params)
          .then(function (data) {
            _this.lsProd = data;
          });
    },

    /** Finds products by its ID. */
    getProductById: function (productId) {
      return this.Restangular.one('product', productId).get();
    }
  };

  // Register the service within AngularJS DI container.
  angular.module('auction').service('ProductService', ProductService);
}());
