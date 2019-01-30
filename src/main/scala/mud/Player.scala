package mud

class Player(
    name: String,
    private var room: Room,
    private var inventory: List[Item]) {
  
  def getRoom = room
  
  def processCommand(command: String): Unit = {
//    println("Cur: " + command)
    val alter = command.trim.toLowerCase
    val fix = if(command.matches("[nsewup].*") && command != "exit") Player.dirIndex.find(_.startsWith(command)).get else if(command.contains(" ")) alter.split(" ")(0) else alter
//    println(fix)
/*    alter match {
      case "mark" => println("works")
      case _ => println("we have prblems")
    }*/
//    val alter = if(command.matches("[nsewup].*")) Player.dirIndex.find(_.startsWith(command)) else if(command.contains(" ")) command.split(" ")(0) else command
    fix match{
      case "north" | "south" | "east" | "west" | "up" | "down" => move(fix)
      case "look" => println(room.description())
      case "inventory" | "inv" => println(inventoryListing)
      case "get" => {
        val target = room.getItem(alter.split(" ")(1))
        target match {
          case Some(n) => {
            inventory ::= n
          }
          case None => println("That item is not in the room")
        }
      }
      case "drop" => {
        room.dropItem(inventory.find(i => i.name == alter.split(" ")(1)).get)
        inventory = inventory.filter(i => i.name != alter.split(" ")(1))
        
      }
      case "exit" => System.exit(0)
      case "help" => println(Player.helpInfo)
      case _ => println("That is not a valid command")
      
    }
  }

  def getFromInventory(itemName: String): Option[Item] = if(inventory.map(_.name).contains(itemName)) inventory.find(_.name == itemName) else None

  def addToInventory(item: Item): Unit = inventory ::= item

  def inventoryListing(): String = {
    if(inventory.size > 0)
    inventory.map(i => i.name + ": " +i.desc).mkString("\n")
    else "You have nothing in your inventory"
  }

  //Needs to handle options
  def move(dir: String): Unit = {
    val potential = room.getExit(Player.dirIndex.indexOf(dir))
    potential match {
      case Some(n) => {
        room = n
        println(room.description())
      }
      case None => println("You cannot go in that direction")
    }
  }
}

object Player{
  val dirIndex = Array("north", "south", "east", "west", "up", "down")
  val helpInfo = ("north, south, east, west, up, down - for movement (single letter abbreviations work)\n" +
    "look - reprints the description of the current room\n" + 
    "inv/inventory - list the contents of your inventory\n" + 
    "get item - to get an item from the room and add it to your inventory\n" + 
    "drop item - to drop an item from your inventory into the room.\n" + 
    "exit - leave the game\n" + 
    "help - print the available commands and what they do")

  
}