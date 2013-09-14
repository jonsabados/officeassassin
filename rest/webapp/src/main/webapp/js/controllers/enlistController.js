Assassin.EnlistController = Ember.Controller.extend({
    termsAccepted: false,
    passwordConfirm: "",
    saving: false,

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

    save: function() {
        this.set("saving", true);
        $.ajax({
            type: "POST",
            url: "rest/public/enlistment",
            processData: false,
            contentType: 'application/json',
            data: JSON.stringify(this.get("model").serialize()),
            success: function() {
                alert("Brand new user");
            },
            error: function(failure, status) {
                alert("things went wrong: " + failure.responseText + " got back:" + status);
            }
        })
    },
})