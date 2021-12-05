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

If you wish to only test the backend, here are the endpoints you can test. For the sake of this assignment, this API allows you to only **check balance**, **withdraw funds**, **authenticate** and **check status (how many notes remaining in the ATM machine)**. 

Two test accounts have been hard-coded and data can be used to include in the requests: 

1) Account Number: 123456789 | PIN: 1234 | Initial Balance: 800  | Overdraft: 200  
2) Account Number: 987654321 | PIN: 4321 | Initial Balance: 1230 | Overdraft: 150

ATM will initialise with the following state: 
- A total funds of €1500 made up of different bank notes. 
- The amount for each bank note is **10 x €50s**, **30 x €20s**, **30 x €10s** and **20 x €5s**

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

#### Important!
Once you've obtained the the "token" response, you will need to set the header **x-auth-token** with this value, eg: "x-auth-token: kkISXzefRsfN", to interact with the ```/balance``` and ```/withdraw``` endpoints.
 
---
#### Check Account Balance 
**Note**: You first need to create a session token to authenticate with the system. **The token will expire one minute after it was created** and you will need to create a new one if the current expires.

Assume that the generated session token and its value is ```kkISXzefRsfN```,  send the following params to the ```/balance``` endpoint:
- Endpoint: ```/balance```
- method: **GET**
- Expected Query Parameters ```accountNumber: Number|Long```
- Expected Headers: ```x-auth-token: String```

- Example Request
```bash
curl -X GET -G localhost:8080/balance -d accountNumber=123456789 -H "x-auth-token: kkISXzefRsfN"
```

- Example Response
```bash
{"accountNumber":123456789,"accountBalance":800.0, "maximumWithdrawAmount":1000.0}
```
- Error Messages:
  - Required request header 'x-auth-token' for method parameter type String is not present.
  - accountNumber parameter is required! accountNumber must be a Number, Int or Long.
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
curl -H "Content-Type: application/json" \
-H "x-auth-token: kkISXzefRsfN" \
-X POST \
-d '{"accountNumber":123456789, "value": 235}' \ 
http://localhost:8080/withdraw

```
- Expected Response:
```bash
{"accountNumber":123456789,"balance":565.0,"withdraw":{"fifties":4,"twenties":1,"tenners":1,"fivers":1,"total":235}}
```
- Error Messages:
  - Required request header 'x-auth-token' for method parameter type String is not present.
  - accountNumber, and value parameters are required! accountNumber must be a Number Int or Long, whereas token must be a String and value an Integer.
  - Insufficient Funds.
  - The value parameter must be a multiple of 50 or 20 or 10 or 5.
  - This request cannot be fulfilled because the ATM doesn't have enough cash.
  - The provided token has expired and thus cannot be used. You need to login again.
  - The token provided is invalid.

## License
[MIT](https://choosealicense.com/licenses/mit/)
