var Assassin = require("config/App"),
    AssassinSubmitter = require("mixins/AssassinSubmitter"),
    Role = require("models/Role"),
    Serializable = require("models/Serializable");

module.exports = Ember.Object.extend(Serializable, AssassinSubmitter, {
    toSerialize: ["emailAddress", "handle", "fullName", "password"],

    requiredFields: [
        "emailAddress",
        "handle",
        "password"
    ],

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
            url: "rest/users/id/" + Assassin.get("user.id") + "/roles",
        }).done(function(results) {
            this.set("_roles", results.data.map(function(roleData) {
               return Role.create(roleData);
            }));
        }.bind(this));
    }
});