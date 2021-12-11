/* Noah Acosta*/
import java.util.*;
public class game
{
	static String CLS = "\033[2J\033[1;1H";
	static String Red = "\033[31;1m";
	static String Green = "\033[32;1m";
	static String Yellow = "\033[33;1m";
	static String Blue = "\033[34;1m";
	static String Purple = "\033[35;1m";
	static String Cyan = "\033[36;1m";
	static String White = "\033[37;1m";
	static String Normal = "\033[0m"; // default gray color & reset background to black
	static String WhiteOnRed = "\033[41;1m";
	static String WhiteOnYellow = "\033[43;1m";	
	static String WhiteOnBlue = "\033[44;1m";
	static String WhiteOnGreen = "\033[42;1m";
	static String WhiteOnCyan = "\033[46;1m";
	static String WhiteOnPurple = "\033[45;1m";
	
	public static void main (String[] args)
	{
		Scanner in = new Scanner(System.in);
		
		Player P = new Player('N', "Noah");
		ArrayList<Enemy> Enemies = new ArrayList<Enemy>();
		Enemies.add(new Enemy("Strawberry"));
		Enemies.add(new Enemy("Strawberry"));
		Enemies.add(new Enemy("Strawberry"));
		Enemies.add(new Enemy("Strawberry"));
		Enemies.add(new Enemy("Vanilla"));
		Enemies.add(new Enemy("Vanilla"));
		Enemies.add(new Enemy("Chocolate"));
		
		Screens screen = new Screens();
		screen.DrawIntroScreen();
		
		String Choice = "";
		String Msg = "";
		while (!Choice.equals("q") && P.Hp > 0 && Enemies.size() > 0) // game loop
		{
			System.out.print(CLS);
			P.PrintWorld();
			System.out.println(P.Name + " HP = " + P.Hp + " Attack = " + P.Attack + " Armor = " + P.Armor);
			System.out.println("Message: " + Msg);
			System.out.println();
			System.out.print("Move = a,s,d,w		Quit=q		Enter Command: ");
			Choice = in.nextLine();
			// player moves
			if (Choice.equals("d"))
				P.MoveRight();
			if (Choice.equals("a"))
				P.MoveLeft();
			if (Choice.equals("w"))
				P.MoveUp();
			if (Choice.equals("s"))
				P.MoveDown();
			//player attacks and enemy attacks
			// Step through Enemy ArrayList and attack if adjacent to player
			for (int i=0; i<Enemies.size(); i++)
			 {
				  if ((Enemies.get(i).Ypos == P.Ypos && (Enemies.get(i).Xpos == P.Xpos+1)) || // player is to the left
					  (Enemies.get(i).Ypos == P.Ypos && (Enemies.get(i).Xpos == P.Xpos-1)) ||  // player is to the right
						  (Enemies.get(i).Xpos == P.Xpos && (Enemies.get(i).Ypos == P.Ypos+1)) ||  // player is above
							  (Enemies.get(i).Xpos == P.Xpos && (Enemies.get(i).Ypos == P.Ypos-1)))    // player is below
				  {
					  Enemies.get(i).Hp -= P.Attack;    // Player attacks Enemy
	                  //P.Hp -= Enemies.get(i).Attack;    // Enemy attacks Player
					  
					  P.Hp -= (100 * Enemies.get(i).Attack) / (100 + P.Armor);    // Enemy attacks Player
					  Msg = "You are being attacked!";
					  if (Enemies.get(i).Hp <= 0)    // Enemy dies    
						  {
							  P.World[Enemies.get(i).Xpos][Enemies.get(i).Ypos] = ' ';
							  Enemies.remove(i);
							  Msg = " ";
						  }
						  
				  }
			  }
			  // Step through Enemy ArrayList and move towards the player
			  for (int i=0; i<Enemies.size(); i++)
			  {
				  // only move enemy w/n range
				  if (Math.abs(Enemies.get(i).Xpos - P.Xpos) <= Enemies.get(i).Range && 
					  Math.abs(Enemies.get(i).Ypos - P.Ypos) <= Enemies.get(i).Range)
				  {
					  if (Enemies.get(i).Xpos > P.Xpos)
						  Enemies.get(i).MoveLeft();
					  else
						  Enemies.get(i).MoveRight();
					  if (Enemies.get(i).Ypos > P.Ypos)
						  Enemies.get(i).MoveUp();
					  else
						  Enemies.get(i).MoveDown();
				  }
				  else
				  {
					  int R = (int)(Math.random() * 4) +1;
					  if (R == 1)
						  Enemies.get(i).MoveUp();
					  if (R == 2)
						  Enemies.get(i).MoveDown();
					  if (R == 3)
						  Enemies.get(i).MoveLeft();
					  else
						  Enemies.get(i).MoveRight();
				  }
			  }
		  }// end game loop
		  if (P.Hp <= 0)
		  {
			  screen.DrawLosingScreen();
			  System.out.println("Better luck next time!");
			  System.out.println(); 
		  }
		  if (P.Hp > 0 && Enemies.size() <= 0)
		  {
			  screen.DrawWinningScreen();
			  System.out.println("CONGRATS. YOU ARE A CERTIFIED CUPCAKE SLAYER... NOW WAKE UP!!!");
			  System.out.println();
		  }
		  if (Choice.equals("q") || Choice.equals("Q"))
			  System.out.println("Thanks for Playing!\n");
	} // end MAIN
} // end Game

