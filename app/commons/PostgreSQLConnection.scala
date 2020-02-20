package commons

import com.typesafe.config.{Config, ConfigFactory}
import slick.jdbc.PostgresProfile.api._

class PostgreSQLConnection {
  val config : Config = ConfigFactory.load().getConfig("postgresqldb")
  val url: String = config.getString("url")
  val user: String = config.getString("user")
  val password: String = config.getString("password")
  val driver: String = config.getString("driver")

  val dbConnectionURL = Database.forURL(url+"&"+user+"&"+password, driver)
}
