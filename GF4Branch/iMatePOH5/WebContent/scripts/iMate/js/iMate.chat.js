iMate.chat = function() {};  // define module class under root namesapce
iMate.chat.prototype = new wsapplication();// extend from wsapplication
iMate.chat.app = new iMate.chat(); // create instance of module application
iMate.chat.app.setURL('ws://localhost:8080/RelationshipService');
iMate.chat.app.setup = function() {/* @Overrride */// app onetime configuration
	// Converting all button to Jquey Buttons
	$("input:button").button();
	// Dialog
	$('#login-form').dialog({
		autoOpen : true,
		width : 600,
		modal : true,
		show : "fadeIn",
		hide : "fadeOut",
		buttons : {
			"Login" : function() {
				iMate.chat.app.requestLogin();
			}
		}
	});
	// setup post button
	$('#post-button').click(this.doSendMessage);

};
iMate.chat.app.wsNotSupported = function() {/* @Overrride */
	$('#missing-sockets').fadeIn();
	$('#login-name').hide();
	$('#display').hide();
};
iMate.chat.app.wsClosing = function() { /* @Overrride */
	// console.log('ws closed, asking for reauth.');
	$('#login-form').dialog('open');
};
// ======= app specific methods =========//

iMate.chat.app.requestLogin = function() {
	var username = $('#login-name').attr('value');
	this.wsSend("login","iMate.chat.app.handleLogin",{"messageKey" : username});
};
iMate.chat.app.handleLogin = function(response) { /* @Handles(login) */
	// console.log('Handling response' + JSON.stringify(response));
	var ptext;
	if (response) {
		if (response.data.success) {
			ptext = 'Welcome ' + response.data.data.firstName + '!';
			// console.log('Closing dialog');
			$('#login-form').dialog('close');
			$('#message-form').fadeIn('slow');
		} else {
			// console.log('Failed login');
			ptext = response.data.messageKey;
		}
	} else {
		ptext = "Invalid response from server, please retry!";
	}
	$('<p/>').text(ptext).appendTo('#display');
};
iMate.chat.app.doSendMessage = function() {
	var msg = $('#message').attr('value');
	this.wsSend("sendMessage","iMate.chat.app.messageRecorded",{"messageKey" : msg});
	$('<p/>').text(msg).appendTo('#display');
	$('#message').attr('value', '');// clean the inputbox
};
iMate.chat.app.messageRecorded = function(response) { /* @Handles(sendMessage) */
	console.log('Message successfully recorded at server' + JSON.stringify(response));
};