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
        var menuItem = "<a class='menu-item pointer' cateId='"+infoCate.id+"' typeId='"+infoTypes[i].id+"'><span class='no-break-word'>" +
            infoTypes[i].name + "</span></a>";
        $("#"+ menuRowId).append(menuItem);
    }
}
/*var contentBlock = "<div id='content-block'></div>";
$("#content").append(contentBlock);*/

// initial get infos
// var hash = window.location.hash.substr(1);
var param;
if (window.location.href.split("?").length == 2)
{
    param = window.location.href.split("?")[1].substr(4);
}
var cateId, typeId;
if (param) {
    cateId = param.split('-')[0];
    typeId = param.split('-')[1];
} else {
    cateId = infoCates[0].id;
    typeId = infoCates[0].infoTypes[0].id;
}
// console.log("cateId: " + cateId + ", typeId: " + typeId);
selected(cateId, typeId);
putInfos("#main", infos);

function putInfos(elementId, infos) {
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

$(".menu-item").click(function () {
    var cateId = $(this).attr("cateId");
    var typeId = $(this).attr("typeId");
   /* window.location = window.location.href.split('#')[0] + "#" + cateId +"-"+typeId;
    window.location.reload();*/
   window.location.href = window.location.href.split('#')[0].split('?')[0] + "?tab=" + cateId +"-"+typeId;
    //getInfos(cateId, typeId);
    //console.log(infos);
});

function selected(cateId, typeId)
{
    // console.log("selected: cateId=" + cateId + ", typeId=" + typeId)
    $(".menu-item").each(function() {
        if ($(this).attr("cateId") == cateId && $(this).attr("typeId") == typeId) {
            $(this).css({"background-color": "#445", "color": "#FFFFFF"});
        }
    });
}