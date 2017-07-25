import board.Board
import util.Helper
import web.WebRequest

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage

/**
  * Created by james on 13/07/17.
  */

package object Chess{
  val board: Board = new Board
  var primaryStage: PrimaryStage = _
}

object launch extends JFXApp{
  stage = new PrimaryStage{
    title.value = "Chess"
  }
  Chess.primaryStage = stage
  stage.scene = Chess.board.getDisplayScene

  new WebRequest get()
  new WebRequest post(Chess.board)
}
