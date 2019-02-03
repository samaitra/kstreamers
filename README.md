# kstreamers
Data streaming application to demonstrate how Apache Ignite can be used as data source

### Data Streaming

![Data Streaming](https://github.com/samaitra/kstreamers/raw/master/resources/data_streaming.jpg) 


### Clone Apache Ignite 

```
$ git clone https://github.com/apache/ignite
```

### Build Apache Ignite 

```
$ mvn clean package install -DskipTests
```

### Build the Flink program :
```
$ ./gradlew clean build
```

### Run the Flink program :
```
$ java -jar build/libs/kstreamers-0.0.1-SNAPSHOT.jar
```

### Ignite rest service
To check the cache key values you can use the Ignite rest service 

```
$ curl -X GET http://localhost:8080/ignite\?cmd\=getall\&k1\=jam\&cacheName\=testCache
```

### Scan cache 
To check all the keys from an Ignite cache the following rest service can be used
```
$ curl -X GET http://localhost:8080/ignite?cmd=qryscanexe&pageSize=10&cacheName=testCache
```
