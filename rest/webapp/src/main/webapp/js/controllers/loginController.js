Assassin.LoginController = Ember.Controller.extend({
    needs: ["application"],




    canSubmit: function() {
        return notEmpty(this.get("emailAddress")) && notEmpty(this.get("password"));
    }.property("emailAddress", "password"),

    cantSubmit: function() {
        return !this.get("canSubmit");
    }.property("canSubmit"),
});