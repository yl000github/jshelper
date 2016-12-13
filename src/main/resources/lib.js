/**
*注入的方法类
*/
//print("lib.js");
function require(className){
	if(className){
		return Java.type(className);
	}
}
var load1=load;
load=function(path){
//	print($_jsDir_$);
	load1($_jsDir_$+path);
}