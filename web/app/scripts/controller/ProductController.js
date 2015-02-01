(function () {
  'use strict';
  var _productService = null;
  var _bidService = null;
  var _this = null;
  var _$rootScope = null;

  var updateModel = function(){
    //if bidServise is active, then model should refresh via websockets
    if (_bidService.active != true) {
      console.log("updateModel");
      _productService.getProductById(_this.product.id).then(function(data) {
      _this.product = data;
      _this.timeleft = updateTimeLeft(data);
      });
    }

  };
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

  var ProductController = function (product, searchFormService, Restangular, ProductService, $scope, BidService, $rootScope) {
    this.product = product;
    this.timeleft = updateTimeLeft(product);
    this.prodBids = {};
    this.searchForm = searchFormService;
    this.rest = Restangular.all('bid');
    this.bidAmount = null;
    _productService = ProductService;
    _this = this;
    _bidService = BidService;
    _bidService.serveForProduct(product, _this);
    _$rootScope = $rootScope;



    this.placeBid = function(){
      console.log(this.bidAmount);
      //7 known properties: "desiredQuantity", "isWinning", "amount", "bidTime", "id", "user", "product"
      this.rest.post({
        productId: product.id,
        amount: this.bidAmount,
        userId: _$rootScope.USER_ID,
        desiredQuantity: 1
      }).then(function(){
        updateModel();

      });



    };
  };

  ProductController.$inject = ['product', 'SearchFormService', 'Restangular', 'ProductService', '$scope', 'BidService', '$rootScope'];

  angular.module('auction').controller('ProductController', ProductController);
}());
