package opap

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._

class PlaceBet extends Simulation {

  private val baseUrl = "https://app.geniusbet-7442.uat.aws.betgenius.com"
  private val requestCount = 1000
  private val endpoint = "/tbs.betting.api/api/v1/sportsbook/clients/1-925967590/bets"


  val httpProtocol: HttpProtocolBuilder = http
        .baseURL(baseUrl)
        .header("Content-Type","application/json")
        .header("application/json","https://app.geniusbet-7442.uat.aws.betgenius.com")
        .header("pragma","no-cache")
        .header("referer","https://app.geniusbet-7442.uat.aws.betgenius.com/web/xdomain/proxy.html")
        .header("user-agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.119 Safari/537.36")
        //.contentTypeHeader("application/json")
        //.userAgentHeader("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.119 Safari/537.36")

  val scn: ScenarioBuilder = scenario("BetAPI")
    .exec(http("PlaceBet")
      .get(endpoint)
      .body(StringBody("{'id':'8ae0a3df-6a81-4cdf-93ec-b632a4a17673','clientId':'1-10','channelId':0,'ipAddress':'1.1.1.1','requestGuid':'REQ-ba2f45b2-79fd-4c44-a29d-f9d6f71097bd','singleBets':[{'leg':{'legId':1,'selection':{'fixedMarketId':'197298','eventId':'51113','outcomeId':'2','marketTypeCode':'WIN','dividendTypeCode':'FIXED','prices':[{'value':'2.04000000','priceType':'WIN'}],'points':0}},'totalStake':0.5,'interceptId':0,'walletAccountId':'AT_MAIN-0','betReferenceGuid':'63e3a65c-f3b6-4ce4-b271-c0c2c512e4e5','bonus':{'freebetId':0,'walletBonusId':0,'isBonusBet':false}}],'multiBets':[]}"))
      //.body("{'id':'8ae0a3df-6a81-4cdf-93ec-b632a4a17673','clientId':'1-10','channelId':0,'ipAddress':'1.1.1.1','requestGuid':'REQ-ba2f45b2-79fd-4c44-a29d-f9d6f71097bd','singleBets':[{'leg':{'legId':1,'selection':{'fixedMarketId':'197298','eventId':'51113','outcomeId':'2','marketTypeCode':'WIN','dividendTypeCode':'FIXED','prices':[{'value':'2.04000000','priceType':'WIN'}],'points':0}},'totalStake':0.5,'interceptId':0,'walletAccountId':'AT_MAIN-0','betReferenceGuid':'63e3a65c-f3b6-4ce4-b271-c0c2c512e4e5','bonus':{'freebetId':0,'walletBonusId':0,'isBonusBet':false}}],'multiBets':[]}")
      .check(status.is(200)))
  setUp(
    scn.inject(constantUsersPerSec(1) during(1 seconds))
  ).protocols(httpProtocol)
}
