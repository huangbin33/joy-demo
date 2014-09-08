<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ include file="common.jsp" %>
<html>
<script type="text/javascript" src="${ctx}/js/jquery/jquery-1.11.1.js"></script>
<script type="text/javascript" src="${ctx}/js/joy/core.js"></script>
<script type="text/javascript" src="${ctx}/js/joy/service/validate.js"></script>
<!--[if lt IE 9]>
<script src="${ctx}/js/webuploader/vendor/html5shiv.js"></script>
<script src="${ctx}/js/webuploader/vendor/respond.min.js"></script>
<![endif]-->
<script type="text/javascript" src="${ctx}/js/webuploader/webuploader.js"></script>
<script type="text/javascript" src="${ctx}/js/joy/plugin/jquery/uploader/uploader.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/webuploader/webuploader.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/joy/plugin/jquery/uploader/uploader.css">
<style>
#jqp-wrap .statusBar div.btns{
	position: static;
}
</style>
<body>
<div id="jqp-wrap" style="width:400px;height:300px"></div>
</body>
</html>
<script>
$(function(){
    $("#jqp-wrap").joyuploader({
        pickLabel: "点击添加",
        pickTips: "或将文件拖到这里",
        //uploadLabel: "开始上传",
        //pickOtherLabel: "继续添加",
        //multiple: false,
        //auto: true,
        thumbWidth: 250,
		thumbHeight: 250,
		showStatusBar: true,
        baseOpts:{
            fileNumLimit: 10,
            server: '${uploadURL4Json}'
        },
        onFileUpload: function(file, response){
        	console.log(response.result);
        },
        BASE_URL: "${pageContext.request.contextPath}"
    });
});
</script>