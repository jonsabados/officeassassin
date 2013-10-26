var Assassin = require("config/App"),
  AssassinRoute = require("routes/AssassinRoute"),
  User = require("models/User");

module.exports = Assassin.EnlistRoute = AssassinRoute.extend({
  model: function () {
    return User.create({});
  }
});
