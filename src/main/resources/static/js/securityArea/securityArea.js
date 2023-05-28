let index = {
		init: function() {
			$("#btn-create").on("click", ()=>{
				this.create();
			});
            $("#btn-update").on("click", ()=>{
                this.update();
            });
            $("#btn-del").on("click", ()=>{
                this.del();
            });
            $("#btn-back").on("click", ()=>{
                this.back();
            });
		},

		create: function() {
		    var param = $("#identity").val();

			let data = {
                           name: $("#name").val(),
                           description: $("#description").val(),
                           location: {
                                           x: $("#x").val(),
                                           y: $("#y").val()
                                     }
                       };

       $.ajax({
               type: "POST",
               url: "/api/v1/security_area/"+param,
               data: JSON.stringify(data),
               contentType: "application/json; charset=utf-8"
           }).done(function(resp) {
               if(resp.status == 400 | resp.status == 500){
                   alert("보안구역 등록에 실패하였습니다.");
               }
               else{
                   alert("보안구역 등록이 완료되었습니다.");
                   location.href = "/admin/area/info";
               }
           }).fail(function(error) {
               alert(JSON.stringify(error));
               location.href = "/admin/area/info";
           });
	},

	   update: function() {
    		    var param1 = $("#identity").val();
    		    var param2 = $("#securityAreaId").val();

    		    var name = $("#name").val();
    		    var description = $("#description").val();
    		    var x = $("#x").val();
    		    var y = $("#y").val();

    			let data = {
                               name: name,
                               description: description,
                               location: {
                                               x: x,
                                               y: y
                                         }
                           };

           $.ajax({
                   type: "PUT",
                   url: "/api/v1/security_area/"+param1+"/"+param2,
                   data: JSON.stringify(data),
                   contentType: "application/json; charset=utf-8"
               }).done(function(resp) {
                   if(resp.status == 400 | resp.status == 500){
                       alert("보안구역 수정에 실패하였습니다.");
                   }
                   else{
                       alert("보안구역 수정이 완료되었습니다.");
                       location.href = "/admin/area/info";
                   }
               }).fail(function(error) {
                       alert(JSON.stringify(error));
                       location.href = "/admin/area/info";
               });
    	},

    	del: function() {
                if (confirm("삭제를 진행하시겠습니까?")) {
    		    var param1 = $("#identity").val();
    		    var param2 = $("#securityAreaId").val();

                $.ajax({
                    type: "DELETE",
                    url: "/api/v1/security_area/"+param1+"/"+param2,
                    contentType: "application/json; charset=utf-8"
                }).done(function(resp) {
                    if(resp.status == 400 || resp.status == 500){
                          alert("보안구역 삭제에 실패하였습니다.");
                          location.href = "/admin/area/info";
                      }
                      else{
                          alert("보안구역 삭제가 완료되었습니다.");
                          location.href = "/admin/area/info";
                    }
                }).fail(function(error) {
                    alert(JSON.stringify(error));
                    location.href = "/admin/area/info";
                });
            }
        },

            back: function() {
                window.history.back();
            }
}

index.init();