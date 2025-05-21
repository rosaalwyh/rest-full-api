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
