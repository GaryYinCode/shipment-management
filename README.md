# shipment-management
Shipment management project for interview.

## 1. Reqirement
### Post Trade: 
* post a trade with title and goods quantity.

### query all trades:
* query all the post trades.

### query all the shipments in a trade:
* At frist, a trade has one shipment.
* After spliting and merging, there are many shipments in a trade.

### change the quantity of a trade:
* change the goods quantity of a trade.
* the shipments' quantity in the trade will be chagned proportionally.

### split a shipment:
* Split a shipment in multi shipments.
* split shipments' quantity sum equals to the original shipment quantity.

### merge some shipments into one shipment:
* Merge two or more shipments in a trade into one shipment.

## 2. Design
### Datebase
* Using H2 Database in memory.
* Two tables: Trade and Shipment.

### I18N
* For WebException Messages.
* For Enum item Names. 

### Exception
* WebServiceException include the api exceptions.

### API
API document and test： Swagger. http://localhost:8080/swagger-ui.html

API response class: ModelJsonRespone.
* code：0 = error, 1 = success, -11 = wrong param, -12 = access deney
* errorNo: exceptionNo in WebServiceException.
* errorDesc: i18n exception message.
* result: the api data according to the biz.

## 3. Test
Data initialized in launch, include one trade and five shipments (1 root shipment, 3 split shipments, 1 merged shipment).

JUnit test, include: Please start the application in advanced.
* service test cases.
* WebServiceException test cases.
* controller test cases. 

## 4. Deployment
* JDK 1.8
* Maven 3.x.
* SpringBoot jar emmbeded tomcat.
