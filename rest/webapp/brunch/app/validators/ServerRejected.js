module.exports = Ember.Validations.validators.local.ServerRejected = Ember.Validations.validators.Base.extend({

  rejectedValuesPath: function () {
    return this.get("options.rejectedValues") + "." + this.get("property");
  }.property("property", "options.rejectedValues").readOnly(),

  init: function () {
    this._super();
    if (!this.get("options.rejectedValues")) {
      throw "rejectedValues must be set";
    }
    this._dependentValidationKeys.pushObject(this.get("rejectedValuesPath") + ".@each");
  },

  call: function () {
    var property = this.get("property"),
      rejectedValues = this.model.get(this.get("rejectedValuesPath"));
    if (rejectedValues) {
      this._checkIfRejected(this.get("model").get(property), rejectedValues);
    }
  },

  _checkIfRejected: function (currentValue, rejectedValues) {
    var rejectedValue = rejectedValues.find(function (item) {
      return item.value === currentValue;
    });
    if (rejectedValue) {
      this.errors.pushObject(rejectedValue.message);
    }
  }

});