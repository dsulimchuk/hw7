(function() {
    'use strict';

    var _this;
    var  deferred;
    var _productService;
    var _$rootScope;
    var ProductController;
    var BidService = function (webSocketEndPoint, $q, ProductService, $rootScope) {
        // Instance attributes go here:
        _this = this;
        deferred = $q.defer();
        _productService  = ProductService;
        _$rootScope = $rootScope;

        this.active  = false;
        if (window.WebSocket) {
            this.ws = new WebSocket(webSocketEndPoint);
            this.ws.onopen = function() {
                console.log("onopen");
                deferred.promise.then(function(){
                    _this.active = true;
                });
                deferred.resolve();

            };
            this.ws.onmessage = function(e) {
                console.log("echo from server : " + e.data);
                $rootScope.$apply(function() {
                    ProductController.product = JSON.parse(e.data);
                });

            };
            this.ws.onclose = function() {
                console.log("onclose");
                _this.active = false;
            };
            this.ws.onerror = function() {
                console.log("onerror"); };
        } else {
            console.log("WebSocket object is not supported");
        };

    };


    BidService.prototype = {
      serveForProduct: function(product, _ProductController) {
          if (_this.ws.readyState === 1) {
              _this.ws.send(product.id);
          } else {
              deferred.promise.then(function(){
                  console.log("defered");
                  _this.ws.send(product.id);
              });
          }
          ProductController = _ProductController;


      },
      placeBid: function(bid){

      }
    };

    BidService.$inject = ['webSocketEndPoint', '$q', 'ProductService', '$rootScope'];
    angular.module('auction').service('BidService', BidService);
})();