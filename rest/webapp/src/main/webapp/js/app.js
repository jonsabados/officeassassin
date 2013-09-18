Assassin = Ember.Application.create({
    currentPath: undefined,
    username: undefined,
    password: undefined,
    user: undefined,
    loggedIn: false
});

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

function notEmpty(val) {
    return val != undefined && val != "";
}

function trimToUndefinedReplacer(key, value) {
    if(typeof(value) == "string" && value == "") {
        return undefined;
    } else {
        return value;
    }
}