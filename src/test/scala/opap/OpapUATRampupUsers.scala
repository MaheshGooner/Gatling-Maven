package opap

import io.gatling.core.Predef._ 
import io.gatling.http.Predef._
import scala.concurrent.duration._

class OpapUATRampupUsers extends Simulation {

  val httpConf = http
    .baseURL("https://opap-uat.betstream.betgenius.com/betstream-view/page/opapgeniusbetinternaltest/desktop/home")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

  val scn = scenario("OpapBasicSimulation")
    .exec(http("Launch_OPAP")
    .get("/"))
    .pause(5)

  setUp(
  scn.inject(
    nothingFor(5 seconds),
    atOnceUsers(1000),
    rampUsers(500) over(5 seconds),
    constantUsersPerSec(10000) during(15 seconds),
    constantUsersPerSec(100) during(15 seconds) randomized,
    rampUsersPerSec(100) to 500 during(10 seconds),
    rampUsersPerSec(10) to 1000 during(20 seconds) randomized,
    splitUsers(1000) into(rampUsers(10) over(2 seconds)) separatedBy(1 second),
    splitUsers(1000) into(rampUsers(10) over(10 seconds)) separatedBy atOnceUsers(5),
    heavisideUsers(1000) over(10 seconds)
  ).protocols(httpConf)
)
}
