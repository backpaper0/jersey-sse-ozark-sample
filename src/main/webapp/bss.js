var es = new EventSource(context.jaxrsRoot + '/bbs/listen');
es.addEventListener('entry', function(event) {
	// ブロードキャストされたメッセージを表示します。
	var element = {
		entry : event.data,
		shown : ko.observable(false)
	};
	model.entries.unshift(element);
	window.setTimeout(function() {
		element.shown(true);
	}, 50);
});

var model = {
	entry : ko.observable(''),
	entries : ko.observableArray([]),
	post : function() {
		// メッセージを投稿します。
		superagent.post(context.jaxrsRoot + '/bbs/post').set('Content-Type',
				'text/plain').send(model.entry()).end(function() {
			model.entry('');
		});
	}
};

ko.applyBindings(model);
