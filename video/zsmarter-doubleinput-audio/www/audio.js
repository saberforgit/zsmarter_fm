

var exec = require('cordova/exec');
exports.startAudio = function( success, error,params) {
    // , ,Android de plugin class ,action,params
    exec(success, error, "AudioPlugin", "AudioPluginStart", [params]);
};

exports.starAudioParam = function( success, error,params) {
    // , ,Android de plugin class ,action,params
    exec(success, error, "AudioPlugin", "AudioPluginStartParam", [params]);
};

exports.stopAudio = function( success, error) {
    // , ,Android de plugin class ,action,params
    exec(success, error, "AudioPlugin", "AudioPluginStop", []);
};

exports.playAudio = function( success, error,params) {
    // , ,Android de plugin class ,action,params
    exec(success, error, "AudioPlugin", "AudioPluginPlay", [params]);
};//exports.post2img = function( success, error,params) {
//    exec(success, error, "Authorization", "post2img", [params]);
//};
//exports.postIdAndImg = function( success, error,params) {
//    exec(success, error, "Authorization", "postIdAndImg", [params]);
//};
