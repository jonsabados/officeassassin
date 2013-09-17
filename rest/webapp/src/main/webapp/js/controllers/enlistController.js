Assassin.EnlistController = Ember.Controller.extend(AssassinSubmitter, {
    needs: ["login"],
    termsAccepted: false,
    passwordConfirm: "",
    hasFieldFailures: function() {
        return this.get("errors").fieldFailures.length > 0;
    }.property("errors"),
    hasGeneralFailures: function() {
        return this.get("errors").generalFailures.length > 0;
    }.property("errors"),
    errors: Errors.create({}),

    requiredFieldsSet: function() {
        var model = this.get("model");
        return model.get("requiredFields").every(function(property) {
            return notEmpty(model.get(property));
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
        }
    }

})