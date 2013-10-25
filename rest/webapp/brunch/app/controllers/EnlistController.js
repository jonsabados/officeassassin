var Assassin = require("config/App"),
  AssassinSubmitter = require("mixins/AssassinSubmitter"),
  Errors = require("models/Errors");

module.exports = Assassin.EnlistController = Ember.ObjectController.extend(AssassinSubmitter, {
  needs: ["login"],

  hasGeneralFailures: function () {
    return (this.get("creationErrors.generalFailures") || []).length > 0;
  }.property("creationErrors"),

  cantSubmit: Ember.computed.not("isValid"),

  actions: {
    save: function () {
      this._submit({
        url: "rest/public/enlistment",
        type: "POST",
        data: this.get("model").serialize(),

        badRequest: function (creationErrors) {
          this.set("creationErrors", creationErrors);
          if (!this.get("rejectedValues")) {
            this.set("rejectedValues", {});
          }

          $.each(creationErrors.fieldFailures, function (index, failure) {
            var field = failure.field;
            if (!this.get("rejectedValues." + field)) {
              this.set("rejectedValues." + field, []);
            }
            this.get("rejectedValues." + field).pushObject({
              value: this.get(field),
              message: failure.message
            });
          }.bind(this));
        }.bind(this),

        error: function (failure, status) {
          alert("Things went wrong, got back status " + failure.status + " from the server");
        }
      }).done(function () {
        this.get("controllers.login").login(this.get("model.emailAddress"), this.get("model.password"));
      }.bind(this));
    },

    focusField: function (field) {
      $("#in_" + field).focus();
    }
  }

});