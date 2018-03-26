package controllers

import Repositories.TaskRepository

import javax.inject._

import models._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.i18n._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class TaskController @Inject() (repo: TaskRepository,
                                cc: ControllerComponents,
                               )(implicit ec: ExecutionContext)
extends AbstractController (cc) {

  val taskForm: Form[CreateTaskForm] = Form {
    mapping(
      "name" -> nonEmptyText,
      "detail" -> nonEmptyText,
      "done" -> boolean
    )(CreateTaskForm.apply)(CreateTaskForm.unapply)
  }

  def index = Action.async {implicit request =>
    repo.list().map { people =>
      Ok(Json.toJson(people))
    }
  }

  def createTask = Action.async {implicit request =>
    taskForm.bindFromRequest.fold(
      errorForm => {
        Future.successful(Ok("Invalid inut!!"))
      },
      task => {
        repo.create(task.name, task.detail, task.done).map {_ =>
          Redirect(routes.TaskController.index).flashing("success" -> "user.created")
        }
      }
    )
  }

}

case class CreateTaskForm(name: String, detail: String, done: Boolean)
