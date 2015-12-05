<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>Jersey SSE, MVC 1.0 sample</title>
<script src="/sample/webjars/superagent/1.4.0/superagent.min.js"></script>
<script src="/sample/webjars/knockout/3.4.0/knockout.js"></script>
</head>
<body>
	<p>
		<input type="text" data-bind="value: entry"> <input
			type="button" value="Post" data-bind="click: post">
	</p>

	<%-- ブロードキャストされたメッセージを表示する領域です。 --%>
	<div data-bind="foreach: entries">
		<p data-bind="text: $data"></p>
	</div>

	<%-- このクライアントが画面を表示するまでに他のクライアントで
	     投稿されたメッセージを表示する領域です。 --%>
	<c:forEach var="entry" items="${entries}">
		<p>${fn:escapeXml(entry)}</p>
	</c:forEach>

	<script>
		var es = new EventSource('/sample/app/bbs/listen');
		es.addEventListener('entry', function(event) {
			//ブロードキャストされたメッセージを表示します。
			model.entries.unshift(event.data);
		});

		var model = {
			entry : ko.observable(''),
			entries : ko.observableArray([]),
			post : function() {
				//メッセージを投稿します。
				superagent.post('/sample/app/bbs/post').set('Content-Type',
						'text/plain').send(model.entry()).end(function() {
					model.entry('');
				});
			}
		};

		ko.applyBindings(model);
	</script>
</body>
</html>