This is a simple p2p chat app, you may change your global name,
connect to dudes using their hostname and address (easter egg: you may connect to yourself)

./gradlew build

May specify port at the start (or the default is 8081)

cd build/javapackager-app
java -Djava.util.logging.config.file="../../src/logging.properties" -jar au_java.jar 8083


--
