import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration.DurationInt
import scala.util.Random


class CustomerSimulation extends Simulation {

  val random = new Random()


  val httpConf: HttpProtocolBuilder = http.baseURL("http://localhost:8080")

  var customerScenario = scenario("Customer Scenario").repeat(10000) {
    exec(
      http("new customer")
        .post("/customer")
        .header(HttpHeaderNames.ContentType, HttpHeaderValues.ApplicationJson)
        .body(StringBody(
          s"""
             |{
             | "name": "${generateName()}"
             |}
        """.stripMargin))).pause(1.second, 5.seconds)
  }

  var transactionScenario = scenario("Transaction Scenario").repeat(10000) {
    exec(
      http("new transaction")
        .post("/transaction")
        .header(HttpHeaderNames.ContentType, HttpHeaderValues.ApplicationJson)
        .body(StringBody( session =>
          s"""
             |{
             | "from": 111,
             | "to": 222,
             | "amount": ${randomAmount()}
             |}
        """.stripMargin))
        .check(status.in(201, 400))
    ).pause(2.milliseconds, 500.milliseconds)
  }

  def generateName(): String = {
    random.alphanumeric take 10 mkString
  }

  def randomAmount(): Int = {
    random.nextInt(5000)
  }

  def randomId(): Int = {
    random.nextInt(Int.MaxValue)
  }

  setUp(
    customerScenario.inject(atOnceUsers(5)),
    transactionScenario.inject(atOnceUsers(50))
  ).protocols(httpConf)


}
