package piece

import collection.JavaConverters._

/**
  * Created by james on 14/07/17.
  */
class Queen(override val colour: Char, override var cell: String) extends Piece{

  override protected def isValidMove(from: String, to: String): Boolean = ???
}