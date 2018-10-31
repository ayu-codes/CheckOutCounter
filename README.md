# CheckOutCounter
This an application which performs the function of a check out counter of any billing station. It has locale support as well.

There are two REST end points exposed here :
	1. Adding items for billing : /billing
	2. Adding items for billing for a locale : /billing/{lanugage}/{country}, example: /billling/de/DE
	3. Generating the bill for the added items : /generateBill
	4. Generating the bill for the added items for a locale : /generateBill/{}/{}, example: /generateBill/cs/CZ
	
	
