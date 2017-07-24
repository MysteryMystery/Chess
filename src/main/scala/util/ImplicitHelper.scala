package util

import scalafx.scene.control.TextField

object ImplicitHelper {
  implicit class ImplicitTextEntry(e: TextField){
    def wipe = e.text = ""
  }

}
