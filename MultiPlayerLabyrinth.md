# Overview #



# Setup #

The following chapter explains how to setup the project.

## Preparation ##

There are different steps to prepare the setup of the project.

### Java ###

_If you allready installed atleast the java jdk 1.6 go to the next step!_

Download Java from http://www.oracle.com/technetwork/java/javase/downloads/index.html

### Tortoise SVN ###

_In case you allready installed Tortoise or use another svn client, you can go to the next step!_

Install Tortoise SVN from [Tortoise SVN Download](http://tortoisesvn.net/downloads.html)


### Eclipse ###

_If you allready installed Eclipse atleast in the version 3.7 then go to the next step!_

Download the 'Eclipse IDE for Java Developers' from http://www.eclipse.org/downloads/ and extract it to a selfchoosen directory! Start eclipse and choose a workspace!

### Android ###

_If you allready installed and configured the adt plugin to eclipse then got to the next step!_

To setup android in eclipse you need to add the adt plugin for eclipse. Check out how to add the plugin on [http://developer.android.com](http://developer.android.com/sdk/eclipse-adt.html#installing).

### Install Android SDKs ###

_In case you allready installed atleast API 15 and API 8 you can go to the actual setup!_

To install the minimum needed Android SDKs you have to call the Android SDK Manager in eclipse and install the API Level 8, 15 and also the api level of your device (smartphone, tablet).

## Check out project ##

You can check out the project from the url https://bremen-gtug.googlecode.com/svn/projects/2012_02_18_GADC

Use this URL with **https** and not the default without SSL encryption, otherwise you can't commit your changes later!

If you use Eclipse itself as SVN client type in your username and password while checkout the project. Because, you can't add this information later in the properties and so you can't commit your changes! Remember, your password for Google code SVN is a special generated one, that you can see here: https://code.google.com/hosting/settings

In case you need the rights to commit code you'll have to ask [+Matthias Friedrich](https://plus.google.com/117981764820200252568/) or [+Steve Liedtke](https://plus.google.com/105644643668348314832/)!

## Set project.properties ##

After you checked out the project, you need to change the project\_Tortoise.properties or project\_Eclipse.properties (depends on what svn client you use) to project.properties

## Import projects to eclipse ##

The last step will be to import the existing projects to your eclipse.

### Import AndEngine extensions ###

The following projects are without exception library projects from https://github.com/nicolasgramlich/. To import these projects select **File -> Import... -> Open General -> Choose Existing Projects into Workspace -> Next**.

In the next window you have to select (under select root directory -> Use Browse) the subfolder **AE Extensions** of the checked out svn.
Under projects must be at least one project. Select finished and the import is done.

### Import MultiPlayerLabyrinth ###

In the last step you need to import the project which was created. Select **File -> Import... -> Open General -> Choose Existing Projects into Workspace -> Next** again.

In the next window you have to select the subfolder **MultiPlayerLabyrinth** of the checked out svn. Under projects you will find the project now. Select finished and the import is done.

# Gameidea #

Here you can find a short explanation how the game will look like after creating it!

The explanation is filled in on the 17th february in he evening!


# Network connection #

_Sorry, the next lines are German and will be translated soon._

Für den Verbindungsaufbau zwischen Client und Server ist ein Ablauf wie folgt geplant:

  * In einer voran gestellten Activity erscheint eine Abfrage ob man Client oder Server sein möchte
    * Server: Start der GameActivity mit "Warte auf Clients"-Dialog
    * Client: Liste mit im Netzwerk gefundenen Servern wird gezeigt
      * Mit Auswahl eines Servers startet die GameActivity, der die IP-Adresse des Servers im intent übergeben wird und die dann ebenfalls den "Warte auf Clients"-Dialog zeigt.
    * Sobald sich mind. X Spieler verbunden haben, hat der Server die Möglichkeit das Spiel zu starten.