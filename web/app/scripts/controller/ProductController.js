(function () {
  'use strict';
  var productService = null;
  var _this = null;
  var ProductController = function (product, searchFormService, Restangular, $rootScope, ProductService) {
    this.product = product;
    this.prodBids = {};
    this.searchForm = searchFormService;
    this.rest = Restangular.all('bid');
    this.bidAmount = null;
    productService = ProductService;
    _this = this;




    this.placeBid = function(){
      console.log(this.bidAmount);
      //7 known properties: "desiredQuantity", "isWinning", "amount", "bidTime", "id", "user", "product"
      this.rest.post({
        productId: product.id,
        amount: this.bidAmount,
        userId: $rootScope.USER_ID,
        desiredQuantity: 1
      }).then(function(){

        productService.getProductById(_this.product.id).then(function(data) {
          _this.product = data;
        });
      });



    };
  };


  ProductController.$inject = ['product', 'SearchFormService', 'Restangular', '$rootScope', 'ProductService'];
  angular.module('auction').controller('ProductController', ProductController);
}());
