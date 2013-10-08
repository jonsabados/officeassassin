module.exports = Ember.Object.extend({
    enabled: true,
    active: function() {
        return this.get("route") === Assassin.get("currentPath");
    }.property("route", "Assassin.currentPath")
});