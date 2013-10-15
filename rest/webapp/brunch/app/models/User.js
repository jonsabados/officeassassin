var Assassin = require("config/App"),
  AssassinSubmitter = require("mixins/AssassinSubmitter"),
  Role = require("models/Role"),
  LimitedPostData = require("models/LimitedPostData");

module.exports = Ember.Object.extend(LimitedPostData, AssassinSubmitter, Ember.Validations.Mixin, {
  emailAddress: null,
  handle: null,
  fullName: null,
  password: null,
  termsAccepted: false,
  passwordConfirmation: null,

  toSerialize: ["emailAddress", "handle", "fullName", "password"],

  displayName: function() {
    return this.get("fullName") || this.get("emailAddress");
  }.property("emailAddress", "fullName"),

  roles: function() {
    if(!this.get("_roles")) {
      this._fetchRoles();
    }
    return this.get("_roles");
  }.property("_roles"),

  _fetchRoles: function() {
    return this._submit({
      type: "GET",
      url: "rest/users/id/" + Assassin.get("user.id") + "/roles?pageLength=999",
    }).done(function(results) {
      this.set("_roles", results.data.map(function(roleData) {
         return Role.create(roleData);
      }));
    }.bind(this));
  },

  validations: {
    emailAddress: {
      presence: true
    },
    handle: {
      presence: true
    },
    password: {
      presence: true,
      confirmation: true
    },
    termsAccepted: {
      acceptance: true
    }
  }

});