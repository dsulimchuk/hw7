(function () {
  'use strict';

  var SearchController = function (productService, searchFormService) {
    this.searchForm = searchFormService;
    this.prodSrv = productService;

    //get params from searchService
    var locationObj = searchFormService.getParamsByModel();
    productService.findAndPopulate(locationObj);



  };

  SearchController.$inject = ['ProductService', 'SearchFormService'];
  angular.module('auction').controller('SearchController', SearchController);
}());
