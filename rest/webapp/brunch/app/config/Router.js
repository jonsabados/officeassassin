var Assassin = require("config/App");

module.exports = Assassin.Router.map(function() {
  this.resource("index", {path: "/"})
  this.resource("enlist", {path: "/enlist"})
});
