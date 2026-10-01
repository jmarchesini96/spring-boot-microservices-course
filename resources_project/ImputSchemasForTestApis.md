# Testing micorservices scheme
This file contains the imputs shcmes for to test POST method on each microservices(customer, product, and transactions)

## 1- Products scheme

### scheme product 1
 ```json
 {
    "code": "01",
    "name": "Cuenta horros"
  }
```
## scheme product 1
 ```json
  {
    "code": "005",
    "name": "tarjeta credito"
  }
```
## scheme product 1
 ```json
  {
    "code": "00687l",
    "name": "Puntos banco"
  }
```
## 2- Customer schemes
### scheme customer 1
```json
{
  "code": "01",
  "name": "Carlos",
  "phone": "string",
  "iban": "000251487",
  "surname": "string",
  "address": "string",
  "products": [
    {
      "productId": 1,
      "productName": "string"
    },
 {
      "productId": 2,
      "productName": "string"
    }
  ],
  "transactions": []
}
```
## 3- Transaction Schema

### scheme Transaction 1
```json
{
  "reference": "6524ld",
  "accountIban": "000251487",
  "date": "2022-08-15T11:36:07.683Z",
  "amount": 450,
  "fee": 2,
  "description": "Consignación",
  "status": "Liquidada",
  "channel": "OFICINA"
}
```
### scheme Transaction 2
```json
{
  "id": 0,
  "reference": "hg52487",
  "accountIban": "000251487",
  "date": "2022-11-10T15:20:00.437Z",
  "amount": 100,
  "fee": 3,
  "description": "Retiro",
  "status": "Rechazada",
  "channel": "WEB"
}
```
### scheme Transaction 3
```json
{
  "id": 0,
  "reference": "53254jks",
  "accountIban": "000257849",
  "date": "2022-11-10T15:20:00.437Z",
  "amount": 100,
  "fee": 3,
  "description": "Retiro",
  "status": "Liquidada",
  "channel": "WEB"
}
```

