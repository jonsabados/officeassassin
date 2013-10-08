var Assassin = require("config/App"),
  AssassinSubmitter = require("controllers/AssassinSubmitter"),
  Errors = require("models/Errors");

module.exports = Assassin.EnlistController = Ember.Controller.extend(AssassinSubmitter, {
    needs: ["login"],
    termsAccepted: false,
    passwordConfirm: "",
    errors: Errors.create({}),

    hasFailures: function() {
        return this.get("errors").generalFailures.length > 0 || this.get("errors").fieldFailures.length > 0;
    }.property("errors"),

    // TODO - there has got to be a better way to do this other than a slew of hasFailureFEILD functions
    hasFailure: function(field) {
        return this.get("errors").fieldFailures.any(function(error) {
            return error.field == field;
        });
    },

    hasFailureEmailAddress: function() {return this.hasFailure("emailAddress")}.property("errors"),
    hasFailureHandle: function() {return this.hasFailure("handle")}.property("errors"),
    hasFailureFullName: function() {return this.hasFailure("fullName")}.property("errors"),
    hasFailurePassword: function() {return this.hasFailure("password")}.property("errors"),
    hasFailurePasswordConfirm: function() {return this.hasFailure("passwordConfirm")}.property("errors"),

    requiredFieldsSet: function() {
        var model = this.get("model");
        return model.get("requiredFields").every(function(property) {
            return !!model.get(property);
        });
    }.property("model.emailAddress", "model.handle", "model.password", "passwordConfirm"),

    passwordMatch: function() {
        return this.get("model").get("password") === this.get("passwordConfirm");
    }.property("model.password", "passwordConfirm"),

    canSubmit: function() {
        return this.get("requiredFieldsSet") && this.get("termsAccepted") && this.get("passwordMatch") && !this.get("submitting");
    }.property("requiredFieldsSet", "termsAccepted", "passwordMatch", "submitting"),

    cantSubmit: function() {
        return !this.get("canSubmit");
    }.property("canSubmit"),

    actions: {
        save: function() {
            this._submit({
                url: "rest/public/enlistment",
                type: "POST",
                data: this.get("model"),

                success: function() {
                    alert("Brand new user created!");
                    var login = this.get("controllers.login");
                    login.login(this.get("model.emailAddress"), this.get("model.password"));
                }.bind(this),

                badRequest: function(errors) {
                    this.set("errors", errors);
                }.bind(this),

                error: function(failure, status) {
                    alert("Things went wrong, got back status " + failure.status + " from the server");
                }
            })
        },

        focusField: function(field) {
            $("#in_" + field).focus();
        }
    }

})