class Screens
{
	static String CLS = "\033[2J\033[1;1H";
	static String Red = "\033[31;1m";
	static String Green = "\033[32;1m";
	static String Yellow = "\033[33;1m";
	static String Blue = "\033[34;1m";
	static String Purple = "\033[35;1m";
	static String Cyan = "\033[36;1m";
	static String White = "\033[37;1m";
	static String Normal = "\033[0m"; // default gray color & reset background to black
	static String WhiteOnRed = "\033[41;1m";
	static String WhiteOnYellow = "\033[43;1m";	
	static String WhiteOnBlue = "\033[44;1m";
	static String WhiteOnGreen = "\033[42;1m";
	static String WhiteOnCyan = "\033[46;1m";
	static String WhiteOnPurple = "\033[45;1m";
	public void DrawIntroScreen()
	{
		Scanner in = new Scanner(System.in);
		System.out.print(CLS);
		System.out.println();
		System.out.println();
		System.out.println(WhiteOnRed+"oOoOOoOOo o      O o.OOoOoo       `o    O  ooOoOOo  o       o      o.OOoOoo `OooOOo.         .oOOOo.  O       o OooOOo.   .oOOOo.     Oo    `o    O  o.OOoOoo .oOOOo.\t");  
		System.out.println(WhiteOnRed+"    o     O      o  O              o   O      O    O       O        O        o     `o       .O     o  o       O O     `O .O     o    o  O    o   O    O       o     o\t");  
		System.out.println(WhiteOnRed+"    o     o      O  o              O  O       o    o       o        o        O      O       o         O       o o      O o          O    o   O  O     o       O.     \t");  
		System.out.println(WhiteOnRed+"    O     OoOooOOo  ooOO           oOo        O    o       o        ooOO     o     .O       o         o       o O     .o o         oOooOoOo  oOo      ooOO     `OOoo. \t"); 
		System.out.println(WhiteOnRed+"    o     o      O  O              o  o       o    O       O        O        OOooOO'        o         o       O oOooOO'  o         o      O  o  o     O             `O\t"); 
		System.out.println(WhiteOnRed+"    O     O      o  o              O   O      O    O       O        o        o    o         O         O       O o        O         O      o  O   O    o              o \t");
		System.out.println(WhiteOnRed+"    O     o      o  O              o    o     O    o     . o     .  O        O     O        `o     .o `o     Oo O        `o     .o o      O  o    o   O       O.    .O \t");
		System.out.println(WhiteOnRed+"    o'    o      O ooOooOoO        O     O ooOOoOo OOoOooO OOoOooO ooOooOoO  O      o        `OoooO'   `OoooO'O o'        `OoooO'  O.     O  O     O ooOooOoO  `oooO'  \t"+Normal);
        System.out.println();                                                                                                                                                               
	

		      
		System.out.println();                                                                                                                                                                           
		System.out.println();
		System.out.println(Red+"                             ,@.                                  "+Normal+"  __________________________________________________________________________");
		System.out.println(Red+"                           ,@.@@,.	                            "+Normal+"| YOU ARE TRAPPED IN A NIGHTMARE.                                        |");
		System.out.println(Red+"                     ,@@,.@@@.  @.@@@,.                            "+Normal+" |                                                                        |");
		System.out.println(Red+"                   ,@@.             @@,.                            "+Normal+"| YOUR FAVORITE CUPCAKES ARE OUT TO KILL YOU!                            |");
		System.out.println(Red+"            ,@@@.@,.@.      "+Normal+"\\  /"+Red+"      @.  @@@,.@.@@,.               "+Normal+"|                                                                        |");
		System.out.println(Red+"       ,@@.@.     @@.@@."+Normal+"     \\/"+Red+"       @,.        @’ @@,             "+Normal+"| DEFEAT THEM ALL IN ORDER TO WAKE UP OR PERISH IN YOUR SLEEP            |");
		System.out.println(Red+" ,@@. @.      "+Normal+"\\\\                           //"+Red+"           @,          "+Normal+"| BUT BEWARE! NOT ALL CUPCAKES ARE THE SAME!                             |");
		System.out.println(Red+",@.  @@.       "+Normal+".-=.                       .-=."+Red+"          @,          "+Normal+"| YOU NEVER WERE A FAN OF STRAWBERRY, BUT YOU LOVE CHOCOLATE DON'T YOU?  |");
		System.out.println(Red+"@.             "+Normal+"`~ `         _______       ` ~ `         "+Red+"@@,         "+Normal+"|                                                                        |");
		System.out.println(Red+"@,.                        "+Normal+"/      \\                "+Red+",  @, @,         "+Normal+"| THEY KNOW THIS...                                                      |");
		System.out.println(Red+"@.                        "+Normal+"/        \\.           "+Red+",        @          "+Normal+"|                                                                        |");
		System.out.println(Red+" @,.@@.     @,.                                  @,.    @’          "+Normal+"|                                                                        |");
		System.out.println(Red+"  @@||@,.  @’@,.       @@,.  @@ @,.           @’@@,  @’             "+Normal+"|                                                                        |");
		System.out.println(Cyan+"     \\\\"+Red+"@@@@’  @,.      @’@@@@’   @@,.   @@@’ "+Cyan+"//"+Red+"@@@’                 "+Normal+"|                       GOOD LUCK (YOU MIGHT NEED IT)                    |");
		System.out.println(Cyan+"        |||||||| "+Red+"@@,.  @@’ "+Cyan+"|||||||  |"+Red+"@@@"+Cyan+"|"+Red+"@"+Cyan+"||  ||                    "+Normal+"|                                                                        |");
		System.out.println(Cyan+"         \\\\\\\\\\\\\\  ||"+Red+"@@@"+Cyan+"||  |||||||  |||||||  //                     "+Normal+"|                                                                        |");
		System.out.println(Cyan+"          |||||||  ||||||  ||||||   ||||||  ||                      "+Normal+"|                                                                        |");
		System.out.println(Cyan+"           \\\\\\\\\\\\  ||||||  ||||||  ||||||  //                       "+Normal+"|________________________________________________________________________|");
		System.out.println(Cyan+"            ||||||  ||N||  |||||   A||||  ||");
		System.out.println(Cyan+"             \\\\\\\\\\  |||||  |||||  |||||  //");
		System.out.println(Cyan+"             |||||  ||||  |||||  ||||  ||");
		System.out.println(Cyan+"               \\\\\\\\  ||||  ||||  ||||  //");
		System.out.println(Cyan+"                ||||||||||||||||||||||||");																 
		System.out.println(Normal);
		System.out.println();                                                                                                              
		System.out.println();                                                                                                       
		System.out.println(Yellow+"                             ,@.                                    "+Normal+"__________________________________________________________________________");
		System.out.println(Yellow+"                           ,@.@@,.	                            "+Normal+"|                                 KEY                                    |");
		System.out.println(Yellow+"                     ,@@,.@@@.  @.@@@,.                             "+Normal+"|                                                                        |");
		System.out.println(Yellow+"                   ,@@.             @@,.                            "+Normal+"| Player: Avatar = "+WhiteOnBlue+"'N'"+Normal+"; Hp = 100; Attack = 10; Armor = 5;                |");
		System.out.println(Yellow+"            ,@@@.@,.@.      "+Normal+"\\  /"+Yellow+"      @.  @@@,.@.@@,.               "+Normal+"|                                                                        |");
		System.out.println(Yellow+"       ,@@.@.     @@.@@.     "+Normal+"\\/       "+Yellow+"@,.        @’ @@,             "+Normal+"| Killer Strawberry: Avatar = "+Red+"'S'"+Normal+"; Hp = 100; Attack = 5; Armor = 20;     |");
		System.out.println(Yellow+" ,@@. @.      "+Normal+"\\\\                           //"+Yellow+"           @,          "+Normal+"| Killer Vanilla: Avatar = "+Yellow+"'V'"+Normal+"; Hp = 120; Attack = 10; Armor = 30;       |");
		System.out.println(Yellow+",@.  @@.       "+Normal+".-=.                       .-=."+Yellow+"          @,          "+Normal+"| Killer Chocolate: Avatar = "+Purple+"'C'"+Normal+";  Hp = 200; Attack = 25; Armor = 45;    |");
		System.out.println(Yellow+"@.             "+Normal+"`~ `         _______       ` ~ `         "+Yellow+"@@,         "+Normal+"|                                                                        |");
		System.out.println(Yellow+"@,.                        "+Normal+"/      \\                "+Yellow+",  @, @,         "+Normal+"|                                                                        |");
		System.out.println(Yellow+"@.                        "+Normal+"/        \\"+Yellow+".           ,        @          "+Normal+"|                         (HEHEHEHE GOOD LUCK >:) )                      |");
		System.out.println(Yellow+" @,.@@.     @,.                                  @,.    @’          "+Normal+"|                                                                        |");
		System.out.println(Yellow+"  @@||@,.  @’@,.       @@,.  @@ @,.           @’@@,  @’             "+Normal+"|                                                                        |");
		System.out.println(Green+"     \\\\"+Yellow+"@@@@’  @,.      @’@@@@’   @@,.   @@@’ "+Green+"//"+Yellow+"@@@’                 "+Normal+"|Pick up "+WhiteOnRed+"'+'"+Normal+" for more health!                                            |");
		System.out.println(Green+"        |||||||| "+Yellow+"@@,.  @@’ "+Green+"|||||||  |"+Yellow+"@@@"+Green+"|"+Yellow+"@"+Green+"||  ||                    "+Normal+"|Pick up "+WhiteOnCyan+"'%'"+Normal+" for more Attack power!                                      |");
		System.out.println(Green+"         \\\\\\\\\\\\\\  ||"+Yellow+"@@@"+Green+"||  |||||||  |||||||  //                     "+Normal+"|Pick up "+WhiteOnPurple+"'#'"+Normal+" for more Armor!                                             |");
		System.out.println(Green+"          |||||||  ||||||  ||||||   ||||||  ||                      "+Normal+"|CREATED BY NOAH ACOSTA                                                  |");
		System.out.println(Green+"           \\\\\\\\\\\\  ||||||  ||||||  ||||||  //                       "+Normal+"|________________________________________________________________________|");
		System.out.println(Green+"            ||||||  ||N||  |||||   A||||  ||");
		System.out.println(Green+"             \\\\\\\\\\  |||||  |||||  |||||  //");
		System.out.println(Green+"             |||||  ||||  |||||  ||||  ||");
		System.out.println(Green+"               \\\\\\\\  ||||  ||||  ||||  //");
		System.out.println(Green+"                ||||||||||||||||||||||||");															
		System.out.println(Normal);
		System.out.println("Press <Enter> To Begin...");
		in.nextLine();
	}
	public void DrawWinningScreen()
	{
		System.out.println();
		System.out.print(CLS);
		
		System.out.println(WhiteOnGreen+"oooooo   oooo   .oooooo.   ooooo     ooo      oooooo   oooooo     oooo ooooo ooooo      ooo ");
		System.out.println(WhiteOnGreen+" `888.   .8'   d8P'  `Y8b  `888'     `8'       `888.    `888.     .8'  `888' `888b.     `8' ");
		System.out.println(WhiteOnGreen+"  `888. .8'   888      888  888       8         `888.   .8888.   .8'    888   8 `88b.    8  ");
		System.out.println(WhiteOnGreen+"   `888.8'    888      888  888       8          `888  .8'`888. .8'     888   8   `88b.  8  ");
		System.out.println(WhiteOnGreen+"    `888'     888      888  888       8           `888.8'  `888.8'      888   8     `88b.8  ");
		System.out.println(WhiteOnGreen+"     888      `88b    d88'  `88.    .8'            `888'    `888'       888   8       `888  ");
		System.out.println(WhiteOnGreen+"    o888o      `Y8bood8P'     `YbodP'               `8'      `8'       o888o o8o        `8  "+Normal);
		System.out.println();
		
		
		System.out.println(Yellow+"                             ,@.                                    ");
		System.out.println(Yellow+"                           ,@.@@,.	                           ");
		System.out.println(Yellow+"                     ,@@,.@@@.  @.@@@,.                        ");
		System.out.println(Yellow+"                   ,@@.             @@,.                         ");
		System.out.println(Yellow+"            ,@@@.@,.@.                @.  @@@,.@.@@,.         ");
		System.out.println(Yellow+"       ,@@.@.     @@.@@.               @,.        @’ @@,         ");
		System.out.println(Yellow+" ,@@. @.      "+Normal+"\\ /                        \\ /"+Yellow+"           @,        ");
		System.out.println(Yellow+",@.  @@.       "+Normal+"\\                          \\             "+Yellow+"@,        ");
		System.out.println(Yellow+"@.            "+Normal+"/ \\          _______       / \\              "+Yellow+"@@,       ");
		System.out.println(Yellow+"@,.                        "+Normal+"/      \\                "+Yellow+",  @, @,     ");
		System.out.println(Yellow+"@.                        "+Normal+"/________\\"+Yellow+"           ,        @     ");
		System.out.println(Yellow+" @,.@@.     @,.                                  @,.    @’         ");
		System.out.println(Yellow+"  @@"+Blue+"||"+Yellow+"@,.  @’@,.       @@,.  @@ @,.           @’@@,  @’      ");
		System.out.println(Blue+"     \\\\"+Yellow+"@@@@’  @,.      @’@@@@’   @@,.   @@@’ "+Blue+"//"+Yellow+"@@@’             ");
		System.out.println(Blue+"        |||||||| "+Yellow+"@@,.  @@’ "+Blue+"|||||||  |"+Yellow+"@@@"+Blue+"|"+Yellow+"@"+Blue+"||  ||               ");
		System.out.println(Blue+"         \\\\\\\\\\\\\\  ||"+Yellow+"@@@"+Blue+"||  |||||||  |||||||  //     ");
		System.out.println(Blue+"          |||||||  ||||||  ||||||   ||||||  ||               ");
		System.out.println(Blue+"         \\\\\\\\\\\\  ||||||  ||||||  ||||||  //       ");
		System.out.println(Blue+"            ||||||  ||N||  |||||   A||||  ||");
		System.out.println(Blue+"             \\\\\\\\\\  |||||  |||||  |||||  //");
		System.out.println(Blue+"             |||||  ||||  |||||  ||||  ||");
		System.out.println(Blue+"               \\\\\\\\  ||||  ||||  ||||  //");
		System.out.println(Blue+"                ||||||||||||||||||||||||");	
		System.out.println(Normal);
		System.out.println();
	}
	public void DrawLosingScreen()
	{
		System.out.println();
		System.out.print(CLS);
		System.out.println();
		System.out.println();
		System.out.println("                                           .\"\"--.._");
		System.out.println("                                           []      `'--.._");
		System.out.println("                                           ||__           "+Red+"`'-,"+Normal);
		System.out.println("                                         `)||_ ```'--"+Red+"..       \\"+Normal);
		System.out.println("                     _                    /|//}        "+Red+"``--._  |"+Normal);
		System.out.println("                  .'` `'.                /////}              "+Red+"`\\/"+Normal);
		System.out.println("                 /  .\"\"\".\\             //{///");
		System.out.println("                /  /_  _`\\            // `||");
		System.out.println("                | |(_)(_)||          _//   ||");
		System.out.println("                | |  /\\  )|        _///\\   ||");
		System.out.println("                | |L====J |       / |/ |   ||");
		System.out.println("               /  /'-..-' /    .'`  \\  |   ||");
		System.out.println("              /   |  :: | |_.-`      |  \\  ||");
		System.out.println("             /|   `\\-::.| |          \\   | ||");
		System.out.println("           /` `|   /    | |          |   / ||");
		System.out.println("         |`    \\   |    / /          \\  |  ||");
		System.out.println("        |       `\\_|    |/      ,.__. \\ |  ||");
		System.out.println("        /                     /`    `\\ ||  ||");
		System.out.println("       |           .         /        \\||  ||");
		System.out.println("       |                     |         |/  ||");
		System.out.println("       /         /           |         (   ||");
		System.out.println("      /          .           /          )  ||");
		System.out.println("     |            \\          |             ||");
		System.out.println("    /             |          /             ||");
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
		System.out.println(WhiteOnRed+"		oooooo   oooo   .oooooo.   ooooo     ooo      oooooooooo.   ooooo oooooooooooo oooooooooo.\t\t\t");
		System.out.println(WhiteOnRed+"		 `888.   .8'   d8P'  `Y8b  `888'     `8'      `888'   `Y8b  `888' `888'     `8 `888'   `Y8b\t\t\t");
		System.out.println(WhiteOnRed+"		  `888. .8'   888      888  888       8        888      888  888   888          888      888\t\t\t");
		System.out.println(WhiteOnRed+"		   `888.8'    888      888  888       8        888      888  888   888oooo8     888      888\t\t\t");
		System.out.println(WhiteOnRed+"		    `888'     888      888  888       8        888      888  888   888    \"     888      888\t\t\t");
		System.out.println(WhiteOnRed+"		     888      `88b    d88'  `88.    .8'        888     d88'  888   888       o  888     d88'\t\t\t");
		System.out.println(WhiteOnRed+"		    o888o      `Y8bood8P'     `YbodP'         o888bood8P'   o888o o888ooooood8 o888bood8P'\t\t\t"+Normal);
		System.out.println();
		System.out.println();
		
	}
}

