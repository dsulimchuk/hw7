(function () {
  'use strict';

  var ProductService = function (Restangular) {
    // Instance attributes go here:
    this.Restangular = Restangular;
  };

  /** List all dependencies required by the service. */
  ProductService.$inject = ['Restangular'];

  // Instance methods go here:
  ProductService.prototype = {

    /** Returns the list of all available products on the server. */
    getProducts: function () {
      console.log('call getProd');
      return this.Restangular.all('product/featured').getList();

    },

    /** Finds products with specified criteria. */
    find: function (params) {
      //console.log(params);
      return this.Restangular.all('product/search').getList(params);
    },

    /** Finds products by its ID. */
    getProductById: function (productId) {
      return this.Restangular.one('product', productId).get();
    }
  };

  // Register the service within AngularJS DI container.
  angular.module('auction').service('ProductService', ProductService);
}());
