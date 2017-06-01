

var exec = require('cordova/exec');
exports.copyFile = function( success, error,params) {
    // , ,Android de plugin class ,action,params
    exec(success, error, "FileUtilPlugin", "FileUtilCopy", [params]);
};

exports.deleteFile = function( success, error,params) {
    // , ,Android de plugin class ,action,params
    exec(success, error, "FileUtilPlugin", "FileUtilDelete", [params]);
};

exports.renameFile = function( success, error,params) {
    // , ,Android de plugin class ,action,params
    exec(success, error, "FileUtilPlugin", "FileUtilRename", [params]);
};

exports.makeDir = function( success, error,params) {
    // , ,Android de plugin class ,action,params
    exec(success, error, "FileUtilPlugin", "FileUtilMakeDir", [params]);
    
};
exports.getFileName = function( success, error,params) {
    // , ,Android de plugin class ,action,params
    exec(success, error, "FileUtilPlugin", "FileUtilGetFileName", [params]);
};
exports.getFileLength = function( success, error,params) {
    // , ,Android de plugin class ,action,params
    exec(success, error, "FileUtilPlugin", "FileUtilGetFileLength", [params]);
};
exports.getFile = function( success, error,params) {
    // , ,Android de plugin class ,action,params
    exec(success, error, "FileUtilPlugin", "FileUtilGetFile", [params]);
};
exports.getAllFile = function( success, error,params) {
    // , ,Android de plugin class ,action,params
    exec(success, error, "FileUtilPlugin", "FileUtilGetAllFile", [params]);
};