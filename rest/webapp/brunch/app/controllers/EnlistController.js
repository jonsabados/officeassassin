var Assassin = require("config/App"),
  AssassinSubmitter = require("mixins/AssassinSubmitter"),
  Errors = require("models/Errors");

module.exports = Assassin.EnlistController = Ember.ObjectController.extend(AssassinSubmitter, {
  needs: ["login"],
  creationErrors: Errors.create({}),

  hasCreateFailures: function () {
    return this.get("creationErrors").generalFailures.length > 0 || this.get("creationErrors").fieldFailures.length > 0;
  }.property("creationErrors"),

  hasFailure: function (field) {
    return this.get("creationErrors").fieldFailures.any(function (error) {
      return error.field == field;
    });
  },

  hasFailureEmailAddress: function () { return this.hasFailure("emailAddress"); }.property("creationErrors"),
  hasFailureHandle: function () { return this.hasFailure("handle"); }.property("creationErrors"),
  hasFailureFullName: function () { return this.hasFailure("fullName"); }.property("creationErrors"),
  hasFailurePassword: function () { return this.hasFailure("password"); }.property("creationErrors"),
  hasFailurePasswordConfirm: function () { return this.hasFailure("passwordConfirm"); }.property("creationErrors"),

  cantSubmit: Ember.computed.not("isValid"),

  actions: {
    save: function () {
      this._submit({
        url: "rest/public/enlistment",
        type: "POST",
        data: this.get("model").serialize(),

        badRequest: function (creationErrors) {
          this.set("creationErrors", creationErrors);
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