package piece

import util.{Colours, Helper}
import Helper._
import Colours._

import collection.JavaConverters._

/**
  * Created by james on 13/07/17.
  */
class Pawn(override val colour: Char, override var cell: String) extends Piece{
  var moveCount = 0

  override protected def isValidMove(from: String, to: String): Boolean = {
    var diff = if (colour == Colours.white) Helper.letterToNum(to.charAt(1)) - Helper.letterToNum(from.charAt(1)) else Helper.letterToNum(from.charAt(1)) - Helper.letterToNum(to.charAt(1))
    if ((((moveCount == 0 && diff == 2) || diff == 1 ) && !pieceInFront) || (pieceDiagRight && to == getCoordDiagRight) || (pieceDiagLeft && to== getCoordDiagLeft)) {
      moveCount += 1
      true
    }else{
      false
    }
  }

  def pieceInFront: Boolean = {
    val addrInFront = if (colour == white) s"${cell.charAt(0)}${Integer.parseInt(cell.charAt(1).toString)+1}" else s"${cell.charAt(0)}${Integer.parseInt(cell.charAt(1).toString)-1}"
    Chess.board.getPieceAt(addrInFront) != null
  }

  def pieceDiagRight: Boolean = {
    false
  }

  def pieceDiagLeft: Boolean = {
    false
  }

  def getCoordDiagRight: String = {
    if (colour == white) s"${Helper.numToLetter(Helper.letterToNum(cell.charAt(0)) + 2)}${Integer.parseInt(cell.charAt(1).toString)+1}" else s"${Helper.numToLetter(Helper.letterToNum(cell.charAt(0)) + 2)}${Integer.parseInt(cell.charAt(1).toString)-1}"
  }

  def getCoordDiagLeft: String = {
    ""
  }
}
