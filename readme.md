# Project templates for microservice infrastucture 


## Eureka Server

Start Eureka server: `gradlew eureka-server:bootRun`

## Configuration Server

#### Prepare external configuration under GIT

- copy /conf/*.properties to the `${user.home}/conf`
NOTE: in windows `${user.home}` is `C:\Users\<username>`
- go to `${user.home}/conf` and create the GIT repo with configuration from scratch
1. `git init` 
2. `git add application.properties`  for all the `.properties` and `.yml` configs
3. `git commit -m "added config"`
4. optional (if you want to link to the remote GIT repo): `git remote add <git url>`

NOTE: or if you already have your configuration under remote GIT repository just create `conf` folder and execute inside `git clone <git url>`
- change Config server property `spring.cloud.config.server.git.uri=file://...` pointing the cloned folder

#### Start Config server
`gradlew config-server:bootRun`

**NOTE**: config server must register in Eureka as `central-config` 

## Microservice Samples

1. Module `microservice-1` is a microservice sample that is using the centralized configuration (registered as `central-config`)
and after startup register itself as `microservice-1`

`gradlew microservice-1:bootRun`

2. Module `microservice-2-feign` is a microservice sample that is consuming `microservice-1` with the help of [Feign](https://cloud.spring.io/spring-cloud-netflix/single/spring-cloud-netflix.html#spring-cloud-feign)
and after startup register itself as `microservice-2-feign`

`gradlew microservice-2-feign:bootRun`

## Api Gateway

Module `microservice-api` provides single entry point for the consumers

`gradlew microservice-api:bootRun`

Api is confgured to redirect `/service1/**` calls to `microservice-1` and `service2/**` calls to `microservice-2-feign`

## Hystrix dashboard

Hystrix dashbord is run on tomcat port `8085` and service id `monitoring`
```gradlew :monitoring:bootRun```

To get metrics for `microservice-1` just put this url `http://localhost:8081/hystrix.stream` to the dashbord for monitoring
Hystrix should be configured for each microsercvice that is planned to include to the dashboard

To show the aggregated turbine metrics use this URL in the dashboard: `http://localhost:8086/turbine.stream`

**NOTE:** at the moment turbine dashboard doesn't work. To be checked.

