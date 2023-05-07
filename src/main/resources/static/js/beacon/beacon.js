let index = {
		init: function() {
			$("#btn-transfer").on("click", ()=>{
				this.transfer();
			});
		},

		transfer: function() {
			let data = {
			            signals: [
                                     {
                                         uuid: "55",
                                         major: "14314134",
                                         minor: "441241221",
                                         rssi: 1
                                     },
                                     {
                                         uuid: "66",
                                         major: "325325235",
                                         minor: "235253254",
                                         rssi: 4
                                     },
                                     {
                                         uuid: "33",
                                         major: "63525326",
                                         minor: "455325233",
                                         rssi: 6
                                     },
                                     {
                                         uuid: "44",
                                         major: "2314",
                                         minor: "12341243",
                                         rssi: 3
                                     }
                                 ]
			            };

        $.ajax({
        		type: 'post',
        		url: '/receiveBeacon',
        		headers:{
        			"Content-Type" : "application/json",
        			"X-HTTP-Method-Override" : "POST"
        		},
        		dataType: 'text',
        		data : JSON.stringify(data),
        		success: function(result){
        			if(result=='success'){
        				alert('성공')
        			}
        		}
        	});
	}
}

index.init();