/*
 * jQuery REST Service Plugin v0.0.1.D.2 with Dhruv's Updates
 * inspired from http://code.google.com/p/jquery-websocket/
 *
 * Author  (c) 2012 by yourfrienddhruv (Dhruv Gohil). 
 */
(function($){
$.extend({
	restserviceSettings: {
		open: function(){},
		close: function(){},
		message: function(){},
		error:function(e){console.log('REST Service error : '+e);}, 
		notsupported:function(){}, //Just to comply with WS library format
		options: {},
		events: {}
	},
	restservice: function(url, s) {
			var _ws = {}; //REST web service End Point
			_ws._url = url;
			_ws._settings = $.extend(true, s , $.extend(true, {}, $.restserviceSettings, s));
			$(_ws)
				.bind('open', _ws._settings.open)
				.bind('close', _ws._settings.close)
				.bind('error', _ws._settings.error)
				.bind('message', _ws._settings.message)
				.bind('message', function(){
					// arguments[0] is always JQuery event type 'message'
					var m = arguments[1]; //is actual data passed
					var h = _ws._settings.events[m.type];
					if (h) h.call(this, m);
					else  {console.log('Receieved:'+m); }
				});
			_ws.send = function(type, data) {
				console.log('Sending:'+JSON.stringify(data));
				 //TODO to use _ws._settings.options 
				return $.ajax({  
					  type: "POST",//For cross domain automatically uses GET, which has 2083 Char Limit including Data
					  url: this._url+'/'+type,// e.g. "http://localhost:8080/iMateGroupon/s/Coupon + / + searchLatest"
					  jsonp:'c', //callback function name
					  cache:true, //don't append time stamp,But due to dynamic callback implementation JQuery will generate unique request each time. 
					  dataType: 'jsonp', //TO ALLOW CROSS DOMAIN request jsonp/script is required.
					  data: {'j':encodeURIComponent(JSON.stringify(data))}, //GET has URL encoding problems thus encodeURIComponent.
					  error :   function(err){$(_ws).trigger('error',err);},
					  success:  function(msg){$(_ws).trigger('message',msg);}
			 		});
			};
		return _ws;		
	}
});
})(jQuery);
