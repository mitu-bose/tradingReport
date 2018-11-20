# tradingReport

This is a trading report application.
The endpoint "http://localhost:8080/transactionReport" is used for displaying the report. It is a post request.
The Sample Input of the request is.

{
	"transactions":
	[{ "entity": "foo", 
		"instruction": "B", 
		"agreedFx": 0.5 ,
		"currency": "SGP", 
		"instructionDate": "01 Jan 2016",
		"settlementDate": "03 Jan 2016",
		"units": 100, 
		"pricePerUnit": 100.25
	},

	{ "entity": "foo", 
		"instruction": "S", 
		"agreedFx": 0.5 ,
		"currency": "SGP", 
		"instructionDate": "01 Jan 2016",
		"settlementDate": "03 Jan 2016",
		"units": 200, 
		"pricePerUnit": 100.25
	},

	{ "entity": "Bar", 
		"instruction": "B", 
		"agreedFx": 0.5 ,
		"currency": "SGP", 
		"instructionDate": "01 Jan 2016",
		"settlementDate": "03 Jan 2016",
		"units": 1000, 
		"pricePerUnit": 200.25
	}]	
}

