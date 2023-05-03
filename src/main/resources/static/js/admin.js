let index = {
		init: function() {
			$("#btn-update").on("click", ()=>{
            	this.update();
            });
            $("#btn-del").on("click", ()=>{
                this.del();
            });
		},

		update: function() {

        			let data = {
        					userId: $("#userId").val(),
        					identity: $("#identity").val(),
        					password: $("#password").val(),
        					name: $("#name").val(),
        					department: $("#department").val(),
        					telephone: $("#telephone").val(),
        					roleType: $("#roleType").val()
        			};

        			$.ajax({
        				type: "PUT",
        				url: "/admin",
        				data: JSON.stringify(data),
        				contentType: "application/json; charset=utf-8",
        				dataType: "json"
        			}).done(function(resp) {
                        if(resp.status == 400 || resp.status == 500){
                              alert("회원수정에 실패하였습니다.");

                              if(resp.data.hasOwnProperty('valid_identity')){
                                $('#valid_identity').text(resp.data.valid_identity);
                                $('#valid_identity').css('color', 'red');
                            }
                            else $('#valid_identity').text('');

                            if(resp.data.hasOwnProperty('valid_password')){
                                $('#valid_password').text(resp.data.valid_password);
                                $('#valid_password').css('color', 'red');
                            }
                            else $('#valid_password').text('');

                            if(resp.data.hasOwnProperty('valid_name')){
                                $('#valid_name').text(resp.data.valid_name);
                                $('#valid_name').css('color', 'red');
                            }
                            else $('#valid_name').text('');

                            if(resp.data.hasOwnProperty('valid_department')){
                                $('#valid_department').text(resp.data.valid_department);
                                $('#valid_department').css('color', 'red');
                              }
                              else $('#valid_department').text('');

                            if(resp.data.hasOwnProperty('valid_telephone')){
                                $('#valid_telephone').text(resp.data.valid_telephone);
                                $('#valid_telephone').css('color', 'red');
                              }
                              else $('#valid_telephone').text('');
                          }
                          else{
                              alert("회원수정이 완료되었습니다.");
                              location.href = "/admin/info";
                        }
                    }).fail(function(error) {
        				alert(JSON.stringify(error));
        			});
        		},

        		del: function() {
                            let data = {
                                    userId: $("#userId").val()
                            };

                            $.ajax({
                                type: "DELETE",
                                url: "/admin/info/delete",
                                data: JSON.stringify(data),
                                contentType: "application/json; charset=utf-8",
                                dataType: "json"
                            }).done(function(resp) {
                                if(resp.status == 400 || resp.status == 500){
                                      alert("회원삭제에 실패하였습니다.");
                                  }
                                  else{
                                      alert("회원삭제가 완료되었습니다.");
                                      location.href = "/admin/info";
                                }
                            }).fail(function(error) {
                                alert("회원삭제가 완료되었습니다.");
                                location.href = "/admin/info";
                            });
                        }
}

index.init();