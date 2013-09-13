
Assassin.Router.map(function() {
  this.resource("index", {path: "/"})
  this.resource("enlist", {path: "/enlist"})
});

Assassin.EnlistRoute = Ember.Route.extend({
  model: function () {
    return this.store.createRecord('user', {emailAddress: "Testing!"});
  }
});

