var Assassin = require("config/App");

module.exports = Assassin.Router.map(function () {
  this.route("index", { path: "/" });
  this.route("enlist", { path: "/enlist" });
  this.resource("userAdmin", { path: "/userAdmin" }, function () {
    this.route("user");
  });
});
