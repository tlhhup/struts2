<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/WdatePicker.js"></script>
</head>
<body>

	<div align="center">
		<form action="${pageContext.request.contextPath }/userAction_login" method="post">
			<table>
				<tr>
					<td>用户名：</td>
					<td><input name="userName" type="text"></td><s:fielderror value="#nameError"/>
				</tr>
				<tr>
					<td>密码：</td>
					<td><input name="password" type="password"></td>
				</tr>
				<tr>
					<td>日期：</td>
					<td><input name="date" type="datetime"
						onClick="WdatePicker();" readonly="readonly"></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit" value="提交">&nbsp;<input
						type="reset" value="重置"></td>
				</tr>
			</table>
		</form>
	</div>

</body>
</html>