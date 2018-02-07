package opap

case class MarketSelections(

betgeniusFixtureId: String,
source: String,
sourceName: String,
sourceMarketId: String,
sourceFixtureId: String,
sport: String,
marketType: String,
description: String,
expiry: String,
suspended: String,
available: String,
lastUpdated: String,
marketSelections: List[Array[String]],
metadata: List[Array[String]],
marketLinkInfo: String,
eachWayFactor: String,
eachWayPlaces: String
                           
/*
  sourceSelectionId: String,
  betgeniusSelectionId: String,
  name: String,
  price: List[String],
  lastUpdated: String,
  handicap: String,
  outcome: String,
  metadata: List[String],
  CashOutProbability: String,
  selectionLinkInfo: String,
  suspended: String,
  available: String*/
)
