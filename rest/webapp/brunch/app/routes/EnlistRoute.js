var Assassin = require("config/App"),
  User = require("models/User");

module.exports = Assassin.EnlistRoute = Ember.Route.extend({
  model: function () {
    return User.create({});
  }
});
