(function($) {
	iMate = function() {}; // define root namesapce
	iMate.groupon = function() {}; // define module class under root namesapce
	iMate.groupon.ws = null; // declaring ws here.
	iMate.groupon.init = function() {// app onetime configuration
		// Autocomplete
		var availableTags = [ "ActionScript", "AppleScript", "Asp", "BASIC", "C", "C++", "Clojure", "COBOL", "ColdFusion", "Erlang",
				"Fortran", "Groovy", "Haskell", "Java", "JavaScript", "Lisp", "Perl", "PHP", "Python", "Ruby", "Scala", "Scheme" ];

		$("#searchLatest").autocomplete({
			source : availableTags
		});
		$('#searchLatest-btn').click(this.searchLatest);

		// Filament datepicker
		$('#shareIt-validFrom, #shareIt-expiring').daterangepicker({
			dateFormat : 'd M yy',
			presetRanges : [ {
				text : 'Valid from Today to One Month',
				dateStart : 'Today',
				dateEnd : 'Today+1month'
			}, {
				text : 'Valid from Yesterday to One Month',
				dateStart : 'Yesterday',
				dateEnd : 'Yesterday+1month'
			} ],
			presets : {
				specificDate : 'Valid for single Day',
				allDatesBefore : 'Expiring on this date! Hurry!',
				allDatesAfter : 'Valid from this date to Forever!',
				dateRange : 'Specific Date Range'
			},
			rangeStartTitle : 'Valid from',
			rangeEndTitle : 'Expiring on',
		});

		// setup post button
		$('#shareIt-btn').click(this.share);

		/*
		iMate.groupon.ws = $.websocket("ws://localhost:9090/Coupon", {
			// iMate.groupon.ws = $.websocket("ws://imategroupon-yourfrienddhruv.rhcloud.com:4712/GrouponService", {
			events : {
				share : iMate.groupon.shareDone,
				searchLatest : iMate.groupon.searchLatestDone
			}
		});
		*/
		iMate.groupon.ws = $.restservice("http://imategroupon-yourfrienddhruv.rhcloud.com/iMateGroupon/s/Coupon", {
		//iMate.groupon.ws = $.restservice("http://localhost:8080/iMateGroupon/s/Coupon", {
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

	// ======= app specific methods =========//

	iMate.groupon.share = function() {
		var couponData = $('#share-it').toObject(); // converts form to JSON
		console.log(couponData);
		iMate.groupon.ws.send("share", {
			"data" : couponData
		});
	};
	iMate.groupon.shareDone = function(response) { /* @Handles(share) */
		console.log(JSON.stringify(response));
		/*
		 * var ptext; if (response) { if (response.data.success) { ptext = 'Welcome ' + response.data.data.firstName + '!';
		 * $('#login-form').dialog('close'); $('#message-form').fadeIn('slow'); } else { ptext = response.data.messageKey; } } else { ptext =
		 * "Invalid response from server, please retry!"; } $('<p/>').text(ptext).appendTo('#display');
		 */
	};

	iMate.groupon.searchLatest = function() {
		var what = $('#searchLatest-txt').val();
		console.log(what);
		iMate.groupon.ws.send("searchLatest", {
			"data" : {brand:what}
		});
		$('#searchResults').slideUp('slow');
		
	};
	iMate.groupon.searchLatestDone = function(response) {
		console.log(JSON.stringify(response.data));
		if (response.data.dataSet) {
			$('#searchCouponRows-template').tmpl(response.data.dataSet).appendTo('#searchResults tbody');
			$('#searchResults').slideDown('slow');
			//.animate( { backgroundColor: "black" }, 1000);
			/*.animate( { height: "hide" }, 2000 )
			.delay( 800 )
			.animate( { height: "show" }, 2000 );*/
		} else {
			console.log(JSON.stringify(response));
		}
		console.log('Done');
	};

})(jQuery);
