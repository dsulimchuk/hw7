'use strict';
describe('test', function(){

    it("The 'toBeCloseTo' matcher is for precision math comparison", function() {
        var pi = 3.1415926, e = 2.78;

        expect(pi).not.toBeCloseTo(e, 2);
        debugger;
        expect(pi).toBeCloseTo(e, 1);
    });
});