package crypt

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class BasicSimulation extends Simulation {

  val httpProtocol = http
      .baseUrl("http://localhost:8888/auth/realms/master/gdpr") // Here is the root for all relative URLs

  // ENCRYPTION
  val encrypt = scenario("GDPR Encryption")
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
      )


  // DECRYPTION
  val decrypt = scenario("GDPR Decryption")
      .exec(http("decrypt_small_text")
          .post("/decrypt")
          .headers(Map(
            "Accept" -> "application/json; q=0.01",
            "Content-Type" -> "application/json; charset=UTF-8"
          ))
          .body(StringBody(
            Util.jsonFromMap(Map(
              "userId" -> "User",
              "cipherText" -> "FyenrobGPAYFXZdAiNRa6AI+PXX05dGpZg=="
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
              "cipherText" -> "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"
            ))
          ))
      )

  // RUN test model
  setUp(
    encrypt.inject(
          nothingFor(4.seconds),
          atOnceUsers(20),
          rampUsers(10).during(5.seconds), 
          constantUsersPerSec(10).during(15.seconds),
          constantUsersPerSec(10).during(15.seconds).randomized,
          rampUsersPerSec(5).to(10).during(4.minutes),
          rampUsersPerSec(5).to(10).during(5.minutes).randomized,
          heavisideUsers(30).during(20.seconds) 
      ).protocols(httpProtocol),
    decrypt.inject(
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
