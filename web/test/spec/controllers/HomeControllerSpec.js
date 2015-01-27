'use strict';

describe('Controller: MainCtrl', function () {


  var fakeRespond = [{
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
    }];

  var controller,
      scope,
      rootScope,
      spyService;

  // load the controller's module
  beforeEach(module('auction', function($provide) {
    spyService = jasmine.createSpyObj('ProductService', ['getProducts']);
    $provide.value('ProductService', spyService);
  }));

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope, ProductService, $q) {
    rootScope = $rootScope;
    scope = $rootScope.$new();

    //promise for controller spy
    var promise = $q(function(resolve) {
          resolve(fakeRespond);
          });
    spyService.getProducts.and.returnValue(promise);

    controller = $controller('HomeController', {
      $scope: scope
    });
  }));

  it('initial amount of products is 0', function () {
    expect(controller.products.length).toBe(0);
  });

  it('should call getProducts function and populate products through promise', function() {
    expect(controller).toBeDefined();
    expect(spyService.getProducts).toHaveBeenCalled();
    expect(controller.products.length).toBe(0);
    rootScope.$digest();
    expect(controller.products.length).toBe(2);
  });

});
