package Repositories

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.ExecutionContextExecutor

@Singleton
class TaskRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContextExecutor) {

}
