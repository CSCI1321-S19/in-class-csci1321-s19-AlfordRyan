package mud

/**
This is a stub for the main class for your MUD.
*/
object Main {
	def main(args: Array[String]): Unit = {
//		println("Welcome to my MUD.\nPlease enter a name")
//		val name = readLine
		val name = "King of Swing"
		val player = new Player(name, Room.rooms(0), Nil)
		println(player.getRoom.description())
		while(true){
		  player.processCommand(readLine)
		}
	}
}
