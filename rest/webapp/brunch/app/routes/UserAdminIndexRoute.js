var Assassin = require("config/App"),
  AssassinRoute = require("routes/AssassinRoute"),
  UserRepository = require("repositories/UserRepository");

module.exports = Assassin.UserAdminIndexRoute = AssassinRoute.extend({
  model: function () {
    return UserRepository.create({
      fetchSize: 10,
      firstRecord: 0
    });
  }
});
