<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ include file="head.jsp" %>
<html>
<script type="text/javascript" src="${ctx}/js/jquery/jquery-1.11.1.js"></script>
<script type="text/javascript" src="${ctx}/js/joy/core.js"></script>
<script type="text/javascript" src="${ctx}/js/joy/service/validate.js"></script>
<script type="text/javascript" src="${ctx}/js/joy/plugin/jquery/loading.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/joy/plugin/jquery/loading.css">

<body>
<div id="jqp-wrap" style="width:400px;height:300px"></div>
<button onclick="startLoading()">Start</button>
</body>
</html>
<script>
function startLoading(){
    $(document.body).joyloading({
    	timeout:3000
    });
}
</script>