# simple-service

Microservice Tester

**Environment**
```shell script
MESSAGE - the message for this simple service to return
```

**End Point**
```shell script
GET:/ - returns the set in MESSAGE
GET:/health - returns 200 or 503 (see PUSH:/unhealthy & PUSH:/healthy)
GET:/healthy - returns 200
GET:/unhealthy - returns 503
PUSH:/healthy - sets health to true, returns 200
PUSH:/unhealthy - sets health to false, returns 200
```
change
change for branch patch/test-1
change for branch patch/test-1
change for branch patch/test-1
