let index = {
		init: function() {
			$("#btn-register").on("click", ()=>{
				this.register();
			});
		},

		register: function() {
            var param = $("#userIdentity").val();

            location.href = "/api/v1/attendance/"+param;

			let data = {
					enterTime: $("#enterTime").val(),
					leaveTime: $("#leaveTime").val(),
					date: $("#date").val()
			};

			$.ajax({
				type: "POST",
				url: "/api/v1/attendance/"+param,
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				dataType: "json"
			}).done(function(resp) {
			    if(resp.status == 400 | resp.status == 500){
                    alert("출근처리에 실패하였습니다.");
                }
                else{
                    alert("출근처리가 완료되었습니다.");
                    location.href = "/";
			    }
			}).fail(function(error) {
				alert(JSON.stringify(error));
			});
		}
}

index.init();