name := "scalaj-time"

organization := "org.scalaj"

version := "0.8"

crossScalaVersions := Seq("2.10.6", "2.11.8", "2.12.1")

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

scalacOptions ++= {
  Seq("-deprecation", "-unchecked", "-feature", "-language:implicitConversions", "-language:higherKinds")
}

scalacOptions ++= {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, 12)) => Seq()
    case _ => Seq("-target:jvm-1.7")
  }
}

libraryDependencies ++= Seq(
  "joda-time" % "joda-time" % "2.2",
  "org.joda" % "joda-convert" % "1.2"
)

publishTo <<= version { (v: String) =>
  val nexus = "https://oss.sonatype.org/"
  if (v.endsWith("-SNAPSHOT"))
    Some("snapshots" at nexus+"content/repositories/snapshots")
  else
    Some("releases" at nexus+"service/local/staging/deploy/maven2")
}

credentials ++= {
  val sonatype = ("Sonatype Nexus Repository Manager", "oss.sonatype.org")
  def loadMavenCredentials(file: java.io.File) : Seq[Credentials] = {
    xml.XML.loadFile(file) \ "servers" \ "server" map (s => {
      val host = (s \ "id").text
      val realm = if (host == sonatype._2) sonatype._1 else "Unknown"
      Credentials(realm, host, (s \ "username").text, (s \ "password").text)
    })
  }
  val ivyCredentials   = Path.userHome / ".ivy2" / ".credentials"
  val mavenCredentials = Path.userHome / ".m2"   / "settings.xml"
  (ivyCredentials.asFile, mavenCredentials.asFile) match {
    case (ivy, _) if ivy.canRead => Credentials(ivy) :: Nil
    case (_, mvn) if mvn.canRead => loadMavenCredentials(mvn)
    case _ => Nil
  }
}

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <url>https://github.com/scalaj/scalaj-time</url>
  <licenses>
    <license>
      <name>Apache</name>
      <url>http://www.opensource.org/licenses/Apache-2.0</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:scalaj/scalaj-time.git</url>
    <connection>scm:git:git@github.com:scalaj/scalaj-time.git</connection>
  </scm>
  <developers>
    <developer>
      <id>jorgeortiz85</id>
      <name>Jorge Ortiz</name>
      <url>http://github.com/jorgeortiz85</url>
    </developer>
  </developers>)
