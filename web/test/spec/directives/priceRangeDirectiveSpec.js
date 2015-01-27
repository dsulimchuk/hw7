'use strict';
describe('Directive: PriceRangeDirective', function() {
    var el,
        scope,
        lPr,
        hPr,
        slPr,
        childScope;

    beforeEach(module('auction'));
    beforeEach(module('views/partial/PriceRangeDirective.html'));


    beforeEach(inject(function($compile, $rootScope, $templateCache){
        scope = $rootScope;
        el = angular.element('<auction-price_range low-price="9" high-price="501"></auction-price_range>');
        el = $compile(el)($rootScope);
        //dump($templateCache.get('views/partial/SearchFormDirective.html'));
        scope.$digest();
        lPr = el.find('#PriceRangeDir-LowPrice');
        hPr = el.find('#PriceRangeDir-HighPrice');
        slPr = el.find('div.slider');
        childScope = hPr.scope();

    }));

    it('should contain elements', function() {
        expect(lPr.length).toBe(1);
        expect(hPr.length).toBe(1);
        expect(slPr.length).toBe(1);
    });

    it('scope must contain initial values', function(){
        //check initial values
        expect(childScope.lowPrice).toBe(9);
        expect(childScope.highPrice).toBe(501);
    });

    it('scope must reflect changes to input elements', function(){
        expect(parseFloat(lPr.val())).not.toBe(100);
        expect(parseFloat(hPr.val())).not.toBe(200);

        childScope.lowPrice = 100;
        childScope.highPrice = 200;

        scope.$digest();

        expect(parseFloat(lPr.val())).toBe(100);
        expect(parseFloat(hPr.val())).toBe(200);
    });

    //
    //it('scope must reflect changes to input elements', function(){
    //    lPr.val(999);
    //    lPr.trigger('oninput');
    //
    //    scope.$apply();
    //    var ff = childScope.lowPrice;
    //    expect(childScope.lowPrice).toBe(999);
    //
    //    debugger;
    //});
})