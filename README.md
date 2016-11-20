./gradlew build

May specify port at the start (or the default is 8081)

cd build/javapackager-app
java -Djava.util.logging.config.file="../../src/logging.properties" -jar au_java.jar 8083

--
