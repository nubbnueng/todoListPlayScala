package controllers

import javax.inject.{Inject, Singleton}
import models.Todo
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._

@Singleton
class TodoController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

//    def add = Action { request: Request[AnyContent] =>
//      val body: AnyContent = request.body
//      val jsonBody: Option[JsValue] = body.asJson
//
//      jsonBody.map { json =>
//        Ok("name: " + (json\"name").as[String])
//      }.getOrElse {
//        BadRequest("input body not a json")
//      }
//    }

  def add = Action(parse.json) {request: Request[JsValue] =>
    Ok("name: " + (request.body \ "name").as[String] + ", detail: " + (request.body \ "detail").as[String])
  }
}
