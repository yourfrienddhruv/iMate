/*
 * jQuery Web Sockets Plugin v0.0.1
 * http://code.google.com/p/jquery-websocket/
 *
 * This document is licensed as free software under the terms of the
 * MIT License: http://www.opensource.org/licenses/mit-license.php
 * 
 * Copyright (c) 2010 by shootaroo (Shotaro Tsubouchi).
 */
(function($){
$.extend({
	websocketSettings: {
		open: function(){},
		close: function(){},
		message: function(){},
		notsupported:function(){console.log('WebSocket not supported : Please use mordern browser.');},
		options: {},
		events: {}
	},
	websocket: function(url, s) {
		var Socket = "MozWebSocket" in window ? MozWebSocket : WebSocket;
		if (Socket==undefined){
			$.websocketSettings.notsupported.call(this);
			return null; //can't create ws
		}
		var _ws = Socket ? new Socket( url ) : {
			send: function(m){ return false; },
			close: function(){}
		};
		_ws._settings = $.extend(true, s , $.extend(true, {}, $.websocketSettings, s));
		$(_ws)
			.bind('open', _ws._settings.open)
			.bind('close', _ws._settings.close)
			.bind('message', _ws._settings.message)
			.bind('message', function(e){
				var m = $.evalJSON(e.originalEvent.data);
				var h = _ws._settings.events[m.type];
				if (h) h.call(this, m);
				else  {console.log('Receieved:'+e.originalEvent.data); }
			});
		_ws._send = _ws.send; //take reference of original send()
		_ws.send = function(type, data) {
			var m = {type: type};
			m = $.extend(true, m, $.extend(true, {}, _ws._settings.options, m));
			if (data) m['data'] = data;
			return this._send($.toJSON(m));
		};
		$(window).unload(function(){ _ws.close(); _ws = null; });
		return _ws;
	}
});
})(jQuery);
