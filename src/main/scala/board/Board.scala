package board

import javafx.event.ActionEvent

import piece._

import collection.JavaConverters._
import scala.collection.mutable.ListBuffer
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.layout.{Background, GridPane, Pane}
import util.Colours._
import util.{Colours, Helper}

import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label, TextField}
import scalafx.scene.text.Text

/**
  * Created by james on 13/07/17.
  */
class Board {
  val pieces: ListBuffer[Piece] = ListBuffer[Piece]()
  val size = 8
  var board: Array[Array[String]] = Array[Array[String]]()

  val coordsFromEntry: TextField = new TextField(){
    id = "textEntry"
    promptText = "e.g. A1"
  }
  val coordsToEntry: TextField = new TextField(){
    id = "textEntry"
    promptText = "e.g. A2"
  }
  val submitButton: Button = new Button("Move!"){
    onAction = (event: ActionEvent) => {
      println(s"${coordsFromEntry.text.value} -> ${coordsToEntry.text.value}")
      val p = getPieceAt(coordsFromEntry.text.value)
      if (p != null && p.colour == Colours.white){
        if(p.moveTo(coordsToEntry.text.value)){
          refresh()
        }
        else{
          warningText.text = s"Moving to ${coordsFromEntry.text.value} is not valid!"
        }
      }else{
        warningText.text = s"Your piece is not at ${coordsFromEntry.text.value}"
      }
    }
  }
  val warningText: Text = new Text(){
    id = "warning"
  }
  refreshBoard
  placePieces

  private def refreshBoard: Unit = {
    var z: ListBuffer[ListBuffer[String]] = ListBuffer()
    for (x <- 0 until size){
      z += ListBuffer()
      for (y <- 0 until size){
        z(x) += ""
      }
    }
    board = z map((c) => c.toArray) toArray
  }

  private def placePieces: Unit = {
    //create each peice, add to list, no formatting required.
    pieces += new Pawn(white, "A2")
    pieces += new Pawn(white, "B2")
    pieces += new Pawn(white, "C2")
    pieces += new Pawn(white, "D2")
    pieces += new Pawn(white, "E2")
    pieces += new Pawn(white, "F2")
    pieces += new Pawn(white, "G2")
    pieces += new Pawn(white, "H2")

    pieces += new Castle(white, "A1")
    pieces += new Knight(white, "B1")
    pieces += new Bishop(white, "C1")
    pieces += new Queen(white, "E1")
    pieces += new King(white, "D1")
    pieces += new Bishop(white, "F1")
    pieces += new Knight(white, "G1")
    pieces += new Castle(white, "H1")

    pieces += new Pawn(black, "A7")
    pieces += new Pawn(black, "B7")
    pieces += new Pawn(black, "C7")
    pieces += new Pawn(black, "D7")
    pieces += new Pawn(black, "E7")
    pieces += new Pawn(black, "F7")
    pieces += new Pawn(black, "G7")
    pieces += new Pawn(black, "H7")

    pieces += new Castle(black, "A8")
    pieces += new Knight(black, "B8")
    pieces += new Bishop(black, "C8")
    pieces += new Queen(black, "E8")
    pieces += new King(black, "D8")
    pieces += new Bishop(black, "F8")
    pieces += new Knight(black, "G8")
    pieces += new Castle(black, "H8")


  }

  def twoPiecesInSameCell(cell: String): (Piece, Piece) = {
    (null, null)
  }

  def getDisplay: GridPane = {
    val parent: GridPane = new GridPane()
    val pane = new GridPane(){
      alignment = Pos.Center
//      hgap = 10
//      vgap = 10
      padding = Insets(25)
    }
    //Loop through peices and place in cells of grid

    //Build Board
    for (p: Piece <- pieces){
      val addr: (Int, Int) = Helper.translateCoordsToCellAddress(p.cell)
      val typeChar = p match {
        case _:Pawn => "♟"
        case _:Bishop => "♝"
        case _:Castle => "♜"
        case _:King => "♚"
        case _: Knight => "♞"
        case _:Queen => "♛"
      }
      val colour = p.colour

      board(addr._1)(addr._2) = s"$typeChar$colour"
    }

    //Translate To something looking nice

    for (x <- 0 until size){
      for (y <- 0 until size){
        val p = if (board(x)(y) != "") board(x)(y).charAt(0) else " "
        val c = if (board(x)(y) != "") board(x)(y).charAt(1) else " "
        pane.add(new Pane(){
          children = new Label(p.toString){
            id = if (c == Colours.black) Colours.black.toString else Colours.white.toString
          }
          prefHeight.value = 60
          prefWidth.value = 60
          style = if (java.lang.Math.floorMod(x, 2) == java.lang.Math.floorMod(y, 2)) "-fx-background-color:  #E8E8E8;" else "-fx-background-color: black;"
        }, x, y)
      }
    }
    val entriesPane: GridPane = new GridPane(){
      padding = Insets(20)
      hgap = 10
      vgap = 10
    }
    entriesPane.add(new Label("Enter Coords: "), 2, 0)
    entriesPane.add(coordsFromEntry, 2, 1)
    entriesPane.add(new Label(" move to "), 3, 1)
    entriesPane.add(coordsToEntry, 4, 1)
    entriesPane.add(submitButton, 4, 2)
    entriesPane.add(warningText, 2, 3)

    parent.add(pane, 1, 0)
    parent.add(entriesPane, 2, 0)

    refreshBoard
    parent
  }

  def getDisplayScene: Scene = {
    val x = new Scene(getDisplay)
    x.stylesheets.add(getClass.getResource("/styles.css").toExternalForm)
    x
  }

  def getPieceAt(coord: String): Piece = {
    for (p: Piece <- pieces){
      if (p.cell == coord){
        return p
      }
    }
    null
  }

  def refresh(): Unit = {
    Chess.primaryStage.scene = getDisplayScene
  }
}
