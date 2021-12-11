// Noah Acosta - HW 7- COSC 2436
import java.io.*;
import java.util.*;

public class dante {
    static String CLS = "\033[2J\033[1;1H";
	static String Red = "\033[31;1m";
	static String Green = "\033[32;1m";
	static String Yellow = "\033[33;1m";
	static String Normal = "\033[0m"; 
	static String WhiteOnRed = "\033[41;1m";
	static String WhiteOnBlue = "\033[44;1m";
	static ArrayList<Room> Vertex = new ArrayList<Room>();
	static ArrayList<Room> Path = new ArrayList<Room>();
	public static void main(String[] args) throws IOException
	{
		Scanner in = new Scanner(System.in);
        // read in vertices
        File file = new File("vertex.txt");
        Scanner infile = new Scanner(file);
		String Input = "";
        Screens screen = new Screens();
		while (infile.hasNextLine())
		{
			Input = infile.nextLine();
			Vertex.add(new Room(Input));
		}
        System.out.println();
		System.out.println(Vertex.size() + " vertices read from file");

		// read in edges
		file = new File("edge.txt");
		infile = new Scanner(file);
		String From, Direction, To;
		int Count=0;
		while (infile.hasNext())
		{
			Count++;
			From = infile.next();
			Direction = infile.next();
			To = infile.next();
			// ! Uncomment this line to help debug runtime error reading edge.txt
			//System.out.println("From: " + From + " Direction: " + Direction + " To: " + To);
			
			// locate From Vertex in ArrayList
			int IndexFrom = 0;
			while (!Vertex.get(IndexFrom).RoomName.equals(From))
			{  IndexFrom++;  }
			// locate To Vertex in ArrayList
			int IndexTo = 0;
			while (!Vertex.get(IndexTo).RoomName.equals(To))
			{  IndexTo++;  }
			// create edge
			if (Direction.equals("North"))
			{
				Vertex.get(IndexFrom).North = Vertex.get(IndexTo);
				Vertex.get(IndexTo).South = Vertex.get(IndexFrom);
			}
			if (Direction.equals("South"))
			{
				Vertex.get(IndexFrom).South = Vertex.get(IndexTo);
				Vertex.get(IndexTo).North = Vertex.get(IndexFrom);
			}
			if (Direction.equals("East"))
			{
				Vertex.get(IndexFrom).East = Vertex.get(IndexTo);
				Vertex.get(IndexTo).West = Vertex.get(IndexFrom);
			}
			if (Direction.equals("West"))
			{
				Vertex.get(IndexFrom).West = Vertex.get(IndexTo);
				Vertex.get(IndexTo).East = Vertex.get(IndexFrom);
			}
		}
		System.out.println(Count + " edges read from file");
        screen.DrawIntroScreen();
		System.out.println("Press <Enter> to Continue");
		in.nextLine();
        System.out.print(CLS);
        Room temp = Vertex.get(0); // points to Entryway
        char choice = ' ';
        while (choice != 'q'){
            System.out.println("\nYou are in room: " + WhiteOnBlue+temp.RoomName+Normal);

			if(temp.RoomName.equals("Limbo")){
				printLimbo();
			}
			if(temp.RoomName.equals("Lust")){
				printLust();
			}
			if(temp.RoomName.equals("Gluttony")){
				printGluttony();
			}
			if(temp.RoomName.equals("Bankruptcy")){
				printBank();
			}
			if(temp.RoomName.equals("Heresy")){
				printHeresy();
			}
			if(temp.RoomName.equals("Violence")){
				printViolence();
			}
			if(temp.RoomName.equals("Fraud")){
				printFraud();
			}
			if(temp.RoomName.equals("Treachery")){
				printTreachery();
			}
			if(temp.RoomName.equals("Damnation")){
				printDamnation();
			}
			if(temp.RoomName.equals("Conflict")){
				printConflict();
			}
			if(temp.RoomName.equals("Lazy")){
				printLazy();
			}
			if(temp.RoomName.equals("Greed")){
				printGreed();
			}
			if(temp.RoomName.equals("Anger")){
				printAnger();
			}
			if(temp.RoomName.equals("Repentance")){
				printRep();
			}
			if(temp.RoomName.equals("Salvation")){
				printSal();
			}
            System.out.println();
            System.out.println("Commands: n = north, s = south, e = east, w = west, f = find shortest path, q = QUIT ");
            System.out.print("Your choice: ");
            choice = in.next().charAt(0);

            if(choice == 'n' && temp.North != null){
                temp = temp.North;            
            }
            if(choice == 'e' && temp.East != null){
                temp = temp.East;
            }
            if(choice == 's' && temp.South != null){
                temp = temp.South;
            }
			if(choice == 'w' && temp.West != null){
                temp = temp.West;
            }
            if(choice == 'f'){
                System.out.println("\nWhich room would you like to travel to? ");
                in.nextLine();
                String destination = in.nextLine();
                // search for destin in vertex array list
                boolean destinFound = false;
                for(int i = 0; i<Vertex.size(); i++){
                    if(destination.equals(Vertex.get(i).RoomName)){ // found room
                        destinFound = true;
                        Dijkstra(temp, Vertex.get(i));
                        System.out.print("Shortest path: "); 
                        // step thru path array list and print e/a room name
                        for(int j = 0; j<Path.size(); j++){
                            System.out.print(Yellow +(Path.get(j).RoomName) +Normal+"   ");
                        }
                        System.out.println();
                    }
                }
                if(!destinFound){
                    System.out.println(Red+"This room was not found!"+Normal);
                }
            }
            if(choice != 'n' && choice != 'e' && choice != 's' && choice != 'w' && choice != 'f' && choice != 'q'){
                System.out.println(Red+"ERROR! Cannot perform that action."+Normal);
            }
        }
	} // end main

// METHODS 
    static void Dijkstra(Room Start, Room Finish)
	{
		// set distance to all rooms (except for Start) to 1000 and visited = false
		for (int i=0; i<Vertex.size(); i++)
		{
			if (Vertex.get(i) == Start)
				Vertex.get(i).Distance = 0;
			else
				Vertex.get(i).Distance = 1000;  //set distance to "infinity"
			Vertex.get(i).Visited = false;
		}
		// Do Dijkstra - find Distance to each room
		Room Temp = Start;
		while (!Finish.Visited)
		{
			Temp.Visited = true;
			if (Temp.North!=null && !Temp.North.Visited && Temp.North.Distance > Temp.Distance+1)
				Temp.North.Distance = 1 + Temp.Distance;
			if (Temp.South!=null && !Temp.South.Visited && Temp.South.Distance > Temp.Distance+1)
				Temp.South.Distance = 1 + Temp.Distance;
			if (Temp.East!=null && !Temp.East.Visited && Temp.East.Distance > Temp.Distance+1)
				Temp.East.Distance = 1 + Temp.Distance;
			if (Temp.West!=null && !Temp.West.Visited && Temp.West.Distance > Temp.Distance+1)
				Temp.West.Distance = 1 + Temp.Distance;

			int Smallest = 1000;
			int SmallestIndex = 0;
			for (int i=0; i<Vertex.size(); i++)
			{
				if (!Vertex.get(i).Visited && Vertex.get(i).Distance < Smallest)
				{
					Smallest = Vertex.get(i).Distance;
					SmallestIndex = i;
				}
			}
			Temp = Vertex.get(SmallestIndex);
		}

		// populate Path ArrayList with Rooms of shortest path
		Temp = Finish;
		Path.clear();
		Path.add(0,Finish);
		while (Temp != Start)
		{
			int N = 1000, S = 1000, E = 1000, W = 1000;
			if (Temp.North != null)  N = Temp.North.Distance;
			if (Temp.South != null)  S = Temp.South.Distance;
			if (Temp.East != null)  E = Temp.East.Distance;
			if (Temp.West != null)  W = Temp.West.Distance;
			if (N < S && N < E && N < W)
				Temp = Temp.North;
			else if (S < E && S < W)
				Temp = Temp.South;
			else if (E < W)
				Temp = Temp.East;
			else
				Temp = Temp.West;
			Path.add(0,Temp);
		}
	} // end Dijktra
    static void printLimbo(){
		System.out.println("---------------------------------------");
		System.out.println("|                                     |");
        System.out.println("|                                     |");
		System.out.println("|             lllllll_______          |");
		System.out.println("|            _,_ llllllllllll\\        |");
		System.out.println("|  ___      /   \\  lllllllllll\\       |");
		System.out.println("|  \\       | @ @ |  llllllllll\\       |");
		System.out.println("|  \\-----oOO---Ooo------------/       |");
		System.out.println("|  \\lllllllllllllllllllllllll/        |");
		System.out.println("|   \\lllllllllllllllllllllll/         |");
		System.out.println("|    \\lllllllllllllllllllll/          |");
		System.out.println("|  	//            \\               |");
		System.out.println("|         ||             ||           |");
		System.out.println("|         OO             OO           |");
        System.out.println("|                                     |");
		System.out.println("|                                     |");
        System.out.println("|                                     "+Green+"--"+Normal);
        System.out.println("|                                     ");
        System.out.println("|                                     "+Green+"__"+Normal);
        System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("---------------------------------------");
    }
    static void printLust(){
		System.out.println("---------------------------------------");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                  _  _               |");
		System.out.println("|                 ( \\/ )              |");
		System.out.println("|          .---.   \\  /   .-\"-.       |");
		System.out.println("|         /   6_6   \\/   / 4 4 \\      |");
		System.out.println("|         \\_  (__\\       \\_ v _/      |");
		System.out.println("|         //   \\\\        //   \\\\      |");
		System.out.println("|        ((     ))      ((     ))     |");
		System.out.println("| ========\"\"===\"\"========\"\"===\"\"===== |");
		System.out.println("|           |||            |||	      |");
		System.out.println("|            |              |         |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println(Green+"--                                    --");
		System.out.println("                                         ");
		System.out.println("__                                    __"+Normal);
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("---------------"+Green+"|        |"+Normal+"--------------");
    }
    static void printGluttony(){
		System.out.println("---------------------------------------");
		System.out.println("| // \"\"--.._		              |");
		System.out.println("| ||  (_)  _ \"-._                     |");
		System.out.println("| ||    _ (_)    '-.                  |");
		System.out.println("| ||   (_)   __..-'		      |");
		System.out.println("|  \\__..--\"\"                          |");
		System.out.println(Green+"--                                    --");
		System.out.println("                                         ");
		System.out.println("__                                    __"+Normal);
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("---------------------------------------");
    }
    static void printBank(){
		System.out.println("---------------------------------------");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println(Green+"--"+Normal+"                                    |");
		System.out.println("                                      |");
		System.out.println(Green+"--"+Normal+"                                    |");
		System.out.println("| ___________________________________ |");
		System.out.println("| |#######====================#######||");
		System.out.println("| |#(1)*UNITED STATES OF AMERICA*(1)#||");
		System.out.println("| |#**          /===\\   ********  **#||");
		System.out.println("| |*# {G}      | (\") |             #*||");
		System.out.println("| |#*  ******  | /v\\ |    O N E    *#||");
		System.out.println("| |#(1)         \\===/            (1)#||");
		System.out.println("| |##=========ONE DOLLAR===========##||");
		System.out.println("| ------------------------------------|");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("---------------"+Green+"|        |"+Normal+"--------------");
    }
    static void printHeresy(){
		System.out.println("---------------"+Green+"|        |"+Normal+"--------------");
        System.out.println("|                                     |");
        System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println(Green+"--"+Normal+"                                    |");
		System.out.println("                                      |");
		System.out.println(Green+"--"+Normal+"                                    |");
		System.out.println("|                                     |");
		System.out.println("|     !                               |");
		System.out.println("|    .-.                              |");
		System.out.println("|  __|=|__                            |");
		System.out.println("| (_/`-`\\_)                           |");
		System.out.println("| //\\___/\\\\                           |");
		System.out.println("| <>/   \\<>                           |");
		System.out.println("|  \\|_._|/                            |");
		System.out.println("|   <_I_>                             |");
		System.out.println("|    |||                              |");
		System.out.println("|   /_|_\\                             |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("---------------"+Green+"|        |"+Normal+"--------------");

    }
    static void printAnger(){
		System.out.println("---------------------------------------");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println(Green+"--                                    --");
		System.out.println("                                         ");
		System.out.println("__                                    __"+Normal);
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("| (╯°□°）╯︵ ┻━┻                      |");;
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("---------------"+Green+"|        |"+Normal+"--------------");
    }
    static void printGreed(){
		System.out.println("---------------"+Green+"|        |"+Normal+"--------------");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println(Green+"--                                    --");
		System.out.println("                                         ");
		System.out.println("__                                    __"+Normal);
		System.out.println("|                                     |");
		System.out.println("|  _ __ ___   ___  _ __   ___ _   _   |");
		System.out.println("| | '_ ` _ \\ / _ \\| '_ \\ / _ \\  ||    |");
		System.out.println("| | | | | | | (_) | | | |  __/ |_| |  |");
		System.out.println("| |_| |_| |_|\\___/|_| |_|\\___|\\__, |  |");
		System.out.println("|                              __/ |  |");
		System.out.println("|                             |___/   |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("---------------"+Green+"|        |"+Normal+"--------------");
    }
	static void printRep(){
		System.out.println("---------------------------------------");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     "+Green+"--"+Normal);
		System.out.println("|                                     ");
		System.out.println("|                                     "+Green+"--"+Normal);
		System.out.println("|         .---.                       |");
		System.out.println("|    '-.  |   |  .-'                  |");
		System.out.println("|      ___|   |___                    |");
		System.out.println("|  -=  [           ]  =-              |");
		System.out.println("|     `---.   .---'                   |");
		System.out.println("|  __||__ |   | __||__                |");
		System.out.println("|  '-..-' |   | '-..-'                |");
		System.out.println("|    ||   |   |   ||                  |");
		System.out.println("|    ||_.-|   |-,_||                  |");
		System.out.println("|  .-\"`   `\"`'`   `\"-.                |");
		System.out.println("|.'                   '.              |");
		System.out.println("|                                     |");
        System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("---------------"+Green+"|        |"+Normal+"--------------");
	}
	static void printSal(){
		System.out.println("---------------"+Green+"|        |"+Normal+"--------------");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|    -=-                              |");
		System.out.println("| (\\  _  /)                           |");
		System.out.println("| ( \\( )/ )                           |");
		System.out.println("| (       )                           |");
		System.out.println("|  `>   <'                            |");
		System.out.println("|  /     \\                            |");
		System.out.println("|  `-._.-'                            |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("---------------------------------------");
	}
	static void printLazy(){
		System.out.println("---------------"+Green+"|        |"+Normal+"--------------");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
        System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
        System.out.println("|                                     |");
        System.out.println("|                                     |");
		System.out.println("|                                     |");
    	System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("---------------"+Green+"|        |"+Normal+"--------------");

	}
	static void printViolence(){
		System.out.println("---------------"+Green+"|        |"+Normal+"--------------");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|        ,--.--._                     |");
		System.out.println("| ------\" _, \\___)                    |");
		System.out.println("|         / _/____)                   |");
		System.out.println("|         \\//(____)                   |");
		System.out.println("| ------\\     (__)                    |");
		System.out.println("|        `-----\"                      |");
		System.out.println("|                                     |");
    	System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("---------------"+Green+"|        |"+Normal+"--------------");
	}
	static void printConflict(){
		System.out.println("---------------"+Green+"|        |"+Normal+"--------------");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
        System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     "+Green+"--"+Normal);
		System.out.println("|                                     ");
		System.out.println("|                                     "+Green+"--"+Normal);
		System.out.println("|                                     |");
		System.out.println("| (ง •̀_•́)ง ผ(•̀_•́ผ)                    |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("---------------------------------------");
	}
	static void printTreachery(){
		System.out.println("-------------------------"+Green+"|        |"+Normal+"-----------------------");
		System.out.println("|                                                        |");
		System.out.println("|                                                        |");
		System.out.println("|                                                        |");
		System.out.println("|                                                        |");
		System.out.println("|                                                        |");
		System.out.println(Green+"--                                                       --");
		System.out.println("                                                           ");
		System.out.println("__                                                       __"+Normal);
		System.out.println("|                                                        |");
		System.out.println("|                   ______________                       |");
		System.out.println("|              ,===:'.,            `-._                  |");
		System.out.println("|                  `:.`---.__         `-._               |");
		System.out.println("|                    `:.     `--.         `.             |");
		System.out.println("|                      \\.        `.         `.           |");
		System.out.println("|              (,,(,    \\.         `.   ____,-`.,        |");
		System.out.println("|          (,'     `/   \\.   ,--.___`.'                  |");
		System.out.println("|       ,  ,'  ,--.  `,   \\.;'         `                 |");
		System.out.println("|         `{D, {    \\  :    \\;                           |");
		System.out.println("|          V,,'    /  /    //                            |");
		System.out.println("|          j;;    /  ,' ,-//.    ,---.      ,            |");
		System.out.println("|          \\;'   /  ,' /  _  \\  /  _   \\   ,'/           |");
		System.out.println("|                 \\   `'  / \\  `'  / \\  `.' /            |");
		System.out.println("|                  `.___,'   `.__,'   `.__,'             |");
		System.out.println("|                                                        |");
		System.out.println("-------------------------"+Green+"|        |"+Normal+"-----------------------");
	}
	static void printFraud(){
		System.out.println("---------------"+Green+"|        |"+Normal+"--------------");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println(Green+"--"+Normal+"                                    |");
		System.out.println("                                      |");
		System.out.println(Green+"--"+Normal+"                                    |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|             ∠( ᐛ 」∠)＿             |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("|                                     |");
		System.out.println("---------------------------------------");
	}
	static void printDamnation(){
		System.out.println("----------------------"+Green+"|        |"+Normal+"--------------------");
		System.out.println("|                                                  |");
		System.out.println("|                                                  |");
		System.out.println("|                                                  |");
		System.out.println("|                                                  |");
		System.out.println("|                                                  |");
		System.out.println("|                                                  |");
		System.out.println("|                                                  |");
		System.out.println("|                                                  |");
		System.out.println("|                 (  .      )                      |");
		System.out.println("|            )           (              )          |");
		System.out.println("|                  .  '   .   '  .  '  .           |");
		System.out.println("|         (    , )       (.   )  (   ',    )       |");
		System.out.println("|          .' ) ( . )    ,  ( ,     )   ( .        |");
		System.out.println("|       ). , ( .   (  ) ( , ')  .' (  ,    )       |");
		System.out.println("|      (_,) . ), ) _) _,')  (, ) '. )  ,. (' )     |");
		System.out.println("|    ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ |");
		System.out.println("----------------------------------------------------");

	}
} // end dante
class Room{
    String RoomName;
    Room North, South, East, West; // pointers
    boolean Visited;
    int Distance;

    Room(String newRoom){
        RoomName = newRoom;
    }
} // end Room
class Screens{
	static String Normal = "\033[0m";
	static String Red = "\033[31;1m";
	static String WhiteOnRed = "\033[41;1m";
    public void DrawIntroScreen(){
        Scanner in = new Scanner(System.in);
        System.out.println();
        System.out.println();
        System.out.println(WhiteOnRed+" ______       ____        __      _   ________    _____   __    _____       _____      __      _   _________    _____   ______        __      _     ____    "+Normal);
        System.out.println(WhiteOnRed+"(_  __ \\     (    )      /  \\\\    / ) (___  ___)  / ___/  (  )  / ____\\     (_   _)    /  \\    / ) (_   _____)  / ___/  (   __ \\      /  \\    / )   / __ \\  "+Normal);
        System.out.println(WhiteOnRed+"  ) ) \\ \\    / /\\ \\     / /\\ \\  / /      ) )    ( (__     \\/  ( (___         | |     / /\\ \\  / /    ) (___    ( (__     ) (__) )    / /\\ \\  / /   / /  \\ \\  "+Normal);
        System.out.println(WhiteOnRed+"  ( (   ) )  ( (__) )    ) ) ) ) ) )     ( (      ) __)         \\___ \\        | |     ) ) ) ) ) )   (   ___)    ) __)   (    __/     ) ) ) ) ) )  ( ()  () )"+Normal);
        System.out.println(WhiteOnRed+"  ) )  ) )   )    (    ( ( ( ( ( (       ) )    ( (                ) )       | |    ( ( ( ( ( (     ) (       ( (       ) \\ \\  _   ( ( ( ( ( (   ( ()  () ) "+Normal);
        System.out.println(WhiteOnRed+" / /__/ /   /  /\\  \\   / /  \\ \\/ /      ( (      \\ \\___        ___/ /       _| |__  / /  \\ \\/ /    (   )       \\ \\___  ( ( \\ \\_))  / /  \\ \\/ /    \\ \\__/ /  "+Normal);
        System.out.println(WhiteOnRed+"(______/   /__(  )__\\ (_/    \\__/       /__\\      \\____\\      /____/       /_____( (_/    \\__/      \\_/         \\____\\  )_) \\__/  (_/    \\__/      \\____/   "+Normal);

        System.out.println();
		System.out.println();
		System.out.println("                                           .\"\"--.._");
		System.out.println("                                           []      `'--.._");
		System.out.println("                                           ||__           "+Red+"`'-,"+Normal + "                         ______________________________________________________________________________________");
		System.out.println("                                         `)||_ ```'--"+Red+"..       \\"+Normal + "                        |           Travel through the Circles of Hell in Dante's Inferno                    |");
		System.out.println("                                           /|//}        "+Red+"``--._  |"+Normal + "                      |                  \"Abondon all hope ye who enter here...\"                           |");
		System.out.println("                     _                    //|| }               "+Red+"`\\/"+Normal + "                     |                                                                                    |");
        System.out.println("                  .'` `'.                /////}            "+Normal + "                            | 00. Salvation -- All that is Holy                                                  |");
		System.out.println("                 /  .\"\"\".\\             //{///" + "                                          | 0. Repentance -- Forgiveness of Sin                                                |");
		System.out.println("                /  /_  _`\\            // ` ||"+ "                                          | 1. Limbo -- Unbaptized and Virtuous Pagans                                         |");
		System.out.println("                | |(_)(_)||          _//   ||"+ "                                          | 2. Lust -- Lustful                                                                 |");
		System.out.println("                | |  /\\  )|        _///\\   ||"+ "                                          | 3. Gluttony -- Gluttons and Gourmand                                               |");
		System.out.println("                | |L====J |       / |/ |   ||"+ "                                          | 4. Bankruptcy -- The Poor                                                          |");
		System.out.println("               /  /'-..-' /    .'`  \\  |   ||"+ "                                          | 5. Lazy -- Lazy Ones                                                               | ");
		System.out.println("              /   |  :: | |_.-`      |  \\  ||"+ "                                          | 6. Greed -- Hoarders and Spenders                                                  |");
		System.out.println("             /|   `\\-::.| |          \\   | ||"+ "                                          | 7. Anger -- Wrath and Sullen                                                       |");
		System.out.println("           /` `|   /    | |          |   / ||"+ "                                          | 8. Heresy -- Heretics and False Teachers                                           |");
		System.out.println("         |`    \\   |    / /          \\  |  ||"+ "                                          | 9. Violence -- Tyrants, Robbers, Suicides, Blasphemers                             |");
		System.out.println("        |       `\\_|    |/      ,.__. \\ |  ||"+ "                                          | 10. Fraud -- Pimps, Coin Falsifiers, Alchemists, Corrupt Politicians               |");
		System.out.println("        /                     /`    `\\ ||  ||"+ "                                          | 11. Treachery -- Betrayers of Kin, Country, Guests, and Benefactors                |");
		System.out.println("       |           .         /        \\||  ||"+"                                          |                                                                                    |");
		System.out.println("       |                     |         |/  ||"+"                                          |                                                                                    |");
		System.out.println("       /         /           |         (   ||"+ "                                          | ** NOTE: This project is an inspiration of Dante Alighieri's Epic Poem, Inferno. **|");
		System.out.println("      /          .           /          )  ||" + "                                          | ** I claim no right to the work, please do not sue me **                           |");
		System.out.println("     |            \\          |             ||"+"                                          | ~ Noah Acosta                                                                      |");
		System.out.println("    /             |          /             ||"+"                                          |____________________________________________________________________________________|");
		System.out.println("   |\\            /          |              ||");
		System.out.println("   \\ `-._       |           /              ||");
		System.out.println("    \\ ,//`\\    /`           |              ||");
		System.out.println("     ///\\  \\  |             \\              ||");
		System.out.println("    |||| ) |__/             |              ||");
		System.out.println("    |||| `.(                |              ||");
		System.out.println("    `\\\\` /`                 /              ||");
		System.out.println("       /`                   /              ||");
		System.out.println("      /                     |              ||");
		System.out.println("     |                      \\              ||");
		System.out.println("    /                        |             ||");
		System.out.println("  /`                          \\            ||");
		System.out.println("/`                            |            ||");
		System.out.println("`-.___,-.      .-.        ___,'            ||");
		System.out.println("         `---'`   `'----'`");
		System.out.println();
		System.out.println();
    }
} // END