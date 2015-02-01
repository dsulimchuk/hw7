(function(){
    "use strict";

    var productService;
    var location;
    var today = new Date();
    var DEFAULTS = {
        product : '',
        category : '',
        maxCloseDate : today.getMonth() +'/'+ today.getDate() + '/' +today.getFullYear(),
        numOfBids : 4,
        lowPrice : 0,
        highPrice : 500
    };

    //Fill all variables default values
    var init = function(searchFormService) {
        for (var param in DEFAULTS) {
            searchFormService[param] = DEFAULTS[param];
        }
    };

    var SearchFormService = function($location, ProductService){
        productService = ProductService;
        location = $location;

        init(this);
    };

    SearchFormService.prototype = {

        getParamsByModel: function () {
            var locationObj = {};
            for (var param in this) {
                var paramType = typeof (this[param]);
                if ((paramType === 'string' || paramType === 'number') && this[param] && this[param] !== DEFAULTS[param]) {
                    locationObj[param] = this[param];
                }
            }
            return locationObj;
        },

        submitBtn : function(){
            console.log('submit');
            var locationObj = this.getParamsByModel();
            if (location.path() !== '/search') {
                location.search(locationObj).path('/search');
            } else {
                location.search(locationObj);
                productService.findAndPopulate(locationObj);
            }
        },

        applyLocationParams : function(params){
            if (location.$$path === '/search' && params) {
                //reset all service val
                init(this);

                //set by params
                for (var param in params) {
                    if (param in DEFAULTS) {
                        if (typeof DEFAULTS[param] === 'number') {
                            this[param] = parseFloat(params[param]);
                        } else if (typeof DEFAULTS[param] === 'string') {
                            this[param] = params[param];
                        }
                    }
                }
            }

        }
    };

    angular.module('auction').service('SearchFormService',['$location', 'ProductService', SearchFormService]);


}());
