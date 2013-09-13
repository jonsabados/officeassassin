Assassin.User = DS.Model.extend({
    emailAddress: DS.attr('string'),
    handle: DS.attr('string'),
    fullName: DS.attr('string'),
    password: DS.attr('string')
})
