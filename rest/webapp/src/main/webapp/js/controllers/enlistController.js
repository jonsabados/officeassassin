Assassin.EnlistController = Ember.Controller.extend({
    needs: ["application"],
    termsAccepted: false,
    passwordConfirm: "",
    saving: false,
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
            var val = model.get(property);
            return val != undefined && val != "";
        });
    }.property("model.emailAddress", "model.handle", "model.password", "passwordConfirm"),

    passwordMatch: function() {
        return this.get("model").get("password") === this.get("passwordConfirm");
    }.property("model.password", "passwordConfirm"),

    canSubmit: function() {
        return this.get("requiredFieldsSet") && this.get("termsAccepted") && this.get("passwordMatch") && !this.get("saving");
    }.property("requiredFieldsSet", "termsAccepted", "passwordMatch", "saving"),

    cantSubmit: function() {
        return !this.get("canSubmit");
    }.property("canSubmit"),

    save: function() {
        var me = this;
        me.set("saving", true);
        $.ajax({
            type: "POST",
            url: "rest/public/enlistment",
            processData: false,
            contentType: "application/json",
            headers: {
                Accept: "application/json"
            },

            data: JSON.stringify(this.get("model").serialize()),
            success: function() {
                var appController = me.get("controllers.application");
                alert("Brand new user - " + appController);
                appController.removeNavItem("enlist");
            },
            error: function(failure, status) {
                if(failure.status == 400) {
                    me.set("errors", JSON.parse(failure.responseText));
                } else {
                    alert("Things went wrong, got back status " + failure.status + " from the server");
                }
                me.set("saving", false);
            }
        })
    },
})