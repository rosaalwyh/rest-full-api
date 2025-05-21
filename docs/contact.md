# Contact API Specs

## Create Contact

Endpoint: POST /api/contacts

Request Body :

```json
{
  "firstName": "Rosa",
  "lastName": "Alawiyah",
  "email": "alawiyahrosa@gmail.com",
  "phone": "62812281721"
}
```

Response Body (Success):

```json
{
  "message": "Successfully create contact",
  "data": {
    "id": "1",
    "firstName": "Rosa",
    "lastName": "Alawiyah",
    "email": "alawiyahrosa@gmail.com",
    "phone": "62812281721"
  }
}
```

Response Body (Failed):

```json
{
  "message": "Failed to create contact, email must be unique"
}
```

## Update Contact

Endpoint: PUT /api/contacts/{id}

Request Body :

```json
{
  "firstName": "Rosa",
  "lastName": "Alawiyah",
  "email": "alawiyahrosa@gmail.com",
  "phone": "62812281721"
}
```

Response Body (Success):

```json
{
  "message": "Successfully update contact",
  "data": {
    "id": "1",
    "firstName": "Rosa",
    "lastName": "Alawiyah",
    "email": "alawiyahrosa@gmail.com",
    "phone": "62812281721"
  }
}
```
Response Body (Failed):

```json
{
  "message": "Failed to update contact, email must be unique"
}
```


## Get Contact

Endpoint: GET /api/contacts/{id}

Response Body (Success):
```json
{
  "message": "Successfully get contact",
  "data": {
    "id": "1",
    "firstName": "Rosa",
    "lastName": "Alawiyah",
    "email": "alawiyahrosa@gmail.com",
    "phone": "62812281721"
  }
}
```

Response Body (Failed):

```json
{
  "message": "Failed to get contact, contact with id {id} not found"
}
```

## Search Contact

Endpoint: GET /api/contacts

Query Parameter :
- name: String, contact first name or last name, using like query, optional
- phone: String, contact phone, using like query, optional
- email: String, contact email, using like query, optional
- page: Integer, start from zero, default 0
- size: Integer, default 10

Response Body (Success):

```json
{
  "message": "Successfully get contact",
  "data": [
    {
      "id": "1",
      "firstName": "Rosa",
      "lastName": "Alawiyah",
      "email": "alawiyahrosa@gmail.com",
      "phone": "62812281721"
    },
    {
      "id": "2",
      "firstName": "Posa",
      "lastName": "Alawiyah",
      "email": "alawiyahposa@gmail.com",
      "phone": "62812211221"
    }
  ],
  "paging": {
    "totalPage": 10,
    "size": 10,
    "currentPage": 0
  }
}
```

Response Body (Failed):

## Remove Contact

Endpoint: DELETE /api/contacts/{id}

Response Body (Success):
```json
{
  "message": "Successfully delete contact"
}
```

Response Body (Failed):
```json
{
  "message": "Failed to get contact, contact with id {id} not found"
}
```