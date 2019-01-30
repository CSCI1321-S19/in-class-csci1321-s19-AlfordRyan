package mud

class Room(
    val name: String,
    desc: String,
    private var items: List[Item],
    exits: Array[Int]){
  def description(): String = {
    val exitString =  exits.filter(_ != -1).map(i => Room.rooms(i).name).mkString(", ")
    name + "\n" + desc + "\nExits: " + exitString + "\nItems: " + items.map(_.name).mkString(", ")
  }
  
  def getExit(dir: Int): Option[Room] = if(exits(dir) != -1) Some(Room.rooms(exits(dir))) else None
  
  def getItem(itemName: String): Option[Item] = {
    if(items.map(_.name).contains(itemName)) {
      val toReturn = items.find(_.name == itemName)
      items = items.filter(_.name != itemName)
      toReturn
    } else None
    
  }
  
  def dropItem(item: Item): Unit = items ::= item

}

object Room {
  val rooms = readRooms()
  
  def readRooms(): Array[Room] = {
    val source = scala.io.Source.fromFile("map.txt")
    val lines = source.getLines()
    val rooms = Array.fill(lines.next.trim.toInt)(readRoom(lines))
    source.close()
    rooms
  }
  
  def readRoom(lines: Iterator[String]): Room = {
    val name = lines.next
    val desc = lines.next
    val items = List.fill(lines.next.trim.toInt) {
      Item(lines.next, lines.next)
    }
    val exits = lines.next.split(",").map(_.trim.toInt)
    new Room(name, desc, items, exits)
  }
}