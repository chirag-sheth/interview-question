Url Shortening Service - Part 2
===============================

APIs for Url shortening service. It converts long urls into short codes.
The service also provides a mechanism to get the original (long) Url when provided with the exact short Url code.
Added H2 Database support with persistence mechanism with this version.

# Requirements

### Part one - Basic local service
* A REST API that takes as input a Url and gives back a short Url
* A REST API that takes the short Url and gives back the original Url

### Part two - Adding persistence layer
We would like to have persistence of the data in case the server drops.
`application.yml` is configured for H2 database, but feel free to use any other relational DB you are comfortable with to save the data.
Make sure that your app will work with H2 as well as it will be tested with H2 (integartion-tests can help here).

# How to run
Run it (using `mvn spring-boot:run`) or your favorite IDE.

## Table Structure

```sql
create table tbl_url (
id bigint not null, 
create_date timestamp, 
expiry_date timestamp, 
long_url TEXT, 
short_url varchar(255), 
primary key (id)
);

create sequence hibernate_sequence start with 1 increment by 1;
```

### Links
- Swagger (API documentation for this project is included with Swagger Configuration)
  - http://localhost:5000/swagger-ui.html

#### Examples

### POST http://localhost:5000/short?url=https://stash.backbase.com/projects/PO/repos/payment-order-integration-spec/browse/src/main/resources/schemas/definitions.json#38
```json
{
    "id": 1,
    "shortUrl": "IXgfKTze7",
    "longUrl": "https://stash.backbase.com/projects/PO/repos/payment-order-integration-spec/browse/src/main/resources/schemas/definitions.json",
    "createDate": "2020-07-31T03:20:12.593+0000",
    "expiryDate": "2020-07-31T03:50:12.593+0000"
}
```

### GET http://localhost:5000/long?tiny=IXgfKTze7
```json
{
    "id": 1,
    "shortUrl": "IXgfKTze7",
    "longUrl": "https://stash.backbase.com/projects/PO/repos/payment-order-integration-spec/browse/src/main/resources/schemas/definitions.json",
    "createDate": "2020-07-31T03:20:12.593+0000",
    "expiryDate": "2020-07-31T03:50:12.593+0000"
}
```

### GET http://localhost:5000/urls
```json
[
    {
        "id": 1,
        "shortUrl": "IXgfKTze7",
        "longUrl": "https://stash.backbase.com/projects/PO/repos/payment-order-integration-spec/browse/src/main/resources/schemas/definitions.json",
        "createDate": "2020-07-31T03:20:12.593+0000",
        "expiryDate": "2020-07-31T03:50:12.593+0000"
    },
    {
        "id": 2,
        "shortUrl": "EfsgBcIBR",
        "longUrl": "https://stash.backbase.com/",
        "createDate": "2020-07-31T03:21:46.019+0000",
        "expiryDate": "2020-07-31T03:51:46.019+0000"
    }
]
```


