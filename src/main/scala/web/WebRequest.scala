package web

import java.io.{ByteArrayOutputStream, ObjectOutputStream}

import collection.JavaConverters._
import java.net.{HttpURLConnection, URL}
import java.io.ByteArrayInputStream
import java.io.ObjectInputStream

import board.Board
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor
import piece.Piece

import scala.collection.mutable.ListBuffer
import scalaj.http.{Http, HttpOptions}

//https://stackoverflow.com/questions/11719373/doing-http-request-in-scala
class WebRequest {
  val serverIP = "http://localhost:2345"

  def get(url: String = serverIP,
          connectTimeout: Int = 5000,
          readTimeout: Int = 5000,
          requestMethod: String = "GET"): ListBuffer[Piece] =
  {
    val connection = (new URL(url)).openConnection.asInstanceOf[HttpURLConnection]
    connection.setConnectTimeout(connectTimeout)
    connection.setReadTimeout(readTimeout)
    connection.setRequestMethod(requestMethod)
    val inputStream = connection.getInputStream
    val content = new Yaml(new Constructor(classOf[Board])) load inputStream
    if (inputStream != null) inputStream.close
    println(content.asInstanceOf[Board].pieces)
    content.asInstanceOf[Board].pieces
  }

  def post(board: Board, url: String = serverIP,
           connectTimeout: Int = 5000,
           readTimeout: Int = 5000,
           requestMethod: String = "POST") =
  {
    try{
      Http(url)
        .postData(new Yaml() dump board)
        .header("Content-Type", "text/plain")
        .header("Charset", "UTF-8")
        .asString
    }catch{
      case e: java.net.SocketTimeoutException => ""
    }
  }
}
