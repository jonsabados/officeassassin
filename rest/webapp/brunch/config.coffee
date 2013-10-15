sysPath = require 'path'

exports.config =
  # See http://brunch.io/#documentation for documentation.
  files:
    javascripts:
      joinTo:
        'js/assassin.js': /^app/
        'js/vendor.js': /^(vendor|bower_components)/

      order:
        after: ['vendor/scripts/ember-validations-1.0.0.beta.1.js']

    stylesheets:
      joinTo:
        'css/assassin.css': /^(app|vendor)/

    templates:
      precompile: true
      root: 'templates'
      joinTo: 'js/assassin.js' : /^app/

