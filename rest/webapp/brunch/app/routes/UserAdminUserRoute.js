var Assassin = require("config/App"),
  AssassinRoute = require("routes/AssassinRoute"),
  AssassinSubmitter = require("mixins/AssassinSubmitter"),
  User = require("models/User");

module.exports = Assassin.UserAdminUserRoute = AssassinRoute.extend(AssassinSubmitter, {
  model: function (params) {
    return new Ember.RSVP.Promise(function (resolve) {
      this._submit({
        type: "GET",
        url: "rest/users/id/" + params.user_id
      }).done(function (userData) {
        resolve(User.create(userData));
      });
    });
  }
});
