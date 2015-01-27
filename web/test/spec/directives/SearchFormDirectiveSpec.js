'use strict';
describe('Directive: SearchFormDirective', function() {
    var el;

    beforeEach(module('auction'));
    beforeEach(module('views/partial/SearchFormDirective.html'));
    beforeEach(module('views/partial/PriceRangeDirective.html'));


    beforeEach(inject(function($compile, $rootScope, $templateCache){
        el = angular.element('<auction-search-form></auction-search-form>');
        el = $compile(el)($rootScope);
        //dump($templateCache.get('views/partial/SearchFormDirective.html'));
        $rootScope.$digest();
        //dump(el);

    }));
    it('Inner HTML after digest must containt  form name="searchForm"', function() {
        //debugger;

        expect(el[0]).toBeDefined();
        expect(el.find('form').attr('name')).toBe('searchForm');

    });
})