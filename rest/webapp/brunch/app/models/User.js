var Serializable = require("models/Serializable");

module.exports = Ember.Object.extend(Serializable, {
    toSerialize: ["emailAddress", "handle", "fullName", "password"],
    requiredFields: Ember.A([
        "emailAddress",
        "handle",
        "password"
    ])
});