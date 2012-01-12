(function($) {
	iMate = function() {}; // define root namesapce
	iMate.chat = function() {}; // define module class under root namesapce
	iMate.chat.ws = null; // declaring ws here.
	iMate.chat.init = function() {// app onetime configuration
		// Dialog
		$('#login-form').dialog({
			autoOpen : true,
			width : 600,
			modal : true,
			show : "fadeIn",
			hide : "fadeOut",
			buttons : {
				"Login" : function() {
					iMate.chat.requestLogin();
				}
			}
		});
		//datepicker
		//TODO $("#datepicker").datepicker();;
		
		// setup post button
		$('#post-button').click(this.doSendMessage);

		// start ws
		iMate.chat.ws = $.websocket("ws://localhost:8080/RelationshipService", {
			close : iMate.chat.wsClosing,
			events : {
				login : iMate.chat.handleLogin,
				message : iMate.chat.messageRecorded
			}
		});

	};
	iMate.chat.wsNotSupported = function() {
		$('#missing-sockets').fadeIn();
		$('#login-name').hide();
		$('#display').hide();
	};
	iMate.chat.wsClosing = function() {
		$('#login-form').dialog('open');
	};
	// ======= app specific methods =========//

	iMate.chat.requestLogin = function() {
		var username = $('#login-name').attr('value');
		iMate.chat.ws.send("login", {
			"messageKey" : username
		});
	};
	iMate.chat.handleLogin = function(response) { /* @Handles(login) */
		var ptext;
		if (response) {
			if (response.data.success) {
				ptext = 'Welcome ' + response.data.data.firstName + '!';
				$('#login-form').dialog('close');
				$('#message-form').fadeIn('slow');
			} else {
				ptext = response.data.messageKey;
			}
		} else {
			ptext = "Invalid response from server, please retry!";
		}
		$('<p/>').text(ptext).appendTo('#display');
	};
	iMate.chat.doSendMessage = function() {
		var msg = $('#message').attr('value');
		iMate.chat.ws.send("message", {
			"messageKey" : msg
		});
		$('<p/>').text(msg).appendTo('#display');
		$('#message').attr('value', '');// clean the inputbox
	};
	iMate.chat.messageRecorded = function(response) { /* @Handles(sendMessage) */
		console.log('Message successfully recorded at server');
	};

})(jQuery);
