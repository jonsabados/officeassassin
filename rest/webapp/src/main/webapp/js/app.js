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