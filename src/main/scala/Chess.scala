import board.Board
import server.ServerLauncher
import util.Helper
import web.WebRequest

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import sys.process._

/**
  * Created by james on 13/07/17.
  */

package object Chess{
  val board: Board = new Board
  var primaryStage: PrimaryStage = _
}

object launch extends JFXApp{
  new Thread(){
    override def run(): Unit = new ServerLauncher
  } start()

  stage = new PrimaryStage{
    title.value = "Chess"
  }
  Chess.primaryStage = stage
  stage.scene = Chess.board.getDisplayScene
}
