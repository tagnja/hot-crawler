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
                var menuItem = "<div class='menu-item pointer' cateId='"+infoCate.id+"' typeId='"+infoTypes[i].id+"'>" + infoTypes[i].name + "</div>";
                $("#"+ menuRowId).append(menuItem);
            }
        }
        var contentBlock = "<div id='content-block'></div>";
        $("#content").append(contentBlock)

        // initial get infos
        getInfos(infoCates[0].id, infoCates[0].infoTypes[0].id);
    },
    error: function(res){
        alert(JSON.stringify(res))
    }
});

$(".menu-item").click(function () {
    var typeId = $(this).attr("typeId");
    var cateId = $(this).attr("cateId");
    getInfos(cateId, typeId);
    //console.log(infos);
});

function getInfos(cateId, typeId)
{
    var infos;
    var getInfoUrl = domain + "/api/v1/cates/"+cateId+"/types/" + typeId + "/infos";
    $.ajax({
        url: getInfoUrl,
        type: "get",
        async: true,
        dataType: "json",
        success: function(res){
            infos = res.data;
            putInfos("#content-block", infos);
        },
        error: function(res){
            alert(JSON.stringify(res))
        }
    });
    return infos;
}

function putInfos(elementId, infos) {
    $(elementId).empty();
    for (var j = 0; j < infos.length; j++)
    {
        var infoItem = "<div class='info-item'><a href='"+infos[j].url+"'>" + (j+1) + ". " + infos[j].title  + "</a></div>";
        $(elementId).append(infoItem);
    }
}