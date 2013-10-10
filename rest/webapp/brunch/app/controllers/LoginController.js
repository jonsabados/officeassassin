var Assassin = require("config/App"),
  AssassinSubmitter = require("mixins/AssassinSubmitter"),
  User = require("models/User");

module.exports = Assassin.LoginController = Ember.Controller.extend(AssassinSubmitter, {
    canSubmit: Ember.computed.and("emailAddress", "password"),

    cantSubmit: Ember.computed.not("canSubmit"),

    login: function(username, password) {
        this._submit({
            type: "GET",
            url: "rest/users/email/" + username,
            sudoCreds: {
                username: username,
                password: password
            },

            error: function(response) {
                if(response.status == 401) {
                    alert("Invalid username or password.");
                } else {
                    alert("Ack - login failed with unexpected status: " + response.status);
                }
            }.bind(this)
        }).done(function(data) {
            var user = User.create().deserialize(data)
            Assassin.set("loggedIn", true);
            Assassin.set("username", this.get("emailAddress"));
            Assassin.set("password", this.get("password"));
            Assassin.set("user", user);
        }.bind(this));
    },

    actions: {
        login: function() {
            this.login(this.get("emailAddress"), this.get("password"));
        },

        logout: function() {
            Assassin.set("loggedIn", false);
            Assassin.set("username", undefined);
            Assassin.set("password", undefined);
            Assassin.set("user", undefined);
        }
    }
});