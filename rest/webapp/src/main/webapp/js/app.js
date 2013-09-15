Assassin = Ember.Application.create({
    currentPath: '',
});

Assassin.ApplicationController = Ember.Controller.extend({
  navigation: Ember.A([
    NavItem.create({route: "index", label: "Home"}),
    NavItem.create({route: "enlist", label: "Enlist"})
  ]),

  removeNavItem: function(forRoute) {
    var nav = this.get("navigation");
    nav.removeObject(nav.find(function(item) {
       return item.get("route") == forRoute;
    }))
  },

  updateCurrentPath: function() {
    Assassin.set('currentPath', this.get('currentPath'));
  }.observes('currentPath')

});