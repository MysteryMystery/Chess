package web

import java.io.{ByteArrayOutputStream, ObjectOutputStream}

import collection.JavaConverters._
import java.net.{HttpURLConnection, URL}
import java.io.ByteArrayInputStream
import java.io.ObjectInputStream

import board.Board

import scalaj.http.{Http, HttpOptions}

//https://stackoverflow.com/questions/11719373/doing-http-request-in-scala
class WebRequest {
  val serverIP = "http://localhost:2345"

  def get(url: String = serverIP,
          connectTimeout: Int = 5000,
          readTimeout: Int = 5000,
          requestMethod: String = "GET") =
  {
    val connection = (new URL(url)).openConnection.asInstanceOf[HttpURLConnection]
    connection.setConnectTimeout(connectTimeout)
    connection.setReadTimeout(readTimeout)
    connection.setRequestMethod(requestMethod)
    val inputStream = connection.getInputStream
    val content = io.Source.fromInputStream(inputStream).mkString
    if (inputStream != null) inputStream.close
    println(content)
    content
  }

  def post(board: Board, url: String = serverIP,
           connectTimeout: Int = 5000,
           readTimeout: Int = 5000,
           requestMethod: String = "POST") =
  {

    Http(url)
      .postData(serialise(board.board))
      .header("Content-Type", "text/plain")
      .header("Charset", "UTF-8")
      .option(HttpOptions.readTimeout(readTimeout))
      .option(HttpOptions.connTimeout(connectTimeout))
      .asString
  }

  def serialise(x: Object): String = {
    try {
      var bo: ByteArrayOutputStream = new ByteArrayOutputStream()
      var so: ObjectOutputStream = new ObjectOutputStream(bo)
      so.writeObject(x)
      so.flush()
      bo.toString()
    } catch {
      case e: Exception => e.printStackTrace(); "Error in object serialisation."
    }
  }

  def deDerialise(x: String): Array[Array[String]] = {
    try {
      val b = x.getBytes
      val bi = new ByteArrayInputStream(b)
      val si = new ObjectInputStream(bi)
      si.readObject.asInstanceOf[Array[Array[String]]]
    } catch {
      case e: Exception =>
        System.out.println(e); null
    }
  }
}
