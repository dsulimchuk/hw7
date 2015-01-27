(function () {
  'use strict';

  var SearchController = function (productService, searchFormService) {
    var _this = this;
    _this.products = [];
    this.searchForm = searchFormService;

    //get params from searchService
    var locationObj = searchFormService.getParamsByModel();
    productService.find(locationObj)
        .then(function (data) { _this.products = data; });
  };

  SearchController.$inject = ['ProductService', 'SearchFormService'];
  angular.module('auction').controller('SearchController', SearchController);
}());
