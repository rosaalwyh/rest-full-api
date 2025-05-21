# User API Specs

## Register User

- Endpoint: POST /api/users

Request Body:
```json
{
   "username" : "rosa",
  "password": "Rosa12345",
  "name": "Rosa"
}
```
Response Body (Success):
```json
{
 "message": "Successfully register user" 
}
```

Response Body (Failed):

```json
{
  "data": "KO",
  "error_messages": "Username is duplicate"
}
```


## Login User

Endpoint : POST /api/auth/login

Request Body :

```json
{
  "username" : "rosa",
  "password" : "rosa12345" 
}
```

Response Body (Success) :

```json
{
  "data" : {
    "token" : "TOKEN",
    "expiredDate" : 2342342423423 // milliseconds
  }
}
```

Response Body (Failed, 401) :

```json
{
  "error_message" : "Username or password is incorrect"
}
```