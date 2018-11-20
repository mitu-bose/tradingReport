# tradingReport

This is a trading report application.
The endpoint "http://localhost:8080/transactionReport" is used for displaying the report. This endpoint expects a post request.
The Sample Input for the request is:

	{
	"transactions": [{
		"entity": "foo",
		"instruction": "S",
		"agreedFx": 0.5,
		"currency": "SGP",
		"instructionDate": "01 Jan 2016",
		"settlementDate": "03 Jan 2016",
		"units": 200,
		"pricePerUnit": 100.25
	},
	{
		"entity": "Bar",
		"instruction": "B",
		"agreedFx": 0.5,
		"currency": "SGP",
		"instructionDate": "01 Jan 2016",
		"settlementDate": "03 Jan 2016",
		"units": 1000,
		"pricePerUnit": 200.25
	}]
	}

Response is generated in below format

{
    "dailySettlementList": {
        "04 Jan 2016": {
            "incomingAmount": 10025,
            "outgoingAmount": 100125
        }
    },
    "incomingEntityRankList": [
        {
            "rankNo": 1,
            "entity": "foo",
            "totalAmount": 10025
        }
    ],
    "outgoingEntityRankList": [
        {
            "rankNo": 1,
            "entity": "Bar",
            "totalAmount": 100125
        }
    ]
}
