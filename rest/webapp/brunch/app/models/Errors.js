var Serializable = require("models/Serializable");

module.exports = Ember.Object.extend(Serializable, {
    toSerialize: ["generalFailures", "fieldFailures"],
    generalFailures: [],
    fieldFailures: []
});