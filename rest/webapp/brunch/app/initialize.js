window.Assassin = require("config/App");
require("config/Router");

var folderOrder = [
  "initializers",
  "mixins",
  "routes",
  "models",
  "views",
  "controllers",
  "helpers",
  "templates",
  "components"
];

folderOrder.forEach(function (folder) {
  var match = new RegExp("^" + folder + "/");
  window.require.list().filter(function (module) {
    return match.test(module);
  }).forEach(function (module) {
    require(module);
  });
});