var Errors = Ember.Object.extend(Serializable, {
    toSerialize: ["generalFailures", "fieldFailures"],
    generalFailures: [],
    fieldFailures: []
});