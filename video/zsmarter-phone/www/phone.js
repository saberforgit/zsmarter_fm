
		var exec = require('cordova/exec');
		exports.startPhoneCallListener= function(success, error) {
	    exec(success, error, "Plugin_ZS_Phone", "startPhoneCallListener", []);
			};
			exports.stopPhoneCallListener= function(success, error) {
      exec(success, error, "Plugin_ZS_Phone", "stopPhoneCallListener", []);
      };
