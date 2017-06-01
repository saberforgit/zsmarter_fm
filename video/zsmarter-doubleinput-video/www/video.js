
  var exec = require('cordova/exec');

  exports.recordVideo = function (success, error, arg0) {
    exec(success, error, "Video", "recordVideo", [arg0]);
  };
  exports.playVideo = function (success, error, arg0) {
    exec(success, error, "Video", "playVideo", [arg0]);
  };
  exports.importVideo = function (success, error,arg0) {
    exec(success, error, "Video", "importVideo", [arg0]);
  };
  exports.getVideoParams = function(success, error,arg0) {
    exec(success, error, "Video", "getVideoParams", [arg0]);
  };