package opap

import java.net.URI

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import net.liftweb.json._


class HttpPlaceBet extends Simulation{

  val sentHeaders = Map("Content-Type" -> "application/json",
                        "origin" -> "https://app.geniusbet-7442.uat.aws.betgenius.com",
                        "pragma" -> "no-cache",
                        "referer" -> "https://app.geniusbet-7442.uat.aws.betgenius.com/web/xdomain/proxy.html",
                        "user-agent" -> "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.119 Safari/537.36")

  val jsonBody : String = "{'id':'8ae0a3df-6a81-4cdf-93ec-b632a4a17673','clientId':'1-10','channelId':0,'ipAddress':'1.1.1.1','requestGuid':'REQ-ba2f45b2-79fd-4c44-a29d-f9d6f71097bd','singleBets':[{'leg':{'legId':1,'selection':{'fixedMarketId':'227044','eventId':'60586','outcomeId':'2','marketTypeCode':'WIN','dividendTypeCode':'FIXED','prices':[{'value':'1.92000000','priceType':'WIN'}],'points':0}},'totalStake':0.5,'interceptId':0,'walletAccountId':'AT_MAIN-0','betReferenceGuid':'63e3a65c-f3b6-4ce4-b271-c0c2c512e4e5','bonus':{'freebetId':0,'walletBonusId':0,'isBonusBet':false}}],'multiBets':[]}"

  val scn = scenario("OpapHitsPerSecond")
            .exec(http("Place Bet")
              .post("https://app.geniusbet-7442.uat.aws.betgenius.com/tbs.betting.api/api/v1/sportsbook/clients/1-925967590/bets")
              .headers(sentHeaders)
              .body(StringBody(jsonBody)).asJSON
              .check(status.is(200))
            )
/*

  def getMarketData(){
    var uri: URI = URI.create("https://opap-uat.betstream.betgenius.com/betstream-view/getMarkets/source/7442.json")
    var tokener = scala.io.Source.fromURL(uri.toURL()).mkString
    val mailAccountString ="""
val json = parse(mailAccountString)

    for(data <- json){tokener
      //println("BetgeniusFixtureId's ---------------->"+data.\("betgeniusFixtureId").values)
      //val list = List[String] = data.\("betgeniusFixtureId").values
      //println("Size --------->"+Seq(data.\("betgeniusFixtureId").values))
      val m = data.extract[MarketSelections]
      println("betgeniusFixtureId ------->"+m.betgeniusFixtureId)
    }


    /*val elements = (json \\ "marketSelections").children
    for ( acct <- elements ) {
      val m = acct.\("sourceSelectionId")
      //extract[MarketSelections]
      println("sourceSelectionId "+m)

    }*/

  }

  getMarketData()
*/

  setUp(
    scn.inject(atOnceUsers(1))
  )

}
