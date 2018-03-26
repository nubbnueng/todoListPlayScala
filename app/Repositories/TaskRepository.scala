package Repositories

import javax.inject.{Inject, Singleton}
import models.Task
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContextExecutor, Future}

@Singleton
class TaskRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContextExecutor) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private class TaskTable(tag: Tag) extends Table[Task](tag, "task") {

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")
    def detail = column[String]("detail")
    def done = column[Boolean]("done")

    def * = (id, name, detail, done) <> ((Task.apply _).tupled, Task.unapply)
  }

  private val task = TableQuery[TaskTable]

  def list(): Future[Seq[Task]] = db.run {
    task.result
  }

  def create(name: String, detail: String, done: Boolean): Future[Task] = db.run {
    (task.map(t => (t.name, t.detail, t.done))
      returning task.map(_.id)
      into ((content, id) => Task(id, content._1, content._2, content._3))
      ) += (name, detail, done)
  }
}
