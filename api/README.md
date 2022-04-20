## API Online Store

### Technologies
- Java
    - Spring Boot
      - Dependencies:
        - spring-boot-starter-web
        - spring-boot-starter-data-jpa
        - postgresql
        - spring-boot-starter-validation
        - spring-boot-starter-jetty
        - spring-boot-starter-security
        - jjwt
        - lombok
        - modelmapper
        - spring-boot-starter-test
            
### Diagramas

#### Diagrama de Flujo

![Diagrama de Flujo](https://github.com/darwinfont2022/online-store/blob/main/api/diagrama_Flujo.png?raw=true)


#### Diagrama Entidad-Relacion

![Diagrama Entidad-Relacion](https://github.com/darwinfont2022/online-store/blob/main/api/diagrama_ER.png?raw=true)


### Rutes:
    /catalogs
**_Method_**:

    GET
**_Return_**:

Retorna una lista de objetos.
```
[
    {
        "id": 1,
        "tetle": "Catalog 1",
        "fee": 12
    }
]
```
**_~~Params~~_**:

_**~~Body~~**_

**_Method_**:

    POST
**_Return_**:

Retorna un objeto.
```
{
"id": 1
}
```
**_~~Params~~_**:

**_Body_**:
```
{
    "id": 1,
    "title": "Catalog 1",
    "fee": 12
}
```
### Rutes:
    /catalogs/{id}/
**_Method_**:

    DELETE
**_Return_**:

```
Http.status 200
```
**_Params_**:

    catalogId: Numero del catalogo
**_~~Body~~_**:

### Rutes:
    /catalogs/{id}/products
**_Method_**:

    GET
**_Return_**:

Retorna una lista de objetos.
```
[
    {
        "id": 1,
        "tetle": "Catalog 1",
        "price": 12,
        "image": "image1"
    }
]
```
**_Params_**:

    id: Numero del catalogo
**_~~Body~~_**:

**_Method_**:

    POST
Insertar productos al catalogo.

**_~~Return~~_**:

**_Params_**:

    catalogId: Numero del catalogo
**_Body_**:

Lista de productos.
```
[
    {
        "productId": 1,
        "fee": 12
    }
]
```

### Rutes:
    /catalogs/{catalogId}/products/{productId}
**_Method_**:

    DELETE
**_~~Return~~_**:

**_Params_**:

    catalogId: Numero del catalogo
    productId: Numero del producto

### Rute

    /clients/all

**_Method:_**

    GET

**_Return:_**

- Retorna una lista de de objetos.
```aidl
[
  {
    "id": 1
  }
]
```
**_~~Params:~~_**

```
```
**_~~Body~~_**
```aidl
```

### Rute

    /clients/register

**_Method:_**

    POST

**_Return:_**

- Retorna una lista de objetos.
```aidl
  {
    "id": 1
  }
```
**_~~Params:~~_**

```
```
**_Body:_**
```aidl
    {
        "name": "User",
        "username": "usertest",
        "email": "email@gmail.com",
        "address": "address",
        "city": "city",
        "password": "password"
    }
```

### Rute

    /payments

**_Method:_**

    POST

**_Return:_**

- Retorna un objetos.
```aidl
  Http.status 200
```
**_~~Params:~~_**

```
```
**_Body:_**
```aidl
    {
        "accountNumber": "1234567890123456",
        "cvv": "usertest",
        "save": true,
        "items": [
            {
                "productId": 1,
                "quantity": 2
            }
        ]
    }
```

### Rute

    /products

**_Method:_**

    GET

**_Return:_**

- Retorna un objetos.
```aidl
  [
    {
        "id": 1,
        "title": "Product 1,
        "price": 9,
        "image": "url"
    }
  ]
```
**_~~Params:~~_**

```
```
**_~~Body~~:_**
```aidl
```

### Rute

    /authenticate

**_Method:_**

    POST

**_Return:_**

- Retorna un objetos.
```aidl
    {
        "jwttoken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhMTMiLCJleHAiOjE2NTA0NDA0OTAsImlhdCI6MTY1MDQyMjQ5MH0.-5szlS5XOxlbphIdAs062yOve4rOplEVvwbP5-
        gcNykGyHpUVgRPgWms1aL1oYh-WfFw_gNmgooWLs37bND_Ww"
    }
```
**_~~Params:~~_**

```
```
**_Body:_**
```aidl
    {
        "username": "usermane1",
        "password": "password1"
    }
```
