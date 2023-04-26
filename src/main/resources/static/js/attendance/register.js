let index = {
		init: function() {
			$("#btn-register").on("click", ()=>{
				this.register();
			});
		},

		create: function() {
			let data = {
					userId: $("#userId").val(),
			}

			$.ajax({
				type: "POST",
				url: "/attendance/registerProc",
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				dataType: "json"
			}).done(function(resp) {
			    if(resp.status == 400){
                    alert("출근처리에 실패하였습니다.");
                }
                else{
                    alert("출근처리가 완료되었습니다.");
                    location.href = "/";
			    }
			}).fail(function(error) {
				alert(JSON.stringify(error));
			});
		},