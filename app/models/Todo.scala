package models

import play.api.libs.json.{JsPath, Writes}
import play.api.libs.functional.syntax._

object Todo {
  implicit val todoWrites: Writes[Todo] = (
    (JsPath \ "name").write[String] and
      (JsPath \ "detail").write[String] and
      (JsPath \ "done").write[Boolean]
    )(unlift(Todo.unapply))
}

case class Todo(name: String, detail: String, done: Boolean)
