var valore = 89;

$(function() {
	$(".mapaelContainer")
			.mapael(
					JSON.parse('{ "map" : { "name" : "world_countries",	"defaultArea" : {"attrs" : {"fill" : "#3366cc", "stroke" : "#ced8d0"}}},"legend" : {"area" : {"slices" : [ {"min" : "1" ,"attrs" : {"fill" : "#FF9900"}} ]}},"areas" : {"AF" : {"value" : "43","tooltip" : {"content" : "<span style=\'font-weight:bold;\'>Zimbabwe<\/span><br \/>Population : 12754378"}}}}'));
});