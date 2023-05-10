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
		},

		create: function() {
			let data = {
                        beaconName: $("#beaconName").val(),
                        uuid: $("#uuid").val(),
                        major: $("#major").val(),
                        minor: $("#minor").val(),
			};

        $.ajax({
                type: "POST",
                url: "/createBeacon",
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8"
            }).done(function(resp) {
                if(resp.status == 400 || resp.status == 500){
                      alert("비콘정보 등록에 실패하였습니다.");
                      location.href = "/admin/beacon/info";
                  }
                  else{
                      alert("비콘정보 등록이 완료되었습니다.");
                      location.href = "/admin/beacon/info";
                }
            }).fail(function(error) {
                alert(JSON.stringify(error));
                location.href = "/admin/beacon/info";
            });
	},

			update: function() {
                let data = {
                            beaconId: $("#beaconId").val(),
                            beaconName: $("#beaconName").val(),
                            uuid: $("#uuid").val(),
                            major: $("#major").val(),
                            minor: $("#minor").val(),
    			};

            $.ajax({
                    type: "PUT",
                    url: "/admin/beacon/info/update",
                    data: JSON.stringify(data),
                    contentType: "application/json; charset=utf-8"
                }).done(function(resp) {
                    if(resp.status == 400 || resp.status == 500){
                          alert("비콘정보 수정에 실패하였습니다.");
                          location.href = "/admin/beacon/info";
                      }
                      else{
                          alert("비콘정보 수정이 완료되었습니다.");
                          location.href = "/admin/beacon/info";
                    }
                }).fail(function(error) {
                    alert(JSON.stringify(error));
                    location.href = "/admin/beacon/info";
                });
    	},

            del: function() {
            let data = {
                        beaconId: $("#beaconId").val()
            };

            $.ajax({
                    type: "DELETE",
                    url: "/admin/beacon/info/delete",
                    data: JSON.stringify(data),
                    contentType: "application/json; charset=utf-8"
                }).done(function(resp) {
                    if(resp.status == 400 || resp.status == 500){
                          alert("비콘정보 삭제에 실패하였습니다.");
                          location.href = "/admin/beacon/info";
                      }
                      else{
                          alert("비콘정보 삭제가 완료되었습니다.");
                          location.href = "/admin/beacon/info";
                    }
                }).fail(function(error) {
                    alert(JSON.stringify(error));
                    location.href = "/admin/beacon/info";
                });
        }
}

index.init();