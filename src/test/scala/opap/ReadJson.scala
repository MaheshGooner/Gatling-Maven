package opap

import java.net.URI

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import net.liftweb.json._


class ReadJson extends Simulation {

  val sentHeaders = Map("Content-Type" -> "application/json",
    "origin" -> "https://app.geniusbet-7442.uat.aws.betgenius.com",
    "pragma" -> "no-cache",
    "referer" -> "https://app.geniusbet-7442.uat.aws.betgenius.com/web/xdomain/proxy.html",
    "user-agent" -> "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.119 Safari/537.36")

  //val jsonBody: String = "{'id':'8ae0a3df-6a81-4cdf-93ec-b632a4a17673','clientId':'1-10','channelId':0,'ipAddress':'1.1.1.1','requestGuid':'REQ-ba2f45b2-79fd-4c44-a29d-f9d6f71097bd','singleBets':[{'leg':{'legId':1,'selection':{'fixedMarketId':'227044','eventId':'60586','outcomeId':'2','marketTypeCode':'WIN','dividendTypeCode':'FIXED','prices':[{'value':'1.92000000','priceType':'WIN'}],'points':0}},'totalStake':0.5,'interceptId':0,'walletAccountId':'AT_MAIN-0','betReferenceGuid':'63e3a65c-f3b6-4ce4-b271-c0c2c512e4e5','bonus':{'freebetId':0,'walletBonusId':0,'isBonusBet':false}}],'multiBets':[]}"
  var request: String = null
  var mt: String = null

  def getMarketData() {
    var uri: URI = URI.create("https://opap-uat.betstream.betgenius.com/betstream-view/getMarkets/source/7442.json")
    var tokener = scala.io.Source.fromURL(uri.toURL()).mkString
    val json = parse(tokener)

    val marketTypeCode = (json \ "marketType").get
    val sourceSelectionId = (json \ "marketSelections").get
    val bgFixtureId = (json \ "betgeniusFixtureId").get
    val sportName = (json \ "sport").get
    val marketId = (json \ "sourceMarketId").get

    /*mt = markettype.toString
    println("betgeniusFixtureId count------->" + mt.size + " First element--->" + markettype.apply(0).toString)
    */
    for (i <- 0 to marketTypeCode.toString.size) {
      if (marketTypeCode.apply(i) == (JInt(322))) {
        val JInt(betgeniusId) = bgFixtureId.apply(i)
        val JInt(marketType) = marketTypeCode.apply(i)
        val JString(marketIdCode) = marketId.apply(i)
        val JString(sourceSelectioId) = sourceSelectionId.apply(i).\("sourceSelectionId").apply(1)
        val JDouble(stake) = sourceSelectionId.apply(i).\("price").\("decimalPrice").apply(1)
        val JString(sport) = sportName.apply(i)
        println("betGeniusFixtureID ------------->" + betgeniusId)
        println("Market Type found  -------------> " + marketType)
        println("Market ID  -------------> " + marketIdCode)
        println("source selection id ------------> " + sourceSelectioId.split("-").apply(1))
        println("stake -------------------------->" + stake)
        println("sport -------------------------->" + sport)
        request = s"{'id':'8ae0a3df-6a81-4cdf-93ec-b632a4a17673','clientId':'1-10','channelId':0,'ipAddress':'1.1.1.1','requestGuid':'REQ-ba2f45b2-79fd-4c44-a29d-f9d6f71097bd','singleBets':[{'leg':{'legId':1,'selection':{'fixedMarketId':'${sourceSelectioId.split("-").apply(1)}','eventId':'$marketIdCode','outcomeId':'1','marketTypeCode':'WIN','dividendTypeCode':'FIXED','prices':[{'value':'$stake','priceType':'WIN'}],'points':0}},'totalStake':0.25,'interceptId':0,'walletAccountId':'AT_MAIN-0','betReferenceGuid':'63e3a65c-f3b6-4ce4-b271-c0c2c512e4e5','bonus':{'freebetId':0,'walletBonusId':0,'isBonusBet':false}}],'multiBets':[]}"
        println("request Body " + request)
        return
      }
    }


  }

  getMarketData()

  val scn = scenario("Place Bets")
    .exec(http("Place Bet")
      .post("https://app.geniusbet-7442.uat.aws.betgenius.com/tbs.betting.api/api/v1/sportsbook/clients/1-925967590/bets")
      .headers(sentHeaders)
      .body(StringBody(request)).asJSON
      .check(status is(200))
      .check(regex("ticketNumber").exists)
    )

  setUp(
    scn.inject(atOnceUsers(25))
  )

}
