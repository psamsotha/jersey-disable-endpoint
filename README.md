Example of Disabling and Enabling Endpoints an Runtime
======================================================

This is a POC based on the Stack Overflow question
[Need Jersey2 technique to enable or disable services during runtime][2].

To handle this requirement, a Jersey [Prematching filter][2] is used to determine
if endpoints are disabled, with the use of a helper service. The service will
listen for changes in the configuration file, and update the disabled endpoints accordingly.

An [`ApplicationEventListener`][3] is also used to start and stop the file watcher
on application start and application end, respectively.

### Run

```
mvn exec:java
```

### Make Changes

Look in the `app.yml` in the `config` directory. You will see the following

```yaml
disabledEndpoints:
  - /lions
#  - /tigers
#  - /bears
```

There are three resources in this application. The Lions, Tigers, and Bears resources.
In the YAML, currently only the Lions endpoint is disabled. Try it with cURL

```
curl -i http://localhost:8080/api/bears        200
curl -i http://localhost:8080/api/tigers       200
curl -i http://localhost:8080/api/lions        404
```

Now uncomment the Tigers and Bears endpoints, and comment the Lions endpoint.
If you make the same request again you should now see the results are reversed.

### Note

As I stated in the beginning, this is just a POC. I don't know if this is the
most efficient way or not.


[1]: http://stackoverflow.com/q/43614223/2587435
[2]: https://jersey.java.net/documentation/latest/user-guide.html#d0e9766
[3]: https://jersey.java.net/documentation/latest/user-guide.html#d0e15956