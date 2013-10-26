Office Assassin
===============

What is this?
-------------
The combination of an office full of developers picking up nerf weapons without any direction -
[see wikipedia](http://en.wikipedia.org/wiki/Assassin_\(game\)), and me wanting to learn scala and how it might look
living with an existing java stuff (like jersey).

What it is not?
---------------
* This is most likely NOT a good example of scala
* This is NOT a good example of how I would actually implement a fairly simple app
* This is NOT a good example of the level of testing I would add to a real project. The test coverage is pretty crappy,
 and usually only exists for things where it was easier to write a test than fire the app up.
* This is probably NOT useful - even if this ever reaches a point where a usable app is produced, it will just be an app
 to assign someone a target and track when someone else says that they hit them.

License
-------
* Ummmm... if your really interested in re-using any of this code I would probably advise against it, but since it
 does bother me to see a project on github without a clear license, think Apache - I may put the proper files and
 notices in at some point, but if it really matters file a bug and force me to do it.

Building and running
--------------------
In order to build Office Assassin you will need to have mvn, npm, bower and brunch on your path. Get maven, an up
to date version of nodejs (note, the version of node that is in the base ubuntu repositories is not up to date, see
https://github.com/joyent/node/wiki/Installing-Node.js-via-package-manager), and then execute:

  sudo npm install -g bower

  sudo npm install -g brunch

At this point you should be able to build the app:

  mvn verify

(note, mvn package will work as well but there are integration tests that will be skipped)

The build will produce a war suitable for running in most any servlet container, as well as a standalone jar.  Without
specifying a config file the directory /var/lib/assassin must exist and must be writable by the user launching the app
(see rest/rest-core/src/main/resources/default-assassin-config.properties). This can be changed by specifying the system
property assassin.config.

To run the standalone jar:

  java -jar rest/standalone/target/standalone-1.0-SNAPSHOT.war

Or:

  java -jar -Dassassin.config=YOUR_CONFIG_FILE_HERE rest/standalone/target/standalone-1.0-SNAPSHOT.war

See rest/webapp/ for a war suitable for deployment in an external servlet container.