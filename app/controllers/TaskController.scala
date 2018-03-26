package controllers

import Repositories.TaskRepository
import javax.inject.Inject
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class TaskController @Inject() (repo: TaskRepository,
                                cc: ControllerComponents,
                               )(implicit ec: ExecutionContext)
extends AbstractController (cc) {


}
