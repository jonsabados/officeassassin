var Assassin = require("config/App"),
  UserRepository = require("repositories/UserRepository");

module.exports = Assassin.UserAdminIndexRoute = Ember.Route.extend({
  model: function () {
    return UserRepository.create({
      resultsPerPage: 10,
      currentPage: 1
    });
  }
});
