var AssassinSubmitter = require("mixins/AssassinSubmitter"),
  User = require("models/User");

module.exports = Ember.Object.extend(AssassinSubmitter, {
  resultsPerPage: null,
  currentPage: null,
  totalResults: null,
  results: null,

  fetchResults: function () {
    return this._submit({
      type: "GET",
      url: "rest/users/?pageLength=" + this.get("resultsPerPage")
    }).done(function (results) {
      this.set("results", results.data.map(function (user) {
        return User.create(user);
      }));
    }.bind(this));
  }.observes("resultsPerPage", "currentPage").on("init")
});