var exec = require('cordova/exec');

  exports.recordVideo = function (success, error, arg0) {
    exec(success, error, "Plugin_ZS_Video", "recordVideo", [arg0]);
 };

