package util

/**
  * Created by james on 13/07/17.
  */
object Helper {
  def translateCoordsToCellAddress(coords: String): (Int, Int) = {
    (letterToNum(coords.charAt(0)), letterToNum(coords.charAt(1)))
  }

  //Translates board coords to index e.g A1 == 0,0
  def letterToNum(s: Char): Int = {
    s match {
      case 'A' => 0
      case 'B' => 1
      case 'C' => 2
      case 'D' => 3
      case 'E' => 4
      case 'F' => 5
      case 'G' => 6
      case 'H' => 7
      case _ => Integer.parseInt(s.toString) - 1
    }
  }

  //1 == A etc
  def numToLetter(i: Int): String = {
    (64 + i).asInstanceOf[Char] toString
  }

}
