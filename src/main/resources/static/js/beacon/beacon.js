let index = {
		init: function() {
			$("#btn-create").on("click", ()=>{
				this.create();
			});
		},

		create: function() {
			let data = {
			            signals: [
                                     {
                                         uuid: "11",
                                         major: "11",
                                         minor: "11",
                                         rssi: 1
                                     },
                                     {
                                         uuid: "22",
                                         major: "22",
                                         minor: "22",
                                         rssi: 2
                                     },
                                     {
                                         uuid: "33",
                                         major: "33",
                                         minor: "33",
                                         rssi: 3
                                     },
                                     {
                                         uuid: "44",
                                         major: "44",
                                         minor: "44",
                                         rssi: 4
                                     }
                                 ]
			            };

        $.ajax({
        		type: 'post',
        		url: '/createBeacon',
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