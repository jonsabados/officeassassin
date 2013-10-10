var Assassin = require("config/App"),
  NavItem = require("models/NavItem");

Assassin.ApplicationController = Ember.Controller.extend({
  navigation: function() {
    var ret = [NavItem.create({route: "index", label: "Home"})];
    if(!Assassin.get("loggedIn")) {
      ret.push(NavItem.create({route: "enlist", label: "Enlist"}));
    }
    return ret;
  }.property("Assassin.loggedIn"),

  bounceToIndex: function() {
    if(!this.get("navigation").find(function(navItem) {
      return navItem.get("route") == this.get("currentPath");
    }.bind(this)) != undefined) {
      this.transitionToRoute("index");
    }
  }.observes("navigation"),

  updateCurrentPath: function() {
    Assassin.set('currentPath', this.get('currentPath'));
  }.observes('currentPath')

});