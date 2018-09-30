package controllers

import di.ApplicationComponents
import org.scalatestplus.play._
import org.scalatestplus.play.components.OneAppPerTestWithComponents
import play.api.BuiltInComponents
import play.api.test._
import play.api.test.Helpers._

class HomeControllerSpec extends PlaySpec with OneAppPerTestWithComponents {

  override def components: BuiltInComponents = new ApplicationComponents(context)

  "HomeController GET" should {

    "render the index page from a new instance of controller" in {
      val controller = new HomeController(stubControllerComponents())
      val home = controller.index().apply(FakeRequest(GET, "/"))

      status(home) mustBe OK
      contentType(home) mustBe Some("text/plain")
      contentAsString(home) must include("java-glob-tester")
    }

    "render the index page from the router" in {
      val request = FakeRequest(GET, "/")
      val home = route(app, request).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/plain")
      contentAsString(home) must include("java-glob-tester")
    }
  }

}
