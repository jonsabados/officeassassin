var Assassin = require("config/App"),
  NavItem = require("models/NavItem");

module.exports = Ember.Object.extend({

  hasUserAdmin: function () {
    return (Assassin.get("user.roles") || []).find(function (role) {
      return role.get("name") === "user_admin";
    });
  }.property("Assassin.user.roles").readOnly(),

  navItems: function () {
    var ret = [NavItem.create({ route: "index", label: "Home" })];
    if (!Assassin.get("loggedIn")) {
      ret.push(NavItem.create({ route: "enlist", label: "Enlist" }));
    }
    if (this.get("hasUserAdmin")) {
      ret.push(NavItem.create({ route: "userAdmin", label: "User Admin" }));
    }
    return ret;
  }.property("Assassin.loggedIn", "Assassin.user.roles"),

  allowedRoutes: function () {
    var allRoutes = this.get("navItems").map(function (route) {
      return route.route;
    });
    if (this.get("hasUserAdmin")) {
      allRoutes.push("userAdmin.index");
      allRoutes.push("userAdmin.user");
    }
    return allRoutes;
  }.property("navItems", "hasUserAdmin"),

  routeAllowed: function (route) {
    return this.get("allowedRoutes").contains(route);
  }

}).create();