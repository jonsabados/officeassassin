var NavigationManager = require("managers/NavigationManager");

module.exports = Ember.Route.extend({
  beforeModel: function (transition) {
    if (!NavigationManager.routeAllowed(transition.targetName)) {
      transition.abort();
      this.transitionTo("index");
    }
  }
});