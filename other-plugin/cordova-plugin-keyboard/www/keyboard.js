
var exec = require('cordova/exec');

exports.showKeyBoard = function( success, error,option) {
    exec(success, error, "KeyBoard", "showKeyBoard", [option]);
};

