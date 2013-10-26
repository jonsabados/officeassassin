var Assassin = require("config/App"),
  NavItem = require("models/NavItem");

module.exports = Ember.Object.extend({
  navItems: function () {
    var roles = Assassin.get("user.roles") || [],
      ret = [NavItem.create({ route: "index", label: "Home" })];
    if (!Assassin.get("loggedIn")) {
      ret.push(NavItem.create({ route: "enlist", label: "Enlist" }));
    }
    if (roles.find(function (role) {
      return role.get("name") === "user_admin";
    })) {
      ret.push(NavItem.create({ route: "userAdmin.index", label: "User Admin" }));
    }
    return ret;
  }.property("Assassin.loggedIn", "Assassin.user.roles"),

  routeAllowed: function (route) {
    return this.get("navItems").find(function (navItem) {
      return navItem.get("route") === route;
    });
  }

}).create();