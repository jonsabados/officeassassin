var Errors = require("models/Errors");

module.exports = Ember.Mixin.create({
    submitting: false,
    authHeader: function(username, password) {
        return "BasicCustom " + btoa(username + ":" + password);
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
            }
        };

        if(submission.sudoCreds != undefined) {
            ajaxData.headers.Authorization = this.authHeader(submission.sudoCreds.username, submission.sudoCreds.password);
        } else if(Assassin.get("loggedIn")) {
            ajaxData.headers.Authorization = this.authHeader(Assassin.get("username"), Assassin.get("password"));
        }

        if(submission.type == "GET") {
            ajaxData.dataType = "json";
        }

        this.set("submitting", true);
        return $.ajax(ajaxData).always(function () {this.set("submitting", false);}.bind(this));
    }

});

function trimToUndefinedReplacer(key, value) {
    if(typeof(value) == "string" && value == "") {
        return undefined;
    } else {
        return value;
    }
}