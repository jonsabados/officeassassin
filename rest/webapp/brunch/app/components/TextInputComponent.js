var Assassin = require("config/App");

module.exports = Assassin.TextInputComponent = Ember.Component.extend({
  classNames: ["form-group", "assassin-text-input"],
  classNameBindings: ["showError:has-error"],
  hasFocusedOut: false,
  errors: null,
  invalid: Ember.computed.gt("errors.length", 0),
  type: "text",
  label: null,
  inputId: null,
  value: null,
  placeholder: null,

  showError: Ember.computed.and("invalid", "hasFocusedOut"),

  focusOut: function () {
    this.set("hasFocusedOut", true);
  }
});