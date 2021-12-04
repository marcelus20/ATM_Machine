# ATM_Machine
Demo Java Application simulating ATM operations

## Installation
Clone the application:
```
git clone https://github.com/marcelus20/ATM_Machine
```

Navigate to project root directory:
```
cd ATM_Machine
```

Before invoking the docker-compose build, you first need to compile the /atm service. 

```bash
# Navigate to the atm folder and build the jar
cd atm
# Build the atm service
mvn clean build
```

A jar file should have been created inside /atm/target/. Once that's done, you are good to run the docker-compose command to build the intire application: 

```bash
# Go back to the root project folder and invoke docker-compose build and up
cd ..
docker-compose build
#run application
docker-compose up
```

## Usage

### ATM Backend Endpoints
If you wish to only test the backend, here are the endpoints you can test. 

#### Check the Status of the ATM (How Many Notes Remaining)

- Endpoint: ```/status```
- method: **GET**
- Example Request: 
```
curl -X GET localhost:8080/status
```
- Example Response:
```
{"fifties":10,"twenties":30,"tenners":30,"fivers":20,"total":1500.0}
```
---
#### Check Account Balance 
You first need to create a session token to authenticate with the system:

- Endpoint: **/session**
- method: **POST**
- Expected Body Parameters ```accountNumber: Number|Long``` and ```pin: String``` 
- Example Request:
```
curl --header "Content-Type: application/json" \
--request POST \
--data '{"accountNumber":123456789,"pin":"1234"}' \
http://localhost:8080/session
```
- Example Response:
```
{"accountId":123456789,"token":"kkISXzefRsfN","expires":1638611411706}
```
Use that token to send a request to ```/balance``` endpoint:
- Endpoint: **/balance**
- method: **GET**
- Expected Query Parameters ```accountNumber: Number|Long``` and ```token: String``` 

##### Example using CURL Request for /balance
```
curl -X GET -G localhost:8080/balance -d accountNumber=123456789 -d token=hHZaMIzrxaQc # Assuming that token hasn't expired.
```

##### Example Response from /balance
```
{"accountNumber":123456789,"accountBalance":800.0}
```
---
## License
[MIT](https://choosealicense.com/licenses/mit/)
