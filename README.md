# CheckOutCounter
This an application which performs the function of a check out counter of any billing station. It has locale support as well.

Below REST end points exposed here :
- Adding items for billing : /billing
- Adding items for billing for a locale : /billing/{lanugage}/{country}, example: /billling/de/DE
- Generating the bill for the added items : /generateBill
- Generating the bill for the added items for a locale : /generateBill/{}/{}, example: /generateBill/cs/CZ
	
	
