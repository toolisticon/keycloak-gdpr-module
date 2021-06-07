package crypt

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

import java.util.UUID

class OperationalSimulation extends Simulation {

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

  // ENCRYPTION && DECRYPTION
  val crypt = scenario("GDPR Encryption and Decryption")
      .exec(http("encrypt_small_text")
          .post("/encrypt")
          .headers(Map(
            "Accept" -> "application/json; q=0.01",
            "Content-Type" -> "application/json; charset=UTF-8"
          ))
          .body(StringBody(
            Util.jsonFromMap(Map(
              "userId" -> "User",
              "data" -> "Some Data"
            ))
          ))
          .check(jsonPath("$.cipherText").saveAs("cipherText_small"))
      )
      .exec(http("encrypt_big_text")
          .post("/encrypt")
          .headers(Map(
            "Accept" -> "application/json; q=0.01",
            "Content-Type" -> "application/json; charset=UTF-8"
          ))
          .body(StringBody(
            Util.jsonFromMap(Map(
              "userId" -> "User",
              "data" -> "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.   Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Nam liber tempor cum soluta nobis eleifend option congue nihil imperdiet doming id quod mazim placerat facer possim assum. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, At accusam aliquyam diam diam dolore dolores duo eirmod eos erat, et nonumy sed tempor et et invidunt justo labore Stet clita ea et gubergren, kasd magna no rebum. sanctus sea sed takimata ut vero voluptua. est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat. Consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Nam liber tempor cum soluta nobis eleifend option congue nihil imperdiet doming id quod mazim placerat facer possim assum. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo"
            ))
          ))
          .check(jsonPath("$.cipherText").saveAs("cipherText_big"))
      )
      .exec(http("decrypt_small_text")
          .post("/decrypt")
          .headers(Map(
            "Accept" -> "application/json; q=0.01",
            "Content-Type" -> "application/json; charset=UTF-8"
          ))
          .body(StringBody(
            Util.jsonFromMap(Map(
              "userId" -> "User",
              "cipherText" -> "${cipherText_small}"
            ))
          ))
      )
      .exec(http("decrypt_big_text")
          .post("/decrypt")
          .headers(Map(
            "Accept" -> "application/json; q=0.01",
            "Content-Type" -> "application/json; charset=UTF-8"
          ))
          .body(StringBody(
            Util.jsonFromMap(Map(
              "userId" -> "User",
              "cipherText" -> "${cipherText_big}"
            ))
          ))
      )

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
    crypt.inject(
          nothingFor(4.seconds),
          atOnceUsers(20),
          rampUsers(10).during(5.seconds), 
          constantUsersPerSec(10).during(15.seconds),
          constantUsersPerSec(10).during(15.seconds).randomized,
          rampUsersPerSec(5).to(10).during(4.minutes),
          rampUsersPerSec(5).to(10).during(5.minutes).randomized,
          heavisideUsers(30).during(20.seconds) 
      ).protocols(httpProtocol),
  )
}
