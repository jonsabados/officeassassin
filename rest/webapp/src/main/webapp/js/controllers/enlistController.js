Assassin.EnlistController = Ember.Controller.extend({
    termsAccepted: false,
    passwordConfirm: "",

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
        return this.get("requiredFieldsSet") && this.get("termsAccepted") && this.get("passwordMatch");
    }.property("requiredFieldsSet", "termsAccepted", "passwordMatch")
})