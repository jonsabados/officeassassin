var Assassin = require("config/App"),
  NavigationManager = require("managers/NavigationManager");

Assassin.ApplicationController = Ember.Controller.extend({
  _navManager: NavigationManager,

  navigation: Ember.computed.alias("_navManager.navItems"),

  bounceToIndexIfNeeded: function () {
    if (!NavigationManager.routeAllowed(this.get("currentPath"))) {
      this.transitionToRoute("index");
    }
  }.observes("navigation")
});