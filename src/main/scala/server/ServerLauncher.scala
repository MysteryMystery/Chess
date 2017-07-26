package server

import java.io.{File, InputStream}
import java.nio.file.Files

import collection.JavaConverters._
import sys.process._

class ServerLauncher {
  copyServerFileToDir
  var launchStatus = "ruby server/Server.rb" !

  println(s"Server Launch Status: $launchStatus")

  def getServerFile: InputStream = getClass.getResourceAsStream("/Server.rb")

  def copyServerFileToDir = {
    if (!Files.exists(new File("server").toPath)){
      Files.createDirectory(new File("server").toPath)
    }
    if (!Files.exists(new File("server/Server.rb").toPath)){
      Files.copy(getServerFile, new File("server/Server.rb").toPath)
    }
  }
}
