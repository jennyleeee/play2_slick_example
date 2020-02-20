package controllers

import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object MainController  {


  case class screens(id: Long, base_app_id: Long, version_id: Long, version_name: String,
                     app_id: Long, screen_key:String, scroll_view_key: String,
                     scroll_key: String, alias_id: Long , portrait_screenshot_id: Long,
                     landscape_screenshot_id: Long, scroll_screenshot_id: Long, favorite: Boolean,
                     screen_id: Long)

  class ScreensTable(tag: Tag) extends Table[screens](tag, "userhabit_screens") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def base_app_id = column[Long]("base_app_id")
    def version_id = column[Long]("version_id")
    def version_name = column[String]("version_name")
    def app_id = column[Long]("app_id")
    def screen_key = column[String]("screen_key")
    def scroll_view_key = column[String]("scroll_view_key")
    def scroll_key = column[String]("scroll_key")
    def alias_id = column[Long]("alias_id")
    def portrait_screenshot_id = column[Long] ("portrait_screenshot_id")
    def landscape_screenshot_id = column[Long] ("landscape_screenshot_id")
    def scroll_screenshot_id = column[Long] ("scroll_screenshot_id")
    def favorite = column[Boolean]("favorite")
    def screen_id = column[Long]("screen_id")
    override def * = (id, base_app_id, version_id, version_name,
      app_id, screen_key, scroll_view_key, scroll_key, alias_id, portrait_screenshot_id,
      landscape_screenshot_id, scroll_screenshot_id, favorite, screen_id) <> ((screens.apply _).tupled, screens.unapply)
  }

  //outer join with ScreenAliases

  case class screenAliases(id: Long,base_app_id:Long, screen_key:String, alias_name:String,
                           created_at: String, updated_at: String, category_id:Long, need_collect:Boolean,
                           favorite: Boolean)

  class ScreensAliasTable(tag: Tag) extends Table[screenAliases](tag, "screen_aliases") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def base_app_id = column[Long]("base_app_id")
    def screen_key = column[String]("screen_key")
    def alias_name = column[String]("alias_name",O.Default(""))
    def created_at = column[String]("created_at")
    def updated_at = column[String]("updated_at")
    def category_id = column[Long]("category_id")
    def need_collect = column[Boolean]("need_collect")
    def favorite = column[Boolean]("favorite")
    override def * = (id, base_app_id, screen_key, alias_name,
      created_at, updated_at, category_id, need_collect, favorite) <> ((screenAliases.apply _).tupled, screenAliases.unapply)
  }

  //outer join with ScreenShot
  case class screenShots(id: Long = 0L, base_app_id: Long, screenshot_file_name: String,
                         screenshot_content_type: String, screenshot_file_size: Int,
                         screenshot_updated_at: String, created_at: String,
                         updated_at: String)

  class ScreenShotTable(tag: Tag) extends Table[screenShots](tag, "screenshots") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def base_app_id = column[Long]("base_app_id")
    def screenshot_file_name = column[String]("screenshot_file_name")
    def screenshot_content_type = column[String]("screenshot_content_type")
    def screenshot_file_size = column[Int]("screenshot_file_size")
    def screenshot_updated_at = column[String]("screenshot_updated_at")
    def created_at = column[String]("created_at")
    def updated_at = column[String]("updated_at")
    override def * = (id, base_app_id, screenshot_file_name, screenshot_content_type, screenshot_file_size, screenshot_updated_at, created_at, updated_at) <> ((screenShots.apply _).tupled, screenShots.unapply)
  }


  def main(args: Array[String]): Unit = {

    lazy val db = Database.forConfig("postgresqldb")
    println("here@")

    try {

      val screens = TableQuery[ScreensTable]
      val aliases = TableQuery[ScreensAliasTable]
      val screenshots = TableQuery[ScreenShotTable]

      val screensIds = List[Long](3017219)
//        ((screens, aliases), screenshots) <- screens joinLeft aliases on (_.aliasId === _.id)joinLeft screenshots on (_._1.portraitScreenshotId === _.id)
//        if screens.id inSetBind screensIds
//      } yield (screens.versionId, screens.versionName, screens.screenKey, screens.aliasId, screens.portraitScreenshotId, screens.screenId, screens.favorite, aliases.map(_.aliasName), screenshots.map(_.screenshotFileName))


      val query = for {
        ((s, a), ss) <- screens joinLeft aliases on (_.alias_id === _.id) joinLeft screenshots on (_._1.portrait_screenshot_id === _.id)
        if s.screen_id inSetBind screensIds
      } yield (s.version_id.?, s.version_name.?, s.screen_key.?, s.alias_id.?, s.portrait_screenshot_id.?,
        s.screen_id.?, s.favorite.?, a.map(_.alias_name), ss.map(_.screenshot_file_name))

      val future = db.run(query.result)
      val result = Await.result(future, Duration.Inf)

//      val action = query.result

//      val result: Future[Seq[(Option[Long], Option[String], Option[String], Option[Long], Option[Long], Option[Long], Option[Boolean], Option[String], Option[String])]] = db.run(action)

        query.result.statements.foreach(println)

//        val futureResult = Await.result(result, Duration.Inf)
        //            futureResult.map {id => Logger.debug(s"id : ${id}")}
        print(result)
    }
    finally db.close()
  }
}

