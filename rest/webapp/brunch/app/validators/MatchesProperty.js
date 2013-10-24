module.exports = Ember.Validations.validators.local.MatchesProperty = Ember.Validations.validators.Base.extend({

  init: function () {
    this._super();
    if (!this.get("options.propertyToMatch")) {
      throw "propertyToMatch must be set";
    }
    if (!this.get("options.message")) {
      this.set("options.message", this.get("property") + " must match " + this.get("options.propertyToMatch"));
    }
    this._dependentValidationKeys.pushObject(this.get("options.propertyToMatch"));
  },

  call: function () {
    if (this.model.get(this.property) !== this.model.get(this.get("options.propertyToMatch"))) {
      this.errors.pushObject(this.get("options.message"));
    }
  }

});