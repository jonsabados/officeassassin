var AssassinSubmitter = require("mixins/AssassinSubmitter"),
  User = require("models/User");

module.exports = Ember.Object.extend(AssassinSubmitter, {
  fetchSize: null,
  firstRecord: null,
  totalRecords: null,
  results: null,
  hasMoreResults: null,

  init: function () {
    this._super();
    window.__TEST = this;
  },

  fetchResults: function () {
    return this._submit({
      type: "GET",
      url: "rest/users/?pageLength=" + this.get("fetchSize") + "&offset=" + this.get("firstRecord")
    }).done(function (results, textStatus, jqXHR) {
      this.set("hasMoreResults", jqXHR.status === 206);
      this.set("totalRecords", results.totalRecords);
      this.set("results", results.data.map(function (user) {
        return User.create(user);
      }));
    }.bind(this));
  }.observes("fetchSize", "firstRecord").on("init")
});