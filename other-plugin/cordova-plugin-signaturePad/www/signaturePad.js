var exec = require('cordova/exec');

exports.showSignature = function(arg0, success, error) {
    exec(success, error, "signaturePad", "showSignature", [arg0]);
};
