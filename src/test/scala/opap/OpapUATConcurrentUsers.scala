package opap

import io.gatling.core.Predef._ 
import io.gatling.http.Predef._
import scala.concurrent.duration._

class OpapUATConcurrentUsers extends Simulation {

    val nbUsers = System.getProperty("users").toInt
    val myRamp  = java.lang.Long.getLong("ramp", 0L)

    println("Parameters from command line "+System.getProperty("users"))
    val httpConf = http
    .baseURL("https://opap.betstream.betgenius.com/betstream-view/page/opapgeniusbetexternaltest/desktop/home")
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
    scn.inject(splitUsers(nbUsers) into atOnceUsers(10) separatedBy(5 seconds))
  ).protocols(httpConf)
}
