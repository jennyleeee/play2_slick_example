package models

import java.sql.Timestamp

import play.api.data.Form
import play.api.data.Forms.mapping
import slick.jdbc.PostgresProfile.api._

trait DatabaseModel {
  case class appsParameter (id: Long)

  case class screens(id: Long = 0L, baseAppId: Option[Long]= Some(0L), versionId: Option[Long] = Some(0L), versionName: Option[String] = Some(""),
                     appId: Option[Long] = Some(0L), screenKey: Option[String] = Some(""), scrollViewKey: Option[String] = Some(""),
                     scrollKey: Option[String] = Some(""), aliasId: Option[Long] = Some(0L) , portraitScreenshotId: Option[Long] = Some(0L),
                     landscapeScreenshotId: Option[Long] = Some(0L), scrollScreenshotId: Option[Long] = Some(0L), favorite: Option[Boolean] = Some(false),
                     screenId: Option[Long] = Some(0L))

  class ScreensTable(tag: Tag) extends Table[screens](tag, "userhabit_screens") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def baseAppId = column[Option[Long]]("base_app_id")
    def versionId = column[Option[Long]]("version_id")
    def versionName = column[Option[String]]("version_name")
    def appId = column[Option[Long]]("app_id")
    def screenKey = column[Option[String]]("screen_key")
    def scrollViewKey = column[Option[String]]("scroll_view_key")
    def scrollKey = column[Option[String]]("scroll_key")
    def aliasId = column[Option[Long]]("alias_id")
    def portraitScreenshotId = column[Option[Long]] ("portrait_screenshot_id")
    def landscapeScreenshotId = column[Option[Long]] ("landscape_screenshot_id")
    def scrollScreenshotId = column[Option[Long]] ("scroll_screenshot_id")
    def favorite = column[Option[Boolean]]("favorite")
    def screenId = column[Option[Long]]("screen_id")
    override def * = (id, baseAppId, versionId, versionName,
      appId, screenKey, scrollViewKey, scrollKey, aliasId, portraitScreenshotId,
      landscapeScreenshotId, scrollScreenshotId, favorite, screenId) <> ((screens.apply _).tupled, screens.unapply)
  }

  //outer join with ScreenAliases

  case class screenAliases(id: Long = 0L,baseAppId: Option[Long] = Some(0L), screenKey:Option[String] = Some(""), aliasName:Option[String] =Some(""),
                           createdAt: Option[String], updatedAt: Option[String], categoryId: Option[Long] = Some(0L), needCollect:Option[Boolean] = Some(false),
                           favorite: Option[Boolean] = Some(false))

  class ScreensAliasTable(tag: Tag) extends Table[screenAliases](tag, "screens") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def baseAppId = column[Option[Long]]("base_app_id")
    def screenKey = column[Option[String]]("screen_key")
    def aliasName = column[Option[String]]("alias_name")
    def createdAt = column[Option[String]]("created_at")
    def updatedAt = column[Option[String]]("updated_at")
    def categoryId = column[Option[Long]]("category_id")
    def needCollect = column[Option[Boolean]]("need_collect")
    def favorite = column[Option[Boolean]]("favorite")
    override def * = (id, baseAppId, screenKey, aliasName,
      createdAt, updatedAt, categoryId, needCollect, favorite) <> ((screenAliases.apply _).tupled, screenAliases.unapply)
  }

  //outer join with ScreenShot
  case class screenShots(id: Long = 0L, baseAppId: Option[Long] = Some(0L), screenshotFileName: Option[String] = Some(""),
                         screenshotContentType: Option[String] = Some(""), screenshotFileSize: Option[Int] = Some(0),
                         screenshotUpdatedAt: Option[String] = Some(""), createdAt: Option[String] = Some(""),
                         updateAt: Option[String] = Some(""))

  class ScreenShotTable(tag: Tag) extends Table[screenShots](tag, "screenshots") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def baseAppId = column[Option[Long]]("base_app_id")
    def screenshotFileName = column[Option[String]]("screenshot_file_name")
    def screenshotContentType = column[Option[String]]("screenshot_content_type")
    def screenshotFileSize = column[Option[Int]]("screenshot_file_size")
    def screenshotUpdatedAt = column[Option[String]]("screenshot_updated_at")
    def createdAt = column[Option[String]]("created_at")
    def updatedAt = column[Option[String]]("updated_at")
    override def * = (id, baseAppId, screenshotFileName, screenshotContentType, screenshotFileSize, screenshotUpdatedAt, createdAt, updatedAt) <> ((screenShots.apply _).tupled, screenShots.unapply)
  }

  case class appsList (list: Seq[apps] = Seq(apps()))
  case class apps(id: Long = 0L, name: Option[String] = Some(""), os_platform: Option[Long] = Some(0L), api_key: Option[String] = Some(""))

}
