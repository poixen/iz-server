## IZ Server - Version 1.0.0-SNAPSHOT

API server built with [Dropwizard 1.0.3](http://www.dropwizard.io/1.0.3/docs/) with persistant [PostgreSQL]() storage.

Was built in just over 2 days. That is for code and DBA duties.

Ways to improve the codebase have been annotated with `TODO:` though have been keept to a minimum. This is my first time using both Dropwizard and Postgres, though they share similarities to other technologies I have used.

Server can be configured using `iz-server.yaml`.

Ways to improve given more time:

+ HA database - Currently only tested on single node.
+ Better password and token management (salt, pepper hashing, expiring tokens, tokens that are not based on username / password).
+ Health checks for resources.
+ Tests. Unit and Cucumber.
+ Better documentation.
+ Testing on Docker, though the Dockerfile has been included.
+ Load testing, see how much traffic it can consume.
+ OAuth2 using a `ChainedFactory`

## Register

Method: `POST`

URL: `/register`

Headers: `Accept:application/json` `Content-Type:application/json`

Json Payload:

```json
{
  "username": "poixen",
  "password": "12341234",
  "name": "poixen",
  "age": 28
}
```

#### Information

`username` and `password` are required.

`name` and `age` are optional.

### Http codes

+ `201` User was registered
+ `400` Request was not valid. 



## Login

Method: `POST`

URL: `/login`

Headers: `Accept:application/json` `Content-Type:application/json`

Json Payload:

```json
{
  "username": "poixen",
  "password": "12341234"
}
```

### Example Response

```json
{
  "code": "200",
  "token": "cG9peGVuOmMxMjliMzI0YWVlNjYyYjA0ZWNjZjY4YmFiYmE4NTg1MTM0NmRmZjk="
}
```



#### Information

`username` and `password` are required.

Upon logging in you will be sent a `token` to use for actions that require Authentication.

### Http codes

- `200` User was logged in.
- `400` Request was not valid. 
- `401` Incorrect username/password. 



## Successful Login Logs

Method: `GET`

URL: `/successful_logins`

Headers: `Accept:application/json` `Authorization: Basic xxxxxxxxxxxxxxxxxxx`

### Example Response

```json
{
  "successful_logins": [
    "Mon Nov 14 10:51:37 CET 2016",
    "Mon Nov 14 10:53:28 CET 2016",
    "Mon Nov 14 10:53:36 CET 2016",
    "Mon Nov 14 10:53:37 CET 2016",
    "Mon Nov 14 11:02:05 CET 2016"
  ]
}
```

### Information

`Authorization` header should use Basic Auth, you should provide the token given to you when logging in.

The 5 most recent logins will be returned.

The server has been configured to use a `CachingAuthenticator`, if there is a delay between a login and retriving the logs, you may alter this in the `iz-server.yaml`.

### Http codes

- `200` Logs are returned.
- `403` You may not perform the request



## Failed Login Logs

Method: `GET`

URL: `/failed_logins`

Headers: `Accept:application/json` `Authorization: Basic xxxxxxxxxxxxxxxxxxx`

### Example Response

```json
{
  "failed_logins": [
    "Mon Nov 14 11:23:09 CET 2016",
    "Mon Nov 14 11:23:42 CET 2016",
    "Mon Nov 14 11:24:09 CET 2016",
    "Mon Nov 14 11:24:24 CET 2016",
    "Mon Nov 14 11:24:58 CET 2016"
  ]
}
```

### Information

`Authorization` header should use Basic Auth, you should provide the token given to you when logging in.

The 5 most recent logins will be returned.

The server has been configured to use a `CachingAuthenticator`, if there is a delay between a login and retriving the logs, you may alter this in the `iz-server.yaml`.

### Http codes

- `200` Logs are returned.
- `403` You may not perform the request