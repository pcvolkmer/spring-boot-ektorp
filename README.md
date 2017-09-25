# Spring Boot autoconfiguration for Ektorp

The primary goal of this project is Spring Boot auto configuration for [Ektorp](https://github.com/helun/Ektorp).

## Usage

Add this project as dependency to your project and add some configuration, e.g.:

```
spring:
  ektorp:
    client:
      url: http://example.com
      username: me
      password: secret
    defaultDatabase: my-database
```

This will provide a ready to use `CouchDbConnector`.

If more than one database should be used, a more complex configuration can be done with a Java-config like this:
```java

@Configuration
class CouchDbConfig {

    @Bean
    public CouchDbConnector firstCouchDbConnector(CouchDbInstance couchdbInstance) {
        return new StdCouchDbConnector("first-db", couchDbInstance)
    }

    @Bean
    public CouchDbConnector otherCouchDbConnector(CouchDbInstance couchdbInstance) {
        return new StdCouchDbConnector("other-db", couchDbInstance)
    }

}

```

Those different connectors can be used with Repositories as been shown in this example:

```java

@Repository
class DataRepository extends CouchDbRepositorySupport<Data> {

    // Use 'other-db' within this repository
    public DataRepository(@Qualifier("otherCouchDbConnector") CouchDbConnector couchDbConnector) {
        super(Data.class, couchDbConnector);
        initStandardDesignDocument();
    }

}

```
