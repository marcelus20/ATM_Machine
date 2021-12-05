# ATM_Machine
Demo Java Application simulating ATM operations

## Installation
Clone the application:
```
git clone https://github.com/marcelus20/ATM_Machine
```

Build and Run Application using docker-compose

```bash
#run application
docker-compose build && docker-compose run
```

## Usage

### ATM Backend Endpoints
If you wish to only test the backend, here are the endpoints you can test. 

#### Check the Status of the ATM (How Many Notes Remaining)

- Endpoint: ```/status```
- method: **GET**
- Example Request: 
```bash
curl -X GET localhost:8080/status
```
- Example Response:
```bash
{"fifties":10,"twenties":30,"tenners":30,"fivers":20,"total":1500.0}
```
---
#### Creating a session token
- Endpoint: **/session**
- method: **POST**
- Expected Body Parameters ```accountNumber: Number|Long``` and ```pin: String``` 
- Example Request:
```bash
curl --header "Content-Type: application/json" \
--request POST \
--data '{"accountNumber":123456789,"pin":"1234"}' \
http://localhost:8080/session
```
- Example Response:
```bash
{"accountId":123456789,"token":"kkISXzefRsfN","expires":1638611411706} #It lasts only one minute!
```
- Error Messages:
  - accountNumber and pin parameters are required! accountNumber must be a Number Int or Long, whereas pin must be a String.
  - The account provided doesn't exist.
  - The PIN provided is incorrect.
---
#### Check Account Balance 
**Note**: You first need to create a session token to authenticate with the system. **The token will expire one minute after it was created** and you will need to create a new one if the current expires.

Assume that the generated session token and its value is ```kkISXzefRsfN```,  send the following params to the ```/balance``` endpoint:
- Endpoint: ```/balance```
- method: **GET**
- Expected Query Parameters ```accountNumber: Number|Long``` and ```token: String``` 

- Example Request
```bash
curl -X GET -G localhost:8080/balance -d accountNumber=123456789 -d token=hHZaMIzrxaQc # Assuming that token hasn't expired.
```

- Example Response
```bash
{"accountNumber":123456789,"accountBalance":800.0, "maximumWithdrawAmount":1000.0}
```
- Error Messages:
  - accountNumber and token parameters are required! accountNumber must be a Number, Int or Long, whereas token must be a String.
  - The token provided is invalid.
  - The provided token has expired and thus cannot be used. You need to login again.
---
#### Perform Withdraw Request
**Note**: You first need to create a session token to authenticate with the system. **The token will expire one minute after it was created** and you will need to create a new one if the current expires.

Assume that the generated session token and its value is ```kkISXzefRsfN``` and you want to withdraw 150 of value, send the following json body data to the ```/withdraw``` endpoint:
- Endpoint: ```/withdraw```
- method: **POST**
- Expected Body ```accountNumber: Number|Long```, ```token: String``` and ```value: Number|Integer``` 

```bash
curl --header "Content-Type: application/json" \
--request POST \
--data '{"accountNumber":123456789,"token":"kkISXzefRsfN", "value": 235}' \
http://localhost:8080/withdraw

```
- Expected Response:
```bash
{"accountNumber":123456789,"balance":565.0,"withdraw":{"fifties":4,"twenties":1,"tenners":1,"fivers":1,"total":235}}
```
- Error Messages:
  - accountNumber, token and value parameters are required! accountNumber must be a Number Int or Long, whereas token must be a String and value an Integer.
  - Insufficient Funds.
  - The value parameter must be a multiple of 50 or 20 or 10 or 5.
  - This request cannot be fulfilled because the ATM doesn't have enough cash.
  - The provided token has expired and thus cannot be used. You need to login again.
  - The token provided is invalid.

## License
[MIT](https://choosealicense.com/licenses/mit/)
