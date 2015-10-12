name := """HelloJubatus"""

version := "1.0"

scalaVersion := "2.11.6"

resolvers ++= Seq(
  "Jubatus Repository for Maven" at "http://download.jubat.us/maven",
  "msgpack" at "http://msgpack.org/maven2/"
)

libraryDependencies ++= Seq(
  // Uncomment to use Akka
  //"com.typesafe.akka" % "akka-actor_2.11" % "2.3.9",
  //"junit"             % "junit"           % "4.12"  % "test",
  //"com.novocode"      % "junit-interface" % "0.11"  % "test",
  "org.msgpack"         % "msgpack-rpc"     % "0.7.0",
  "us.jubat"            % "jubatus"         % "[0.8,)"
)

fork in run := true