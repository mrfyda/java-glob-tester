package controllers

import akka.stream.Materializer
import di.ApplicationComponents
import org.scalatestplus.play._
import org.scalatestplus.play.components.OneAppPerTestWithComponents
import play.api.BuiltInComponents
import play.api.libs.json.Json
import play.api.test._
import play.api.test.Helpers._

class HomeControllerSpec extends PlaySpec with OneAppPerTestWithComponents {

  override def components: BuiltInComponents = new ApplicationComponents(context)

  implicit lazy val materializer: Materializer = app.materializer

  "GET" should {

    "render the page from a new instance of controller" in {
      val controller = new HomeController(stubControllerComponents())
      val home = controller.index().apply(FakeRequest(GET, "/"))

      status(home) mustBe OK
      contentType(home) mustBe Some("text/plain")
      contentAsString(home) must include("java-glob-tester")
    }

    "render the page from the router" in {
      val request = FakeRequest(GET, "/")
      val home = route(app, request).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/plain")
      contentAsString(home) must include("java-glob-tester")
    }
  }

  "POST" should {

    "render the page from a new instance of controller" in {
      val controller = new HomeController(stubControllerComponents())
      val home = controller.test().apply(
        FakeRequest(POST, "/")
          .withBody(Json.toJson(GlobTestRequest("/app/**", "/app/Controller.scala")))
      )

      status(home) mustBe OK
      contentType(home) mustBe Some("application/json")
      contentAsString(home) must include("true")
    }

    "render the page from the router" in {
      val request = FakeRequest(POST, "/")
        .withBody(Json.toJson(GlobTestRequest("/app/**", "/app/Controller.scala")))
      val home = route(app, request).get

      status(home) mustBe OK
      contentType(home) mustBe Some("application/json")
      contentAsString(home) must include("true")
    }
  }

}
