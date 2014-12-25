mvn clean install - build the project

mvn appengine:devserver - run the app in the development server on the local machine
mvn clean install appengine:devserver -Dmaven.test.skip=true

mvn appengine:update - uploading the application