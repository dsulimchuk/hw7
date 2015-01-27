// Karma configuration
// http://karma-runner.github.io/0.12/config/configuration-file.html
// Generated on 2014-08-02 using
// generator-karma 0.8.3

module.exports = function(config) {
  'use strict';

  config.set({
    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: true,

    // base path, that will be used to resolve files and exclude
    basePath: '../',

    // testing framework to use (jasmine/mocha/qunit/...)
    frameworks: ['jasmine'],

    // list of files / patterns to load in the browser
    files: [
      'bower_components/jquery/dist/jquery.js',
      'bower_components/bootstrap-datepicker/js/bootstrap-datepicker.js',
      'bower_components/seiyria-bootstrap-slider/js/bootstrap-slider.js',
      'bower_components/angular/angular.js',
      'bower_components/angular-mocks/angular-mocks.js',

    //'bower_components/jquery/dist/jquery.js',
      'bower_components/angular-route/angular-route.js',
      'bower_components/lodash/dist/lodash.compat.js',
      'bower_components/restangular/dist/restangular.js',



      'app/scripts/**/*.js',
      'app/views/partial/*.html',
      'test/mock/**/*.js',
      'test/spec/**/*.js'
    ],
    preprocessors: {
      'app/views/partial/*.html': ['ng-html2js']
    },
    ngHtml2JsPreprocessor: {
      stripPrefix:'app/'
    },
    // list of files / patterns to exclude
    exclude: [],

    // web server port
    port: 8080,

    // Start these browsers, currently available:
    // - Chrome
    // - ChromeCanary
    // - Firefox
    // - Opera
    // - Safari (only Mac)
    // - PhantomJS
    // - IE (only Windows)
    browsers: [
         'Chrome'

    ],

    // Which plugins to enable
    plugins: [
      'karma-phantomjs-launcher',
      'karma-jasmine',
      'karma-chrome-launcher',
      'karma-firefor-launcher',
      'karma-ng-html2js-preprocessor'
    ],

    // Continuous Integration mode
    // if true, it capture browsers, run tests and exit
    singleRun: false,

    colors: true,

    // level of logging
    // possible values: LOG_DISABLE || LOG_ERROR || LOG_WARN || LOG_INFO || LOG_DEBUG
    logLevel: config.LOG_INFO,

    // Uncomment the following lines if you are using grunt's server to run the tests
    // proxies: {
    //   '/': 'http://localhost:9000/'
    // },
    // URL root prevent conflicts with the site root
    // urlRoot: '_karma_'

  });
};
