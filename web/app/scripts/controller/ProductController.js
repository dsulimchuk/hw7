(function () {
  'use strict';

  var ProductController = function (product, searchFormService) {
    this.product = product;
    this.searchForm = searchFormService;
  };

  ProductController.$inject = ['product', 'SearchFormService'];
  angular.module('auction').controller('ProductController', ProductController);
}());
