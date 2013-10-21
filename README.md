HTML5-Player
============

An HTML5 version of the [catrobat project](http://catrobat.org/).

Current state
-------------

Our next milestone is to make catrobat projects work with the current version
of the catrobat XML format.

Setup for developers
--------------------

1. Use Eclipse as IDE (recommended: Eclipse IDE for Java Developers).
2. Install [GWT](http://www.gwtproject.org/gettingstarted.html) as eclipse plugin.
3. Download the Catrobat repository by triggering `git clone git@github.com:Catrobat/HTML5-Player.git`.
4. Import the project to eclipse using `File > Import > Git > Projects from Git > Existing local repository`.
5. Be sure to add all dependencies to the Build Path (manually in eclipse).
6. Configure `eclipse-java-google-style.xml` as your code style (has not been applied globally yet).
7. Right-click on the project, `Google`, `Compile GWT`.
8. `Run As`, `Web application`.
9. HTML5 Player should now be running.

(Note for Linux from 14th of Oct 2013: GWT requires the Android SDK which
in turn requires the debian package `ia32-libs`)

If you come across an error where the browser only shows you an error
"HTTP ERROR: 404. Problem accessing /Html5Player.html. Reason: NOT_FOUND",
you have come across a known GWT bug. Remove "GWT SDK" from your build path
and add it again. It should work now again.

Bugs
----

Please report bugs or problems either to

    contact@catrobat.org

or report an issue in the issue tracker at

    https://github.com/Catrobat/HTML5-Player/issues


Greets,
the Catrobat HTML5 team
