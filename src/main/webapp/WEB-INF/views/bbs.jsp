<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="contextPath" value="${mvc.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<title>Jersey SSE, MVC 1.0 sample</title>
<script src="${contextPath}/webjars/superagent/1.4.0/superagent.min.js"></script>
<script src="${contextPath}/webjars/knockout/3.4.0/knockout.js"></script>
<link rel="stylesheet" href="${contextPath}/bss.css">
</head>
<body>

	<p>
		<input type="text" data-bind="value: entry"> <input
			type="button" value="Post" data-bind="click: post">
	</p>

	<!-- ブロードキャストされたメッセージを表示する領域です。 -->
	<div data-bind="foreach: entries">
		<p data-bind="text: entry, css: { shown: shown }" class="broadcasted"></p>
	</div>

	<!-- このクライアントが画面を表示するまでに他のクライアントで
	     投稿されたメッセージを表示する領域です。 -->
	<c:forEach var="entry" items="${entries}">
		<p>${fn:escapeXml(entry)}</p>
	</c:forEach>

	<script>
		var context = {
			jaxrsRoot : '${mvc.contextPath}${mvc.applicationPath}'
		}
	</script>
	<script src="${contextPath}/bss.js"></script>
</body>
</html>
