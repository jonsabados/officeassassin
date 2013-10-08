var Assassin = require("config/App"),
  NavItem = require("models/NavItem");

Assassin.ApplicationController = Ember.Controller.extend({
  navigation: Ember.A([
    NavItem.create({route: "index", label: "Home"}),
    NavItem.create({route: "enlist", label: "Enlist"})
  ]),

  setNavState: function(forRoute, state) {
    this.get("navigation").find(function(item) {
      return item.get("route") == forRoute;
    }).set("enabled", state);
  },

  removeNavItem: function(forRoute) {
    this.setNavState(forRoute, false);

    if(forRoute == this.get("currentPath")) {
       this.transitionToRoute("index");
    }
  },

  addNavItem: function(forRoute) {
    this.setNavState(forRoute, true);
  },

  updateCurrentPath: function() {
    Assassin.set('currentPath', this.get('currentPath'));
  }.observes('currentPath')

});