module.exports = Ember.Mixin.create({
  serialize: function () {
    var propertyNames = this.get("toSerialize");
    return this.getProperties(propertyNames);
  }
});
