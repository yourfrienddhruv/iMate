var iMate = {}; // define root namesapce
function wsrequest(_operation, _callbackName, _data) {
	this.operation = _operation;
	this.callback = _callbackName;
	this.data = _data;
};
function wsprotocolrequest(_operation, _callback, _data) {
	this.operation = _operation;
	this.callback = _callback;
	this.data = _data;
	this.type = "PROTOCOL";
};

function wsapplication() {
	// define app
	this.init = function() { // app entry point
		this.setup();
		this.wsInit();
	};
	this.setup = function() { /* TODO @override */
		// app onetime configuration
		console.log('wsapplication setup done.');
	};
	// app sepcific variables and methods follows..
	this.ws;
	this.url; /* TODO @override */
	this.count = 0, this.wsInit = function() {
		if ("WebSocket" in window || "MozWebSocket" in window) {
			this.wsListen();
			this.wsReady();
		} else {
			this.wsNotSupported();
		}
		;
	};
	this.wsNotSupported = function() { /* TODO @override */
		console.log('wsapplication wsNotSupported in your browser, No server side communication can be done.');
	};
	this.wsListen = function() {
		//console.log('wsapplication wsListen..');
		$('#websockets-frame').attr('src', this.url + '?' + this.count);
		this.count++;
		var Socket = "MozWebSocket" in window ? MozWebSocket : WebSocket;
		ws = new Socket(this.url);
		ws.onopen = this.wsRegister;
		ws.onclose = this.wsClosed;
		var _this = this; // using closure : as onmessage will be called from websocket context not from app context.
		ws.onmessage = function(evt) {
			// console.log('wsapplication ws.onmessage:'+evt.data.substring(5,evt.data.length-1) + '\n'+evt.data.substring(0,4));
			if (evt.data.substring(0, 4) == "null") {
				// if callback is null then delegate to default callback, remove null callback >> null({xyz:abc})
				_this.wsDefaultCallback(JSON.parse(evt.data.substring(5, evt.data.length - 1)));
			} else {
				// calling handlers automatically on response
				eval(evt.data);
			}
		};
		console.log('wsapplication wsListen done.');
	};
	this.wsReady = function() {/* TODO @override */
		console.log('wsapplication wsReady.');
	};
	this.wsDefaultCallback = function(resp) {
		console.log('wsapplication wsDefaultCallback :' + JSON.stringify(resp));
	};
	this.wsNotificationCallback = function(notif) { /* TODO @override */
		console.log('wsapplication wsNotificationCallback:' + notif);
	};
	this.wsRegister = function() {
		console.log('wsapplication wsRegister: doing registration.');
		var req = new wsprotocolrequest("session_set_notificationHandler", "_this.wsNotificationCallback");
		ws.send(JSON.stringify(req));
	};
	this.wsClosed = function() {
		console.log('wsapplication wsClosed done.');
	};
};