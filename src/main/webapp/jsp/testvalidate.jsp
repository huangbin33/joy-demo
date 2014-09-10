<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ include file="head.jsp" %>
<html>
<head>
<script type="text/javascript" src="${ctx}/js/jquery/jquery-1.11.1.js"></script>
<script type="text/javascript" src="${ctx}/js/joy/core.js"></script>
<script type="text/javascript" src="${ctx}/js/joy/service/validate.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery/plugins/validation/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/js/joy/plugin/jquery/validator.js"></script>
<link rel="stylesheet" href="${ctx}/css/joy/plugin/jquery/validator.css">
</head>
<body>
	<form id="vf" action="">
		<table>
			<tr>
				<td>必填</td>
				<td><input type="text" name="req" class="required"></td>
			</tr>
			<tr>
				<td>数字</td>
				<td><input type="text" name="num" class="required number " range="[5,100]"></td>
			</tr>
			<tr>
				<td>昵称</td>
				<td><input type="text" name="nick" class="required nick"></td>
			</tr>
			<tr>
				<td>Email</td>
				<td><input type="text" name="email" class="email"></td>
			</tr>
			<tr>
				<td>手机</td>
				<td><input type="text" name="mobile" minlength="5" class="mobile"></td>
			</tr>
			<tr>
				<td>电话</td>
				<td><input type="text" name="tel" class="phone"></td>
			</tr>
			<tr>
				<td>身份证</td>
				<td><input type="text" name="idcard" class="idcard"></td>
			</tr>
			<tr>
				<td>下拉</td>
				<td><select name="sel" class="required">
						<option value=""></option>
						<option value="111">AAA</option>
						<option value="222">BBB</option>
						<option value="333">CCC</option>
				</select></td>
			</tr>
			<tr>
				<td>单选</td>
				<td><input type="radio" value="1" name="gender" class="required">男 <input
					type="radio" value="0" name="gender">女
					<label for="gender" class="error"></label>
					</td>
			</tr>
			<tr>
				<td>多选</td>
				<td><input type="checkbox" name="course">语文 <input
					type="checkbox" name="course">数学 <input type="checkbox"
					name="course" class="required">应用</td>
			</tr>
			<tr>
				<td>隐藏</td>
				<td><input type="hidden" name="hide" class="required"></td>
			</tr>
		</table>

		<button type="submit">提交</button>

		<button type="button" onclick="$('#vf').submit();">提交2</button>
	</form>
</body>
<script type="text/javascript">
	$(function() {
		$("#vf").joyvalidator({

		});
	});
</script>
</html>