
var exec = require('cordova/exec');
exports.createWaterMark = function( success, error,option) {
    exec(success, error, "WaterMark", "createWaterMark", [option]);
};
