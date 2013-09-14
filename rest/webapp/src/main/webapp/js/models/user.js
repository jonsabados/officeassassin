var User = Ember.Object.extend({
    requiredFields: Ember.A([
        "emailAddress",
        "handle",
        "password"
    ])
})