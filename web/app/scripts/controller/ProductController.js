(function () {
  'use strict';
  var productService = null;
  var _this = null;
  var _$scope = null;
  var updateModel = function(){
    console.log("updateModel");
    productService.getProductById(_this.product.id).then(function(data) {
      _this.product = data;
      _this.timeleft = updateTimeLeft(data);
    });
  }
  var timeout = setInterval(function(){
    updateModel();
  }, 3000);

  var updateTimeLeft = function(product){
    if (product.auctionIsClosed) {
      return 'Closed';
    } else {
      return (new Date(product.auctionEndTime) - new Date()) / 1000;
    }

  };
  var ProductController = function (product, searchFormService, Restangular, $rootScope, ProductService, $timeout, $scope) {
    this.product = product;
    this.timeleft = updateTimeLeft(product);
    this.prodBids = {};
    this.searchForm = searchFormService;
    this.rest = Restangular.all('bid');
    this.bidAmount = null;
    productService = ProductService;
    _this = this;
    _$scope = $rootScope;



    this.placeBid = function(){
      console.log(this.bidAmount);
      //7 known properties: "desiredQuantity", "isWinning", "amount", "bidTime", "id", "user", "product"
      this.rest.post({
        productId: product.id,
        amount: this.bidAmount,
        userId: $rootScope.USER_ID,
        desiredQuantity: 1
      }).then(function(){
        updateModel();

      });



    };
  };


  ProductController.$inject = ['product', 'SearchFormService', 'Restangular', '$rootScope', 'ProductService', '$timeout'];
  angular.module('auction').controller('ProductController', ProductController);
}());
