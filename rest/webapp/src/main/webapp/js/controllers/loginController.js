Assassin.LoginController = Ember.Controller.extend(AssassinSubmitter, {
    needs: ["application"],

    canSubmit: function() {
        return notEmpty(this.get("emailAddress")) && notEmpty(this.get("password"));
    }.property("emailAddress", "password"),

    cantSubmit: function() {
        return !this.get("canSubmit");
    }.property("canSubmit"),

    login: function(username, password) {
        this._submit({
            type: "GET",
            url: "rest/users/email/" + username,
            returnType: User,
            nativeProps: {
                username: username,
                password: password
            },

            error: function(response) {
                if(response.status == 403) {
                    alert("Invalid username or password.");
                } else {
                    alert("Ack - login failed with unexpected status: " + response.status);
                }
            }.bind(this),

            success: function(user) {
                Assassin.set("loggedIn", true);
                Assassin.set("username", this.get("emailAddress"));
                Assassin.set("password", this.get("password"));
                Assassin.set("user", user);
                var appController = this.get("controllers.application");
                appController.removeNavItem("enlist");
            }.bind(this)
        });
    },

    actions: {
        login: function() {
            this.login(this.get("emailAddress"), this.get("password"));
        }
    }
});