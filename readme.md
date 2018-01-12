# Project templates for microservice infrastucture 


## Eureka Service

Start Eureka server: `gradlew eureka-server:bootRun`

## Configuration Server

#### Prepare external configuration

- copy /conf/*.properties to the `${user.home}/conf`
NOTE: in windows `${user.home}` is `C:\Users\<username>`
- go to `${user.home}/conf` and execute `git init` 

or if you have your configuration under remtoe GIT repository

- `git clone <git config url>` if configuration exist under remote git repository
- change Config server property `spring.cloud.config.server.git.uri=file://...` pointing the cloned folder

#### Start Config server
`gradlew config-server:bootRun`

**NOTE**: config server must register in Eureka as `central-config` 

## Microservice Sample

Module `microservice-1` is a microservice sample that is using the centralized configuration (registered as `central-config`)
and after startup register itself as `microservice-test`

`gradlew microservice-1:bootRun`