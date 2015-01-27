'use strict';
describe('Service: ProductService', function(){
    var fakeRespond = {
        "items":[
                {
                    'id'          : 3,
                    'title'       : 'Item 3',
                    'thumb'       : '03.jpg',
                    'description' : 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore adipiscing elit. Ut enim.',
                    'timeleft'    : 2,
                    'watchers'    : 12,
                    'price'       : 39
                },
                {
                    'id'          : 4,
                    'title'       : 'Item 4',
                    'thumb'       : '04.jpg',
                    'description' : 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore adipiscing elit. Ut enim.',
                    'timeleft'    : 2,
                    'watchers'    : 12,
                    'price'       : 72
                }]
    };
    var srv,
        httpB;

    beforeEach(angular.mock.module("restangular"));
    beforeEach(module('auction'));

    beforeEach(inject(function(_$httpBackend_, ProductService){
        srv = ProductService;
        httpB =_$httpBackend_;
        //for getProduct
        httpB.whenGET(/.*\/product\/featured$/i).respond(fakeRespond);
        //for find
        httpB.whenGET(/.*\/product\/search\?/i).respond({'items':fakeRespond.items.concat(fakeRespond.items)});
        //for getProduct
        httpB.whenGET(/.*\/product\/\d+/i).respond(_.rest(fakeRespond.items));


    }));

    it('getProducts method should return array', function(){
        httpB.expectGET(/.*\/product\/featured?$/i);
        srv.getProducts().then(function(result){
            expect(result.length).toBe(2);
        });
        httpB.flush();
    });

    it('find method should return array', function(){
        httpB.expectGET(/.*\/product\/search\?/i);
        srv.find({ggg: 222}).then(function(result){
            expect(result.length).toBe(4);
        });
        httpB.flush();
    });

    it('getProductById method should return one element', function(){
        httpB.expectGET(/.*\/product\/\d+/i);
        srv.getProductById(222).then(function(result){
            expect(result.length).toBe(1);
            expect(result[0].id).toBe(4);
        });
        httpB.flush();
    });

    afterEach(function() {
        httpB.verifyNoOutstandingExpectation();
        httpB.verifyNoOutstandingRequest();
    });
});