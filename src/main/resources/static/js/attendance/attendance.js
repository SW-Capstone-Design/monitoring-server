let index = {
		init: function() {
			$("#btn-register").on("click", ()=>{
				this.register();
			});
            $("#btn-update").on("click", ()=>{
                this.update();
            });
            $("#btn-leave").on("click", ()=>{
                this.leave();
            });
            $("#btn-del").on("click", ()=>{
                this.del();
            });
		},

		register: function() {
		    var param1 = $("#userId").val();
            var param2 = $("#userIdentity").val();

            var today = new Date();
            var year = today.getFullYear();
            var month = ('0' + (today.getMonth() + 1)).slice(-2);
            var day = ('0' + today.getDate()).slice(-2);
            var dateString = year + '-' + month  + '-' + day;

            var hours = ('0' + today.getHours()).slice(-2);
            var minutes = ('0' + today.getMinutes()).slice(-2);
            var seconds = ('0' + today.getSeconds()).slice(-2);
            var timeString = hours + ':' + minutes  + ':' + seconds;

			let data = {
					enterTime: timeString,
					leaveTime: timeString,
					date: dateString
			};

			$.ajax({
				type: "POST",
				url: "/api/v1/attendance/"+param2,
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				dataType: "json"
			}).done(function(resp) {
			    if(resp.status == 400 | resp.status == 500){
                    alert("출근처리에 실패하였습니다.");
                }
                else{
                    alert("출근처리가 완료되었습니다.");
                    location.href = "/attendance/list/"+param1;
			    }
			}).fail(function(error) {
				alert(JSON.stringify(error));
			});
		},

		update: function() {
		        var param = $("#identity").val();

                let data = {
                        enterTime: $("#enterTime").val(),
                        leaveTime: $("#leaveTime").val(),
                        date: $("#date").val()
                };

                $.ajax({
                    type: "PUT",
                    url: "/api/v1/attendance/"+param,
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
            },

        leave: function() {
                var param1 = $("#userIdentity").val();
                var param2 = $("#userId").val();

                var today = new Date();
                var year = today.getFullYear();
                var month = ('0' + (today.getMonth() + 1)).slice(-2);
                var day = ('0' + today.getDate()).slice(-2);
                var dateString = year + '-' + month  + '-' + day;

                var hours = ('0' + today.getHours()).slice(-2);
                var minutes = ('0' + today.getMinutes()).slice(-2);
                var seconds = ('0' + today.getSeconds()).slice(-2);
                var timeString = hours + ':' + minutes  + ':' + seconds;

                let data = {
                        leaveTime: timeString,
                        date: dateString
                };

                $.ajax({
                    type: "PUT",
                    url: "/api/v1/attendance/leave/"+param1,
                    data: JSON.stringify(data),
                    contentType: "application/json; charset=utf-8",
                    dataType: "json"
                }).done(function(resp) {
                    if(resp.status == 400 | resp.status == 500){
                        alert("퇴근정보 갱신에 실패하였습니다.");
                    }
                    else{
                        alert("퇴근정보 갱신이 완료되었습니다.");
                        location.href = "/attendance/list/"+param2;
                    }
                }).fail(function(error) {
                    alert(JSON.stringify(error));
                });
            },

            del: function() {
                    if (confirm("삭제를 진행하시겠습니까?")) {
                    var param = $("#identity").val();
                    var date = $("#date").val();

                    let data = {
                            date: $("#date").val()
                    };

                    $.ajax({
                        type: "DELETE",
                        url: "/api/v1/attendance/"+param+"?date="+date,
                        data: JSON.stringify(data),
                        contentType: "application/json; charset=utf-8",
                        dataType: "json"
                    }).done(function(resp) {
                        if(resp.status == 400 || resp.status == 500){
                              alert("출결정보 삭제에 실패하였습니다.");
                          }
                          else{
                              alert("출결정보 삭제가 완료되었습니다.");
                              location.href = "/admin/attendance/list";
                        }
                    }).fail(function(error) {
                        alert(JSON.stringify(error));
                    });
                }
            }
}

index.init();