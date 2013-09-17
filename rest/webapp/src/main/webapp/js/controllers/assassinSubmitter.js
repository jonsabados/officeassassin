var AssassinSubmitter = Ember.Mixin.create({
    submitting: false,

    _submit: function(submission) {
        var data = submission.data == undefined ? undefined : JSON.stringify(submission.data, trimToUndefinedReplacer);
        var ajaxData = {
            processData: false,
            contentType: "application/json",
            headers: {
                Accept: "application/json"
            },
            data: data,
            type: submission.type,
            dataType: submission.dataType,
            url: submission.url,

            error: function(response) {
                if(response.status == 400) {
                    submission.badRequest(JSON.parse(response.responseText));
                } else {
                    submission.error(response);
                }
                this.set("submitting", false);
            }.bind(this)
        };
        if(Assassin.get("loggedIn")) {
            ajaxData.username = Assassin.get("username");
            ajaxData.password = Assassin.get("password");
        }
        if(data.returnType != undefined)  {
            ajaxData.dataType = "json";
            ajaxData.success = function(json) {
                submission.success(ajaxData.dataType.create().deserialize(json));
                this.set("submitting", false);
            }.bind(this);
        } else {
            ajaxData.success = function(responseData, textStatus, jqXHR) {
                submission.success(responseData, textStatus, jqXHR);
                this.set("submitting", false);
            }.bind(this);
        };

        this.set("submitting", true);
        $.ajax(ajaxData);
    }

})