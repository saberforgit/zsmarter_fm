
  var exec = require('cordova/exec');
  exports.playVideo = function (success, error, arg0) {
    exec(success, error, "VideoPlay", "playVideo", [arg0]);
  };

