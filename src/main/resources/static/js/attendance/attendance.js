let index = {
		init: function() {
			$("#btn-register").on("click", ()=>{
				this.register();
			});
            $("#btn-update").on("click", ()=>{
                this.update();
            });
		},

		register: function() {
            var param = $("#userIdentity").val();

            location.href = "/attendance/"+param;

			let data = {
					enterTime: $("#enterTime").val(),
					leaveTime: $("#leaveTime").val(),
					date: $("#date").val()
			};

			$.ajax({
				type: "POST",
				url: "/attendance/"+param,
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
		},

		update: function() {
		        param1 = $("#identity").val();

                let data = {
                        enterTime: $("#enterTime").val(),
                        leaveTime: $("#leaveTime").val(),
                        date: $("#date").val()
                };

                $.ajax({
                    type: "PUT",
                    url: "/attendance/"+param1,
                    data: JSON.stringify(data),
                    contentType: "application/json; charset=utf-8",
                    dataType: "json"
                }).done(function(resp) {
                    if(resp.status == 400 | resp.status == 500){
                        alert("출결정보 수정에 실패하였습니다.");
                    }
                    else{
                        alert("출결정보 수정이 완료되었습니다.");
                        location.href = "/admin/attendance/list";
                    }
                }).fail(function(error) {
                    alert(JSON.stringify(error));
                });
            }
}

index.init();