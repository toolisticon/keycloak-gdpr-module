package crypt

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

import java.util.UUID

class NormalSimulation extends Simulation {

  val client_id = "gatlin"
  val realm = "app"

  val headers_http = Map(
    "Accept" -> """application/json"""
  )

  val headers_kc = Map(
    "Accept" -> """application/json""",
   // "X-XSRF-TOKEN" -> "${xsrf_token}"
  )
  val headers_http_authenticated = Map(
    "Accept" -> """application/json""",
    "Authorization" -> "Bearer ${access_token}"
  )
  val keycloakHeaders = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
    "Upgrade-Insecure-Requests" -> "1"
  )

  val authUri = "http://localhost:8888/auth/realms/" + realm + "/protocol/openid-connect/auth"

  val tokenUri = "http://localhost:8888/auth/realms/" + realm + "/protocol/openid-connect/token"

  val accountUri = "http://localhost:8888/auth/realms/" + realm + "/account/"

  val keycloak = scenario("OAuth Login")
      .exec(http("First unauthenticated request")
          .get(accountUri)
                .headers(headers_http)
          .check(status.is(401))
          //.check(headerRegex("Set-Cookie", "XSRF-TOKEN=(.*);[\\s]").saveAs("xsrf_token"))
        ).exitHereIfFailed
        .pause(10)
      .exec(http("Authentication")
          .get(accountUri)
          .check(status.is(302))
          .check(header("Location").saveAs("loginUrl"))).exitHereIfFailed
      .pause(2)
      .exec(http("Login Redirect")
          .get("${loginUrl}")
          .silent
          .headers(keycloakHeaders)
          .check(css("#kc-form-login", "action").saveAs("kc-form-login"))).exitHereIfFailed
      .pause(10)
      .exec(http("Authenticate")
          .post("${kc-form-login}")
          .silent
          .headers(keycloakHeaders)
          .formParam("username", "user")
          .formParam("password", "user")
          .formParam("submit", "Login")
          .check(status.is(302))
          .check(header("Location").saveAs("afterLoginUrl"))).exitHereIfFailed
      .pause(2)
      .exec(http("After Login Redirect")
          .get("${afterLoginUrl}")
          .silent
          .check(status.is(302))
          .check(header("Location").saveAs("finalRedirectUrl"))
        //  .check(headerRegex("Set-Cookie", "XSRF-TOKEN=(.*);[\\s]").saveAs("xsrf_token"))
        )
      .exec(http("Open Account Page")
          .get(accountUri)
          .check(status.is(200))).exitHereIfFailed

  val httpProtocol = http
      .baseUrl("http://localhost:8888/auth/realms/master/gdpr") // Here is the root for all relative URLs
      .inferHtmlResources()
      .acceptHeader("*/*")
      .acceptEncodingHeader("gzip, deflate")
      .acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
      .connectionHeader("keep-alive")
      .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:33.0) Gecko/20100101 Firefox/33.0")
      .silentResources // Silence all resources like css or css so they don't clutter the results

  // RUN test model
  setUp(
    keycloak.inject(
            nothingFor(10.seconds), // Pause for a given duration.
            atOnceUsers(10), // Injects a given number of users at once.
            rampUsers(10).during(5.seconds), // Injects a given number of users distributed evenly on a time window of a given duration.
            constantUsersPerSec(20).during(15.seconds), // Injects users at a constant rate, defined in users per second, during a given duration. Users will be injected at regular intervals.
            constantUsersPerSec(20).during(15.seconds).randomized, // Injects users at a constant rate, defined in users per second, during a given duration. Users will be injected at randomized intervals.
            rampUsersPerSec(10).to(20).during(5.minutes), // Injects users from starting rate to target rate, defined in users per second, during a given duration. Users will be injected at regular intervals.
            rampUsersPerSec(10).to(20).during(5.minutes).randomized, // Injects users from starting rate to target rate, defined in users per second, during a given duration. Users will be injected at randomized intervals.
            heavisideUsers(10).during(20.seconds) // Injects a given number of users following a smooth approximation of the heaviside step function stretched to a given duration (seconds)
        ).protocols(httpProtocol
        .inferHtmlResources()
        .disableFollowRedirect // We must follow redirects manually to get the xsrf token from the keycloak redirect
        .disableAutoReferer
    ),
  )
}
