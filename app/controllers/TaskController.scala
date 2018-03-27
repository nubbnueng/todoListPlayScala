package controllers

import Repositories.TaskRepository
import javax.inject._
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class TaskController @Inject() (repo: TaskRepository,
                                cc: ControllerComponents,
                               )(implicit ec: ExecutionContext)
extends AbstractController (cc) {

  val createTaskForm: Form[CreateTaskForm] = Form {
    mapping(
      "name" -> nonEmptyText,
      "detail" -> nonEmptyText,
      "done" -> boolean
    )(CreateTaskForm.apply)(CreateTaskForm.unapply)
  }

  val updateTaskForm: Form[UpdateTaskForm] = Form {
    mapping(
      "name" -> optional(text),
      "detail" -> optional(text),
      "done" -> optional(boolean)
    )(UpdateTaskForm.apply)(UpdateTaskForm.unapply)
  }

  def index = Action.async {implicit request =>
    repo.list().map { people =>
      Ok(Json.toJson(people))
    }
  }

  def createTask = Action.async {implicit request =>
    createTaskForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(BadRequest("Invalid input!!"))
      },
      task => {
        repo.create(task.name, task.detail, task.done)
         // .map {_ =>  Redirect(routes.TaskController.index).flashing("success" -> "user.created")
          .map { t => Ok(Json.toJson(t))
        }
      }
    )
  }

  def updateTask(id: Long) = Action.async { implicit request =>
    updateTaskForm.bindFromRequest().fold(
      error => {
        Future.successful(BadRequest("Invalid input!!"))
      },
      task => {
        repo.update(id, task.name, task.detail, task.done).map{ t => Ok(Json.toJson(t))}
      })
  }
}

case class CreateTaskForm(name: String, detail: String, done: Boolean)
case class UpdateTaskForm(name: Option[String], detail: Option[String], done: Option[Boolean])
