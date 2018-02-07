package opap

import java.net.URI

import io.gatling.core.Predef._
import net.liftweb.json.{JsonAST, parse}

import scala.util.parsing.json.JSONArray

class ReadJson extends Simulation{

  def getMarketData(){
    var uri: URI = URI.create("https://opap-uat.betstream.betgenius.com/betstream-view/getMarkets/source/7442.json")
    var tokener = scala.io.Source.fromURL(uri.toURL()).mkString
    val json = parse(tokener)

   // println("Market Type "+(json.\("marketType")))
    val marketypes: JsonAST.JValue = (json.\("marketType"))
    for(mt <- (json.\("marketType"))){
      println("Values ---------->"+ Seq(marketypes))
    }

  }

  getMarketData()

}
