package models

import play.api.libs.json.Json

case class  Task(id: Long, name: String, detail: String, done: Boolean)

object Task {
  implicit val taskFormat = Json.format[Task]
}
