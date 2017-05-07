$(document).ready(function() {
	$("#searchKeywords").bind("focus", function() {
		$("#searchKeywords").removeClass("inputBlur").addClass("inputFocus");
	});
	$("#searchKeywords").bind("blur", function() {
		$("#searchKeywords").removeClass("inputFocus").addClass("inputBlur");
	});
	$("#searchButton").bind("mouseover", function() {
		$("#searchButton").css("background-color", "#2E6DA4");
	});
	$("#searchButton").bind("mouseout", function() {
		$("#searchButton").css("background-color", "##337AB7");
	});
});