Assassin = Ember.Application.create();

Assassin.ApplicationController = Ember.Controller.extend({
  navigation: function() {
    var path = this.get('currentPath');
    return Ember.A([
        {
            route: "index",
            label: "Home",
            active: path == "index"
        },
        {
            route: "enlist",
            label: "Enlist",
            active: path == "enlist"
        }
    ])
    }.property("currentPath")
});