class GameObj // superclass
{
	static String CLS = "\033[2J\033[1;1H";
	static String Red = "\033[31;1m";
	static String Green = "\033[32;1m";
	static String Yellow = "\033[33;1m";
	static String Blue = "\033[34;1m";
	static String Purple = "\033[35;1m";
	static String Cyan = "\033[36;1m";
	static String White = "\033[37;1m";
	static String Normal = "\033[0m"; // default gray color & reset background to black
	static String WhiteOnRed = "\033[41;1m";
	static String WhiteOnYellow = "\033[43;1m";	
	static String WhiteOnBlue = "\033[44;1m";
	static String WhiteOnGreen = "\033[42;1m";
	static String WhiteOnCyan = "\033[46;1m";
	static String WhiteOnPurple = "\033[45;1m";
	static public char World[][] = new char[81][41];	
	int Xpos, Ypos;
	char Avatar;
	public void PrintWorld()
	{
		for (int y=1; y<=40; y++)
		{
		   for (int x=1; x<=80; x++)
		   {
			   if (World[x][y] == ' ')
				   System.out.print("  ");
			   else if (World[x][y] == '*')
				   System.out.print(Green+"* " + Normal);
			   else if (World[x][y] == 'S')
				   System.out.print(Red+"S ");
			   else if (World[x][y] == 'V')
				   System.out.print(Yellow+"V ");
			   else if (World[x][y] == 'C')
				   System.out.print(Purple+"C ");
			   else if (World[x][y] == 'N')
				   System.out.print(WhiteOnBlue+"N"+Normal + " ");
			   else if (World[x][y] == '+')
				   System.out.print(WhiteOnRed+"+"+ Normal+ " ");
			   else if (World[x][y] == '%')
				   System.out.print(WhiteOnCyan+"%" + Normal +" ");
			   else if (World[x][y] == '#')
				   System.out.print(WhiteOnPurple+"#" + Normal +" ");
			   else
				   System.out.print(Normal+World[x][y]+" ");
		   }
		   System.out.println();
		}
	}
} // end GameObj Class
class Enemy extends GameObj //subclass
{
	String Type;
	int Hp, Attack, Armor;
	int Range;
	Enemy (String theType)
	{
		Type = theType;
		Xpos = (int)(Math.random() * 75) + 3;
		Ypos = (int)(Math.random() * 35) + 3;
		
		if (Type.equals("Strawberry"))
		{
			Hp = 100; Attack = 5; Avatar = 'S'; Armor = 20; Range = 40;
		}
		if (Type.equals("Vanilla"))
		{
			Hp = 120; Attack = 10; Avatar = 'V'; Armor = 30; Range = 20;
		}
		if (Type.equals("Chocolate"))
		{
			Hp = 200; Attack = 25; Avatar = 'C';  Armor = 45; Range = 10;
		}
		World[Xpos][Ypos] = Avatar; // puts enemy in world
	}
	public void MoveRight()
	{
		if (World[Xpos+1][Ypos] == ' ')
		{
			World[Xpos][Ypos] = ' ';
			Xpos++;
			World[Xpos][Ypos] = Avatar;
		}
	}
	public void MoveLeft()
	{
		if (World[Xpos-1][Ypos] == ' ')
		{
			World[Xpos][Ypos] = ' ';
			Xpos--;
			World[Xpos][Ypos] = Avatar;
		}
	}
	public void MoveUp()
	{
		if (World[Xpos][Ypos-1] == ' ')
		{
			World[Xpos][Ypos] = ' ';
			Ypos--;
			World[Xpos][Ypos] = Avatar;
		}
	}
	public void MoveDown()
	{
		if (World[Xpos][Ypos+1] == ' ')
		{
			World[Xpos][Ypos] = ' ';
			Ypos++;
			World[Xpos][Ypos] = Avatar;
		}
	}
} //end Enemy Class
class Player extends GameObj
{
	int Hp, Attack, Armor;
	String Name;
	
