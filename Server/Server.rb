#A server for the Chess game, to allow PvP
#Works by player posting board, then other user getting board

require 'socket'

$BOARDFILE = "board.txt"

def getBoard()
  content = ""
  File.open($BOARDFILE, "r") do |f|
    while (line = f.gets)
      content += line
    end
  end
  return content
end

def saveBoard(contents)
  File.open($BOARDFILE, "w") do |f|
    f.syswrite(contents)
  end
end

def handlePost()
end

def handleGet()
end

ip = "localhost"
port = 2345

puts "Starting web server on #{ip}:#{port} "

server = TCPServer.new(ip, port)

if (!File.exist? File.expand_path "board.txt")
  saveBoard("")
end

loop {
  socket = server.accept
  request = socket.gets
  requestType, path= request.split(" ")
  STDERR.puts "requestType => #{requestType}"
  STDERR.puts "path => #{path}"
  STDERR.puts ""

  headers = {}
  while line = socket.gets.split(" ", 2) do
    break if line[0] == ""
    headers[line[0].chop] = line[1].strip
  end
  puts "Headers => #{headers}"

  response = "stock response"
  if requestType == "POST"
    puts "POST request"
    response = "POST req"

    data = socket.read(headers["Content-Length".to_i])
    socket.close

    puts "Post Data => #{data}"
    saveBoard(data)
  end
  if requestType == "GET"
    response = getBoard()

    socket.print "HTTP/1.1 200 OK\r\n" +
                 "Content-Type: text/plain\r\n" + #CHange plain to html to make html parsed
                 "Content-Length: #{response.bytesize}\r\n" +
                 "Connection: close\r\n"
    socket.print "\r\n"
    socket.print response
    socket.close
  end
}
