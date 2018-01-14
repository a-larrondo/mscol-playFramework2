import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "Scol"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
      // Add your project dependencies here,
        "mysql" % "mysql-connector-java" % "5.1.22",
        "com.itextpdf" % "itextpdf" % "5.5.2",
        javaCore,
    	javaJdbc,
    	jdbc,
  		cache,
    	javaEbean,
    	"com.typesafe.play" %% "play-jdbc" % "2.2.0" exclude("com.jolbox", "bonecp")   	
        
    	
    )

 val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here
    //"net.sf.jasperreports" % "jasperreports" % "5.5.0",
    //    "com.lowagie" % "itext" % "4.2.1",
    //  	"com.jolbox" % "bonecp" % "0.8.0.RELEASE"      
  )

}
