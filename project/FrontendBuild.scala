import java.net.InetSocketAddress

import play.sbt.PlayRunHook
import sbt._

import scala.sys.process.Process

/**
  * Frontend build play run hook.
  * https://www.playframework.com/documentation/2.6.x/SBTCookbook
  */
object FrontendBuild {
  def apply(base: File): PlayRunHook = {
    object UIBuildHook extends PlayRunHook {

      var process: Option[Process] = None

      var yarnInstall: String = FrontendCommands.dependencyInstall
      var yarnBuild: String = FrontendCommands.build

      // Windows requires yarn commands prefixed with cmd /c
      if (System.getProperty("os.name").toLowerCase().contains("win")){
        yarnInstall = "cmd /c" + yarnInstall
        yarnBuild = "cmd /c" + yarnBuild
      }

      /**
        * Executed before play run start.
        * Run yarn install if node modules are not installed.
        */
      override def beforeStarted(): Unit = {
        if (!(base / "ui" / "node_modules").exists()) Process(yarnInstall, base / "ui").!
      }

      /**
        * Executed after play run start.
        * Run yarn start
        */
      override def afterStarted(addr: InetSocketAddress): Unit = {
        process = Option(
          Process(yarnBuild, base / "ui").run
        )
      }

      /**
        * Executed after play run stop.
        * Cleanup frontend execution processes.
        */
      override def afterStopped(): Unit = {
        process.foreach(_.destroy())
        process = None
      }

    }

    UIBuildHook
  }
}
