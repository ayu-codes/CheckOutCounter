# CheckOutCounter
This an application which performs the function of a check out counter of any billing station. It has locale support as well. The default locale is set to English, India.

This application implemented using Spring Boot's starter project.

Below REST end points exposed here :
- /billing : Post endpoint for adding items for billing.
- /billing/{lanugage}/{country} : Post endpoint for adding items for billing for a locale, example: /billling/de/DE.
- /generateBill : Get endpoint for generating the bill for the added items.
- /generateBill/{language}/{country} : Get endpoint for generating the bill for the added items for a locale, example: /generateBill/cs/CZ.

Headers: Accept:application/json, Content-Type-application/json

Body:

{"productName":"Banana",
"productCost":2000000.23,
"productCategory":"A"}

Product categories mantained for sales tax - A,B,C.
	
	
