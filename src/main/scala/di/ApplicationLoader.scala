package di

import _root_.controllers.{AssetsComponents, HomeController}
import play.api.ApplicationLoader.Context
import play.api.mvc.EssentialFilter
import play.api.routing.Router
import play.api.routing.sird._
import play.api.{Application, BuiltInComponentsFromContext, LoggerConfigurator}

class ApplicationLoader extends play.api.ApplicationLoader {
  def load(context: Context): Application = {
    LoggerConfigurator(context.environment.classLoader).foreach {
      _.configure(context.environment, context.initialConfiguration, Map.empty)
    }
    new ApplicationComponents(context).application
  }
}

class ApplicationComponents(context: Context)
  extends BuiltInComponentsFromContext(context)
    with AssetsComponents {
  lazy val router: Router = Router.from {
    case GET(p"/api/v1") => new HomeController(controllerComponents).index()
    case POST(p"/api/v1") => new HomeController(controllerComponents).test()

    case GET(p"/") => assets.at("index.html")
    case GET(p"$file*") => assets.at(file)
  }

  override def httpFilters: Seq[EssentialFilter] = Seq.empty
}
