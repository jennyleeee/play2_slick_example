package repositories

import models.DatabaseModel
import slick.lifted.TableQuery
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

trait DatabaseRepository extends DatabaseModel{


  def getAllScreens(screenIds: List[Long]) : Unit = {
    val screensTable = TableQuery[ScreensTable]
    val screensAliasTable = TableQuery[ScreensAliasTable]
    val screenShotTable = TableQuery[ScreenShotTable]
    val db = Database.forConfig("postgresqldb")

    try {
      val query = screensTable.map(_.appId)
      val action = query.result
      val result: Future[Seq[Option[Long]]] = db.run(action)
      val futureResult = Await.result(result, Duration.Inf)
    }
    finally db.close()
  }
}
