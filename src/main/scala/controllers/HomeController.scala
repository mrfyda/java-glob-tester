package controllers

import java.nio.file.{FileSystem, FileSystems}

import play.api.libs.json._
import play.api.mvc._

case class GlobTestRequest(pattern: String, path: String)

object GlobTestRequest {
  implicit val format: OFormat[GlobTestRequest] = Json.format[GlobTestRequest]
}

class HomeController(cc: ControllerComponents) extends AbstractController(cc) {

  lazy val fs: FileSystem = FileSystems.getDefault

  def index() = Action {
    Ok("java-glob-tester")
  }

  def test(): Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[GlobTestRequest] match {
      case JsError(errors) =>
        BadRequest(errors.toString)
      case JsSuccess(GlobTestRequest(pattern, path), _) =>
        val matcher = fs.getPathMatcher(s"glob:$pattern")
        val result = matcher.matches(fs.getPath(path))
        Ok(Json.toJson(result))
    }
  }

}
