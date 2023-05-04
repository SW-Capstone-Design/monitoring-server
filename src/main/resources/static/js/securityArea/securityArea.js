let index = {
		init: function() {
			$("#btn-create").on("click", ()=>{
				this.create();
			});
            $("#btn-update").on("click", ()=>{
                this.update();
            });
		},

		create: function() {
		    var param = $("#identity").val();
		    var name = $("#name").val();
		    var description = $("#description").val();
		    var latitude = $("#latitude").val();
		    var longitude = $("#longitude").val();

            location.href="/api/v1/security_area/"+param;

			let data = {
                           name: name,
                           description: description,
                           location: {
                                           latitude: latitude,
                                           longitude: longitude
                                     }
                       };

       $.ajax({
               type: "POST",
               url: "/api/v1/security_area/"+param,
               data: JSON.stringify(data),
               contentType: "application/json; charset=utf-8",
               dataType: "json"
           }).done(function(resp) {
               if(resp.status == 400 | resp.status == 500){
                   alert("보안구역 등록에 실패하였습니다.");
               }
               else{
                   alert("보안구역 등록이 완료되었습니다.");
                   location.href = "/admin/index";
               }
           }).fail(function(error) {
               alert(JSON.stringify(error));
           });
	},

	   update: function() {
    		    var param = $("#id").val();
    		    var name = $("#name").val();
    		    var description = $("#description").val();
    		    var latitude = $("#latitude").val();
    		    var longitude = $("#longitude").val();

                location.href="/api/v1/security_area/"+param;

    			let data = {
                               name: name,
                               description: description,
                               location: {
                                               latitude: latitude,
                                               longitude: longitude
                                         }
                           };

           $.ajax({
                   type: "PUT",
                   url: "/api/v1/security_area/"+param,
                   data: JSON.stringify(data),
                   contentType: "application/json; charset=utf-8",
                   dataType: "json"
               }).done(function(resp) {
                   if(resp.status == 400 | resp.status == 500){
                       alert("보안구역 수정에 실패하였습니다.");
                   }
                   else{
                       alert("보안구역 수정에 완료되었습니다.");
                       location.href = "/admin/area/info";
                   }
               }).fail(function(error) {
                   alert(JSON.stringify(error));
               });
    	}
}

index.init();