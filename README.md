# AWS API Gateway and Lambda to grant and verify JWT

This is still a small POC project. It is simple.

## Current method names

- grant: grant a token right now only setting a duration

> Request 
```json
  {
    "duration" : "int of minutes for grant"
  }
```

> Response
```json
  {
    "token" : "string token"
  }
```

- verify: will send back the system time and expiration time

> Request 
```json
  {
    "token" : "string token"
  }
```

> Response
```json
  {
    "valid" : "boolean"
  }
```

## Short commings

- INSECURE...
- Limited error handling
- Pretty much useless at the moment for grant

## Planning

- I have no idea just working at it as I go
- At the moment can be hit here: https://f1v8hefxu8.execute-api.us-east-2.amazonaws.com/test/token/verify

