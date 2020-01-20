generateMenu();
showStatData();
selectTab();
generateInfos("#main", infos);
console.log(thisSiteInfo);

/**
 ****************************
 * functions
 ****************************
 */

function generateMenu(){
	for (var j = 0; j < infoCates.length; j++)
	{
		var infoCate = infoCates[j];
		var  menuRowId = "menu-row-"+j;
		var menuRow = '<div class="menu-row" id="'+menuRowId+'"></div>'
		$("#menu-bar").append(menuRow)
		var infoCateCol = '<div class="menu-item-cate">'+infoCate.name+'</div>';
		$("#"+menuRowId).append(infoCateCol);
		var infoTypes = infoCate.infoTypes;
		for (var i = 0; i < infoTypes.length; i++)
		{
			var menuItem = "<a class='menu-item pointer' code='"+infoTypes[i].code+"'><span class='no-break-word'>"
				+ infoTypes[i].name + "</span></a>";
			if (i < infoTypes.length - 1)
			{
				menuItem = menuItem + "<div class='menu-item-split'></div>";
			}
			$("#"+ menuRowId).append(menuItem);
		}
	}
}

function showStatData() {
	var visitUserCountDiv = '<div style="margin: 0 auto;height: 50px; min-width: 200px; max-width: 1100px;">\n' +
		'            <div style="margin-left: 10px; color: darkgray;">今日访问人数：'+visitUserCount+'</div>\n' +
		'        </div>';
	$("#footer").html(visitUserCountDiv);
}

function generateInfos(elementId, infos) {
    $(elementId).empty();
    if (infos.length > 0){
        var infoItem;
        for (var j = 0; j < infos.length; j++)
        {
            if (typeof window.orientation !== 'undefined') {
                infoItem = "<div class='info-item'><a href='"+infos[j].url+"'>" + (j+1) + ". " + infos[j].title  + "</a></div>";
            }else {
                infoItem = "<div class='info-item'><a target='_blank' href='"+infos[j].url+"'>" + (j+1) + ". " + infos[j].title  + "</a></div>";
            }
            $(elementId).append(infoItem);
        }
    }else {
        var blankTip = "<div style='text-align: center;'>该站点暂无数据！</div>";
        $(elementId).append(blankTip);
    }
}

function selectTab()
{
	var code;
	if (window.location.href.split("?").length == 2)
	{
		code = window.location.href.split("?")[1].split("=")[1];
	}
	if (! code) {
		code = infoCates[0].infoTypes[0].code;
	}
	console.log("code is : " + code);
    // console.log("selected: cateId=" + cateId + ", code=" + code)
    $(".menu-item").each(function() {
        if ($(this).attr("code") == code) {
            $(this).css({"background-color": "#445", "color": "#FFFFFF"});
        }
    });
    showNote();
}
function showNote(){
	$("#tip").empty();
	if (thisSiteInfo.type == "abroad"){
        console.log("thisSiteInfo type is : " + thisSiteInfo)
	    $("#tip").html("(该网站需要科学上网！)");
    }
}
// function checkNetwork() {
//     var request = $.ajax({
//         url: "https://google.com",
//         type: "get",
//         cache: false,
//         dataType: "jsonp",
//         processData: false,
//         timeout: 10000,
//         complete: function (data) {
//             if (data.status != 200) {
//                 $("#tip").html("访问该网站需要科学上网!");
//                 request.abort();
//             }
//         }
//     });
// }

/**
 *************************
 * Events
 *************************
 */

$(".menu-item").click(function () {
	var code = $(this).attr("code");
	/* window.location = window.location.href.split('#')[0] + "#" + cateId +"-"+code;
	 window.location.reload();*/
	window.location.href = window.location.href.split('#')[0].split('?')[0] + "?tab=" +code;
	//getInfos(cateId, code);
	//console.log(infos);
});
