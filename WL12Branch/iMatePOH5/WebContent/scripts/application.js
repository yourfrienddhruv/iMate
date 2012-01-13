var count = 0;
//var websocket = null;
var name  = null;

var app = {
	websocket:null,
    url: 'ws://localhost:8080/RelationshipService',
    //url: 'wss://localhost:8181/RelationshipService',
    initialize: function() {
        if ("WebSocket" in window || "MozWebSocket" in window) {
            $('login-name').focus();
            app.listen();
        } else {
            $('missing-sockets').style.display = 'inherit';
            $('login-name').style.display = 'none';
            $('login-button').style.display = 'none';
            $('display').style.display = 'none';
        }
    },
    listen: function() {
        $('websockets-frame').src = app.url + '?' + count;
        count ++;
    },
    login: function() {
        name = $F('login-name');
        if (! name.length > 0) {
            $('system-message').style.color = 'red';
            $('login-name').focus();
            return;
        }
        $('system-message').style.color = '#2d2b3d';
        $('system-message').innerHTML = name + ':';

        $('login-button').disabled = true;
        $('login-form').style.display = 'none';
        $('message-form').style.display = '';

        var Socket = "MozWebSocket" in window ? MozWebSocket : WebSocket;
        websocket = new Socket(app.url);
        websocket.onopen = function() {
            // Web Socket is connected. You can send data by send() method
            //websocket.send('login:' + name);
        	var req={};
        	req.type="PROTOCOL";
        	req.operation="session_set_notificationHandler";
        	req.callback="window.parent.app.update";
        	//alert(JSON.stringify(req));
        	websocket.send(JSON.stringify(req));
        };
        websocket.onmessage = function (evt) {
            eval(evt.data);
            $('message').disabled = false;
            $('post-button').disabled = false;
            $('message').focus();
            $('message').value = '';
        };
        websocket.onclose = function() {
            var p = document.createElement('p');
            p.innerHTML = name + ': has left the chat';

            $('display').appendChild(p);

            new Fx.Scroll('display').down();
        };
    },
    post: function() {
        var message = $F('message');
        if (!message > 0) {
            return;
        }
        $('message').disabled = true;
        $('post-button').disabled = true;
        var req={};
    	//req.type=null; USER 
    	req.operation="login";
    	req.callback="window.parent.app.update";
    	req.request={"messageKey":message};
    	//alert(JSON.stringify(req));
    	websocket.send(JSON.stringify(req));
    },
    update: function(response) {
    	var ptext ="";
        if (response) {
        	if(response.operation="login"){
	        	if (response.success=="true") {
	                ptext=document.createTextNode(response.request.data.val);
	        	}else{
	        		//failed login
	        		ptext=document.createTextNode(response.request.messageKey);
	        	}
        	}else{
        		ptext=document.createTextNode("unhandled operation:"+response.operation);
        	}
        }else{
        	ptext=document.createTextNode("Invalid response received from server");
        }
        var p = document.createElement('p');
        //IE p.innerHTML = data.name + ': ' + data.message;
        p.appendChild(ptext);
        $('display').appendChild(p);
        new Fx.Scroll('display').down();
    }
};

var rules = {
    '#login-name': function(elem) {
        Event.observe(elem, 'keydown', function(e) {
            if (e.keyCode == 13) {
                $('login-button').focus();
            }
        });
    },
    '#login-button': function(elem) {
        elem.onclick = app.login;
    },
    '#message': function(elem) {
        Event.observe(elem, 'keydown', function(e) {
            if (e.shiftKey && e.keyCode == 13) {
                $('post-button').focus();
            }
        });
    },
    '#post-button': function(elem) {
        elem.onclick = app.post;
    }
};
Behaviour.addLoadEvent(app.initialize);
Behaviour.register(rules);

