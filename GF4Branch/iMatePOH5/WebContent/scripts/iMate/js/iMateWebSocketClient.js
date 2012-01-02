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
	var _this = this; // using closure : as onmessage will be called from websocket context not from app context.
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
	this._ws;
	this._url;
	this.setURL = function(_wsURL) { /* TODO @call */
		this._url=_wsURL;
	};
	this.wsFrameName = '#websockets-frame'; //default name of iframe element on page.
	this.count = 0;
	this.autoReconnect = true; //by default reconnects on close each 5 sec.
	this.wsInit = function() {
		if ("WebSocket" in window || "MozWebSocket" in window) {
			this.wsListen();
		} else {
			this.wsNotSupported();
		}
		;
	};
	this.wsNotSupported = function() { /* TODO @override */
		console.log('wsapplication wsNotSupported in your browser, No server side communication can be done.');
	};
	this.wsListen = function() {
		//this._url=this._url || arguments[0];
		_connectURL = this._ws?this._ws.URL:this._url;
		alert(JSON.stringify(_connectURL));
		//console.log('wsapplication wsListen..');
		$(this.wsFrameName).attr('src', this._url + '?' + this.count++);
		var Socket = "MozWebSocket" in window ? MozWebSocket : WebSocket;
		this._ws = new Socket(_connectURL);
		this._ws.onopen = this.wsRegister;
		this._ws.onclose = this.wsClosed;
		this._ws.onmessage = function(evt) {
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
	this.wsDefaultCallback = function(_resp) {
		console.log('wsapplication wsDefaultCallback :' + JSON.stringify(_resp));
	};
	this.wsNotificationCallback = function(_notif) { /* TODO @override */
		console.log('wsapplication wsNotificationCallback:' + _notif);
	};
	this.wsRegister = function() {
		console.log('wsapplication wsRegister: doing registration.');
		var req = new wsprotocolrequest("session_set_notificationHandler", "_this.wsNotificationCallback");
		this._ws.send(JSON.stringify(req));
		_this.wsReady();
	};
	this.wsClosing = function() { /* TODO @override */
		console.log('wsapplication wsClosing.');
	};
	this.wsClosed = function() {
		_this.wsClosing();
		// NOT IMPLEMENTED  if(_this.autoReconnect){//re connects to ws in case closed, due to ideal timeout at user or server level.
			//try to reconnect in 5 seconds
		// NOT IMPLEMENTED console.log('wsapplication wsClosed will reconnect after 5 second.');
		// NOT IMPLEMENTED _this.wsListen(); // NOT IMPLEMENTED
			//window.setTimeout(_this.wsListen.bind(_this), 5000);// wrong scope.
		// NOT IMPLEMENTED }else{
			console.log('wsapplication wsClosed.');
		// NOT IMPLEMENTED}
	};
	this.wsSend = function(_operationName,_handlerName,_reqData) { /* TODO @call */
		var msg = JSON.stringify(new wsrequest(_operationName, _handlerName, _reqData));
		console.log('Sending:'+msg);
		this._ws.send(msg);
	};
};