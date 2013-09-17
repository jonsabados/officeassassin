var AssassinSubmitter = Ember.Mixin.create({
    submitting: false,
    authHeader: function(username, password) {
        return "Basic " + btoa(username + ":" + password);
    },

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
                    submission.badRequest(Errors.create().deserialize(JSON.parse(response.responseText)));
                } else {
                    submission.error(response);
                }
                this.set("submitting", false);
            }.bind(this)
        };
        if(Assassin.get("loggedIn")) {
            ajaxData.headers.Authorization = this.authHeader(Assassin.get("username"), Assassin.get("password"));
        }
        if(submission.returnType != undefined)  {
            ajaxData.dataType = "json";
            ajaxData.success = function(json) {
                submission.success(submission.returnType.create().deserialize(json));
                this.set("submitting", false);
            }.bind(this);
        } else {
            ajaxData.success = function(responseData, textStatus, jqXHR) {
                submission.success(responseData, textStatus, jqXHR);
                this.set("submitting", false);
            }.bind(this);
        }

        if(submission.nativeProps != undefined) {
            for(key in submission.nativeProps) {
                ajaxData[key] = submission.nativeProps[key];
            }
        }

        this.set("submitting", true);
        $.ajax(ajaxData);
    }

})