	Player (char theAvatar, String theName)
	{
		Avatar = theAvatar; Name = theName;
		Attack = 10; Hp = 150; Armor = 5;
		Xpos = 2; Ypos = 2;
		// set entire world to spaces
		for (int x=1; x<=80; x++)
		   for (int y=1; y<=40; y++)
		      World[x][y] = ' ';
		// put player into World
		World[Xpos][Ypos] = Avatar;
		// Outline Wolrd
		
		for(int x = 1; x <= 80; x++)
			World[x][1] = '*';
		for(int x = 1; x <= 80; x++)
			World[x][40] = '*';
		for(int x = 60; x <=80; x++)
			World[x][5] = '*';
		for(int x = 10; x <=25; x++)
			World[x][20] = '*';
		for(int x = 30; x <=40; x++)
			World[x][20] = '*';
		for(int x = 45; x <=60; x++)
			World[x][20] = '*';
		for(int x = 65; x <=80; x++)
			World[x][20] = '*';
		
		
		for(int y = 1; y <= 40; y++)
			World[1][y] = '*';
		for(int y = 1; y <= 40; y++)
			World[80][y] = '*';
		for(int y = 10; y <= 30; y++)
			World[18][y] = '*';
		for(int y = 34; y <= 39; y++)
			World[10][y] = '*';
		for(int y = 10; y <= 30; y++)
			World[28][y] = '*';
		for(int y = 10; y <= 30; y++)
			World[35][y] = '*';
		for(int y = 10; y <= 30; y++)
			World[60][y] = '*';
		for(int y = 10; y <= 30; y++)
			World[65][y] = '*';
		
		
		// put power up items into world
		World [(int)(Math.random() * 74) + 3][(int)(Math.random() * 30) + 3] = '+';// + means health pack
		World [(int)(Math.random() * 20) + 3][(int)(Math.random() * 25) + 3] = '+';// + means health pack
		World [(int)(Math.random() * 70) + 3][(int)(Math.random() * 35) + 3] = '+';// + means health pack
		World [(int)(Math.random() * 60) + 3][(int)(Math.random() * 10) + 3] = '+';
		World [(int)(Math.random() * 60) + 3][(int)(Math.random() * 26) + 3] = '%'; // % means attack up
		World [(int)(Math.random() * 70) + 3][(int)(Math.random() * 27) + 3] = '%'; // % means attack up
		World [(int)(Math.random() * 37) + 3][(int)(Math.random() * 16) + 3] = '%'; // % means attack up
		World [(int)(Math.random() * 20) + 3][(int)(Math.random() * 10) + 3] = '%'; // % means attack up
		World [(int)(Math.random() * 60) + 3][(int)(Math.random() * 5) + 3] = '#'; // # means armor up
		World [(int)(Math.random() * 10) + 3][(int)(Math.random() * 36) + 3] = '#';
		World [(int)(Math.random() * 70) + 3][(int)(Math.random() * 1) + 3] = '#';
		
		
	}
	public void MoveRight()
	{
		if (World[Xpos+1][Ypos] == ' ' || World[Xpos+1][Ypos] == '+' || World[Xpos+1][Ypos] == '%' || World[Xpos+1][Ypos] == '#')
		{
			if (World[Xpos+1][Ypos] == '+')
				Hp+=50;
			if (World[Xpos+1][Ypos] == '%')
				Attack += 10;
			if (World[Xpos+1][Ypos] == '#')
				Armor += 20;
			World[Xpos][Ypos] = ' ';
			Xpos++;
			World[Xpos][Ypos] = Avatar;
		}
	}
	public void MoveLeft()
	{
		if (World[Xpos-1][Ypos] == ' ' || World[Xpos-1][Ypos] == '+' || World[Xpos-1][Ypos] == '%' || World[Xpos-1][Ypos] == '#')
		{
			if (World[Xpos-1][Ypos] == '+')
				Hp+=50;
			if (World[Xpos-1][Ypos] == '%')
				Attack += 10;
			if (World[Xpos-1][Ypos] == '#')
				Armor += 20;
			World[Xpos][Ypos] = ' ';
			Xpos--;
			World[Xpos][Ypos] = Avatar;
		}
	}
	public void MoveUp()
	{
		if (World[Xpos][Ypos-1] == ' ' || World[Xpos][Ypos-1] == '+' || World[Xpos][Ypos-1] == '%' || World[Xpos][Ypos-1] == '#')
		{
			if (World[Xpos][Ypos-1] == '+')
				Hp+=50;
			if (World[Xpos][Ypos-1] == '%')
				Attack += 10;
			if (World[Xpos][Ypos-1] == '#')
				Armor += 20;
			World[Xpos][Ypos] = ' ';
			Ypos--;
			World[Xpos][Ypos] = Avatar;
		}
	}
	public void MoveDown()
	{
		if (World[Xpos][Ypos+1] == ' ' || World[Xpos][Ypos+1] == '+' || World[Xpos][Ypos+1] == '%' || World[Xpos][Ypos+1] == '#')
		{
			if (World[Xpos][Ypos+1] == '+')
				Hp+=50;
			if (World[Xpos][Ypos+1] == '%')
				Attack += 10;
			if (World[Xpos][Ypos+1] == '#')
				Armor += 20;
			World[Xpos][Ypos] = ' ';
			Ypos++;
			World[Xpos][Ypos] = Avatar;
		}
	}
} // end Player Class