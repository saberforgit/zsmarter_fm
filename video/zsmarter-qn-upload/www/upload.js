	var exec = require('cordova/exec');
		exports.upload= function(success, error,args) {
	    exec(success, error, "Plugin_QN_Upload", "upload", [args]);
			};
			exports.cancel= function(success, error) {
      	    exec(success, error, "Plugin_QN_Upload", "cancel", []);
      			};
