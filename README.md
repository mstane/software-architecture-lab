# MyBooks - GWT implementation #


## Technology stack ##


### Technology stack on the client side ###

 - GWT
 - GwtBootstrap3 to build responsive application to work on Desktops, Tablets and Mobile device.
 - MVP (Model View Presenter)
 - GWT Internationalization (i18n) support
 - GIN/GUICE dependency injection
 - Event Bus
 - GWT Places and Activities for History Management
 - UI binder
 - GWT cells
 - gwt-dispatch - command pattern for GWT

### Technology stack on the server side ###

 - Maven configuration for building, testing and running the application
 - JPA + Bean Validation
 - ORM framework - EclipseLink
 - Log management with Log4j
 - Testing with JUnit

### Requirements ###
* [Java Platform (JDK) 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Apache Maven 3.x](http://maven.apache.org/)

### Start ###
1. Open shell and type: `mvn exec:java` to start HSQLDB server
2. Open another shell and type: `mvn gwt:run`
3. Point your browser to [http://127.0.0.1:8888/](http://127.0.0.1:8888/)
4. Demo user credentials: `username:juk.bag@example.com / password: Mb.1234`

### Debug client side ###
1. After starting according previous steps
2. Open "Developer tools" in web browser Ctrl+Shift+I / "Sources" tab / `127.0.0.1:9876/sourcemaps/mybooks` browse to find the line for the breakpoint in the source
3. Put the breakpoint

### Debug server side ###
1. Open shell and type: `mvn exec:java` to start HSQLDB server
2. Open another shell and type: `mvn gwt:debug`
3. In Eclipse: Debug Configuration/ Remote Java Application/ New / `Host: localhost; Port: 8000`
4. Browse in Eclipse to find the line for the breakpoint in the source


