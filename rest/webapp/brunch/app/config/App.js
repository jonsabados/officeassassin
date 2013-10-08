module.exports = Ember.Application.create({
    currentPath: null,
    username: null,
    password: null,
    user: null,
    loggedIn: false
});

function trimToUndefinedReplacer(key, value) {
    if(typeof(value) == "string" && value == "") {
        return undefined;
    } else {
        return value;
    }
}