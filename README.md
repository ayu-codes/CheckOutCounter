# CheckOutCounter
This an application which performs the function of a check out counter of any billing station. It has locale support as well.

Below REST end points exposed here :
- /billing : Adding items for billing.
- /billing/{lanugage}/{country} : Adding items for billing for a locale, example: /billling/de/DE.
- /generateBill : Generating the bill for the added items.
- /generateBill/{language}/{country} : Generating the bill for the added items for a locale, example: /generateBill/cs/CZ
	
	
