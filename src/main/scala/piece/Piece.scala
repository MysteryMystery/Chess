package piece

/**
  * Created by james on 13/07/17.
  */
@SerialVersionUID(123L)
trait Piece extends Serializable{
  val colour: Char
  var cell: String

  def moveTo(coordinate: String): Boolean = {
    if(isValidMove(getCurrentPosition, coordinate)){
      cell = coordinate
      true
    }else{
      false
    }
  }

  protected def isValidMove(from: String, to: String): Boolean

  def getCurrentPosition: String = cell

  protected def take: Boolean = {
    if (Chess.board.twoPiecesInSameCell(cell) != (null, null)){
      //remove the piece of opposing colour
      true
    }else{
      false
    }
  }
}
