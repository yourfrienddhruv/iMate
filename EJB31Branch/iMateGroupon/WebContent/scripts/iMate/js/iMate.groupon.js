(function($) {
	iMate = function() {}; // define root namesapce
	iMate.groupon = function() {}; // define module class under root namesapce
	iMate.groupon.ws = null; // declaring ws here.
	iMate.groupon.init = function() {// app onetime configuration
		 // Autocomplete
	    var availableTags = ["ActionScript", "AppleScript", "Asp", "BASIC", "C", "C++", "Clojure", "COBOL", "ColdFusion", "Erlang", "Fortran", "Groovy", "Haskell", "Java", "JavaScript", "Lisp", "Perl", "PHP", "Python", "Ruby", "Scala", "Scheme"];

	    $("#search-fav").autocomplete({
	        source: availableTags
	    });

	    $('#validFrom').datepicker({ dateFormat: 'd M yy' });  //complying to java.text.DateFormat.MEDIUM
	    $('#expiring').datepicker({ dateFormat: 'd M yy' });//complying to java.text.DateFormat.MEDIUM
	    
		// setup post button
		$('#share-it-btn').click(this.share);

		$('#searchLatest-btn').click(this.searchLatest);

		// start ws
		iMate.groupon.ws = $.websocket("ws://localhost:8080/GrouponService", {
			close : iMate.groupon.wsClosing,
			events : {
				share : iMate.groupon.shareDone,
				searchLatest : iMate.groupon.searchLatestDone
			}
		});

	};
	
	iMate.groupon.wsNotSupported = function() {
		$('#oh-shit-dialog').dialog({
			autoOpen : true,
			width : 600,
			modal : true,
			show : "fadeIn",
			hide : "fadeOut",
			buttons : {
				"What the hell?" : function() {},
				"Atleast you should say sorry, Don't you think so?" : function() {}
			}
		}).show();
	};
	
	iMate.groupon.wsClosing = function() {
		console.log('Closing connection :TODO handle');
	};
	// ======= app specific methods =========//

	iMate.groupon.share = function() {
		var couponData= $('#share-it').toObject(); // converts form to JSON
		console.log(couponData);
		iMate.groupon.ws.send("share", {
			"data":couponData
		});
	};
	iMate.groupon.shareDone = function(response) { /* @Handles(share) */
		console.log(JSON.stringify(response));
		/*var ptext;
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
		$('<p/>').text(ptext).appendTo('#display');*/
	};

	iMate.groupon.searchLatest = function() {
		var what= $('#searchLatest-txt').val(); 
		console.log(what);
		iMate.groupon.ws.send("searchLatest", {
			"messageKey":what
		});
	};
	iMate.groupon.searchLatestDone = function(response) {
		console.log(JSON.stringify(response));
	};
	
})(jQuery);
