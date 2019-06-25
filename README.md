# AWS API Gateway and Lambda to grant and verify JWT

This is still a small POC project. It is simple.

```json
{
  "method" : "MethodName",
  "token" : "for verify method",
  "duration" : "int of minutes for grant"
}
```
## Current method names

- health: verify that the lambda still exist and is currently running
- grant: grant a token right now only setting a duration
- verify: will send back the system time and expiration time

## Short commings

- INSECURE...
- Limited error handling
- Pretty much useless at the moment for the grant

## Planning

- I have no idea just working at it as I go
- At the moment can be hit here: https://f5v6s3y92m.execute-api.us-east-2.amazonaws.com/POC/LambdaPOC

