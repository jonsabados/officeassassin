var NavItem = Ember.Object.extend({
    active: function() {
        return this.get("route") === Assassin.get("currentPath");
    }.property("route", "Assassin.currentPath")
});