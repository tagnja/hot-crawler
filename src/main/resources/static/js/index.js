var getTypesUrl = domain + "/api/v1/types";
$.ajax({
    url: getTypesUrl,
    type: "get",
    async: false,
    dataType: "json",
    success: function(res){
        //console.log(JSON.stringify(res));
        var infoCates = res.data;
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
                var menuItem = "<div class='menu-item pointer' cateId='"+infoCate.id+"' code='"+infoTypes[i].id+"'>" + infoTypes[i].name + "</div>";
                $("#"+ menuRowId).append(menuItem);
            }
        }
        /*var contentBlock = "<div id='content-block'></div>";
        $("#content").append(contentBlock);*/

        // initial get infos
        var hash = window.location.hash.substr(1);
        var cateId, code;
        if (hash) {
            cateId = hash.split('-')[0];
            code = hash.split('-')[1];
        } else {
            cateId = infoCates[0].id;
            code = infoCates[0].infoTypes[0].id;
        }
        // console.log("cateId: " + cateId + ", code: " + code);
        getInfos(cateId, code);
    },
    error: function(res){
        console.log(JSON.stringify(res))
    }
});

$(".menu-item").click(function () {
    var cateId = $(this).attr("cateId");
    var code = $(this).attr("code");
    window.location = window.location.href.split('#')[0] + "#" + cateId +"-"+code;
    window.location.reload();
    //getInfos(cateId, code);
    //console.log(infos);
});

function selected(cateId, code)
{
    $(".menu-item").each(function() {
        if ($(this).attr("cateId") == cateId && $(this).attr("code") == code) {
            $(this).css({"background-color": "#445", "color": "#FFFFFF"});

        }
    });
}
function getInfos(cateId, code)
{
    selected(cateId, code);
    var infos;
    var getInfoUrl = domain + "/api/v1/cates/"+cateId+"/types/" + code + "/infos";
    $.ajax({
        url: getInfoUrl,
        type: "get",
        async: false,
        dataType: "json",
        success: function(res){
            infos = res.data;
            putInfos("#main", infos);
        },
        error: function(res){
            console.log(JSON.stringify(res))
        }
    });
    return infos;
}

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