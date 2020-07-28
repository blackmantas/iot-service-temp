# IoT Temperature Service

## GET Requests
### /ping
Returns OK if service is alive
### /temp
Returns last 100 records
### /temp/{id}
Returns temperature record by id

## POST Requests
### /temp
#### Request Example
```
[
   {
        "temperature" : "29.4",
        "sensorId" : "building-1-sens-13",
        "dateTime" : "2020-07-24T21:22:10",
        "errorCodes" : [12, 6]     // optional, 0 or more error codes
    },
    {
        ...
    } 
]
```
#### Response Example
```
[
   {
        "id" : "7f0b463d-b845-4cf8-ad2e-c0c5897e749f"
        "temperature" : "29.4",
        "sensorId" : "building-1-sens-13",
        "dateTime" : "2020-07-24T21:22:10",
        "errorCodes" : [12, 6]     // optional, 0 or more error codes
    },
    {
        ...
    } 
]
```