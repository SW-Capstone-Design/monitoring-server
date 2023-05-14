let index = {
		init: function() {

			$("#btn-update").on("click", ()=>{
            	this.update();
            });
            $("#btn-save").on("click", ()=>{
                this.save();
            });
		},

		update: function() {
                    var password = $("#password").val();
                    var confirm = $("#confirm").val();

                    if (password != confirm) {
                    alert("패스워드가 일치하지 않습니다.");
                    return false;
                    }

        			let data = {
        					userId: $("#userId").val(),
        					identity: $("#identity").val(),
        					password: $("#password").val(),
        					name: $("#name").val(),
        					department: $("#department").val(),
        					telephone: $("#telephone").val()
        			};

        			$.ajax({
        				type: "PUT",
        				url: "/user",
        				data: JSON.stringify(data),
        				contentType: "application/json; charset=utf-8",
        				dataType: "json"
        			}).done(function(resp) {
                        if(resp.status == 400){
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
                              location.href = "/";
                        }
                    }).fail(function(error) {
        				alert(JSON.stringify(error));
        			});
        		},

        		save: function() {
                        var password = $("#password").val();
                        var confirm = $("#confirm").val();

                        if (password != confirm) {
                        alert("패스워드가 일치하지 않습니다.");
                        return false;
                        }

                        let data = {
                                identity: $("#identity").val(),
                                password: $("#password").val(),
                                name: $("#name").val(),
                                telephone: $("#telephone").val(),
                                department: $("#department").val()
                        };

                        $.ajax({
                            type: "POST",
                            url: "/auth/joinProc",
                            data: JSON.stringify(data),
                            contentType: "application/json; charset=utf-8",
                            dataType: "json"
                        }).done(function(resp) {
                            if(resp.status == 400 || resp.status == 500){
                                alert("회원가입에 실패하였습니다.");

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
                                alert("회원가입이 완료되었습니다.");
                                location.href = "/";
                            }
                        }).fail(function(error) {
                            alert(JSON.stringify(error));
                        });
                    }

}

index.init();