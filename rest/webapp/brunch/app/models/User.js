var Assassin = require("config/App"),
    AssassinSubmitter = require("mixins/AssassinSubmitter"),
    Role = require("models/Role"),
    Serializable = require("models/Serializable");

module.exports = Ember.Object.extend(Serializable, AssassinSubmitter, {
    toSerialize: ["emailAddress", "handle", "fullName", "password"],
    requiredFields: Ember.A([
        "emailAddress",
        "handle",
        "password"
    ]),
    roles: function() {
        return this._submit({
            type: "GET",
            url: "rest/users/id/" + Assassin.get("user.id"),
        }).done(function(data) {
            return data.map(function(roleData) {
               return Role.create(roleData);
            });
        });
    }.property()
});