var Assassin = require("config/App"),
  NavItem = require("models/NavItem");

Assassin.ApplicationController = Ember.Controller.extend({
  navigation: function () {
    var roles = Assassin.get("user.roles") || [],
      ret = [NavItem.create({ route: "index", label: "Home" })];
    window.console.log(roles);
    if (!Assassin.get("loggedIn")) {
      ret.push(NavItem.create({ route: "enlist", label: "Enlist" }));
    }
    if (roles.find(function (role) {
      return role.get("name") === "user_admin";
    })) {
      ret.push(NavItem.create({ route: "userAdmin", label: "User Admin" }));
    }
    return ret;
  }.property("Assassin.loggedIn", "Assassin.user.roles"),

  bounceToIndexIfNeeded: function () {
    if (this.get("navigation").find(function (navItem) {
      return navItem.get("route") === this.get("currentPath");
    }.bind(this)) === undefined) {
      this.transitionToRoute("index");
    }
  }.observes("navigation"),

  updateCurrentPath: function () {
    Assassin.set("currentPath", this.get("currentPath"));
  }.observes("currentPath")

});