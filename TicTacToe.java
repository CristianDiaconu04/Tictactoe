/**
 *  @author Cristian Diaconu
 *	June 18, 2021
 *  This program runs the game tic tac toe
 */

 import java.util.*;
 import java.io.*;
 
 public class TicTacToe {
     
     static Board b;
     static int[][] grid;
     static int win;
     static int player=1;
     static int turns=0;
     
     public static void main(String[] args) {
 
         b = new Board(3,3);
         grid = new int [3][3];
         int win=0;
         boolean playing=true;
         boolean bot=false;
         int row, col;
                 
         b.putPeg("x", 1,0);
         b.putPeg("o", 1,2);
         b.displayMessage("Click the x for multiplayer or the o for singleplayer.");
         Coordinate pick;
         pick=b.getClick();
         row=pick.getRow();
         col=pick.getCol();
         grid[row][col]=1;
         
         // Removes the pegs so that the game starts with an empty board
         b.removePeg(1,0);
         b.removePeg(1,2);		
         
         b.displayMessage("Play!");
         
         // Runs only for multiplayer
         if (grid[1][0]==1) {
             grid[1][0]=0; // Sets the "box" to 0 so it does not mess up the rest of the program
             row=0;
             col=0;
             while (playing) {
                 // Calls the method that lets the player choose their move
                 win=player();
                 turns+=1;
                 // The board has 9 squares, so the loop should stop running if all of them are taken
                 if (turns==9) {
                     playing=false;
                 }
                 if (win==1 || win==2) {	
                     playing=false;
                 }
                 // If player 1 just had their turn, player 2 goes next
                 if (player==1) {
                     player=2;
                 } // And vice versa
                 else {
                     player=1;
                 }
             }
         }
     
         // Runs only for singleplayer with the computer
         else {
             grid[1][2]=0; // Sets the "box" to 0 so it does not mess up the rest of the program
             row=0;
             col=0;
             bot=true;
             while (playing) {
                 player=1;
                 // Calls the method that lets the player choose their move
                 win=player();
                 if (win==1) {	
                     playing=false;
                     break;
                 }
                 turns+=1;
                 // The board has 9 squares, so the loop should stop running if all of them are taken
                 if (turns>4) {
                     playing=false;	
                     break;
                 }
                 player=2;
                 // Calls the method that chooses the computer's move
                 win=bot();
                 if (win==2) {	
                     playing=false;
                     break;
                 }
             }
         }
         
         // The following if/else if/else checks if anyone won
         if (win==1) {
             b.displayMessage("Player 1 wins!");
         }
         else if (win==2) {
             // If the player was the computer
             if (bot) {
                 fileReadWrite();
             }
             // If the player was human
             else {
                 b.displayMessage("Player 2 wins!");
             }
             
         }
         else {
             b.displayMessage("Draw!");
         }
     }
     
     /**
      *  Description: This method lets the player make their move
      *	Preconditions: player must be an int equal to 1 or 2
      *  Postconditions: Returns a boolean to main() based on if the player has won the game yet or not
      */	
     public static int player() {
         Coordinate c;
         int row=0, col=0;
         
         c=b.getClick();
         row=c.getRow();
         col=c.getCol();
         
         // This loop only occus if the player clicks a box that has already been clicked, and allows them to re-pick
         while (grid[row][col]==1 || grid[row][col]==2) {
             b.displayMessage("Pick an unpicked box!");
             c=b.getClick();
             row=c.getRow();
             col=c.getCol();
         }
         // Player 1 is x, while player 2 is o
         if (player==1) {
             b.putPeg("x", row,col);
         }
         else {
             b.putPeg("o", row,col);
         }
         // Sets that box to equal 1 or 2 based on the player 
         grid[row][col]=player;
         // Calls this method which checks if the current player has wont the game yet and returns true or false
         win=checkPlayer(player);
         return win;
     }
     
     /**
      *  Description: This method decides what move the bot should make and puts an o on that square if the player chooses singleplayer mode
      *	Preconditions: player must be an int
      *  Postconditions: Returns a boolean to main() based on if the computer has won the game or not
      */		
     public static int bot() {
         int row=0, col=0;
         /* This loop first checks if there are two in a row anywhere with o's so that the computer can win. 
          * If there aren't, it checks if there are two in a row anywhere with x's so that the computer can stop the player from winning. */
         for (int i=2; i>0; --i) {
             // Checks if the bot should click on (1,1)
             if ((grid[0][0]==i && grid[2][2]==i) || (grid[2][0]==i && grid[0][2]==i) || (grid[0][1]==i && grid[2][1]==i) || (grid[1][0]==i && grid[1][2]==i)) {
                 if (grid[1][1]==0) {
                     grid[1][1]=2;
                     b.putPeg("o", 1,1);
                     win=botCheck(player);	
                     return win;
                 }
             }
             else if ((grid[0][0]==i && grid[2][2]==i) && (grid[2][0]==i && grid[0][2]==i) && (grid[0][1]==i && grid[2][1]==i) && (grid[1][0]==i && grid[1][2]==i)) {
                 if (grid[1][1]==0) {
                     grid[1][1]=2;
                     b.putPeg("o", 1,1);
                     win=botCheck(player);	
                     return win;
                 }
             }
             else if ((grid[0][0]==i && grid[2][2]==i) && (grid[2][0]==i && grid[0][2]==i)) {
                 if (grid[1][1]==0) {
                     grid[1][1]=2;
                     b.putPeg("o", 1,1);
                     win=botCheck(player);	
                     return win;
                 }
             }
             else if ((grid[0][0]==i && grid[2][2]==i) && (grid[0][1]==i && grid[2][1]==i)) {
                 if (grid[1][1]==0) {
                     grid[1][1]=2;
                     b.putPeg("o", 1,1);
                     win=botCheck(player);	
                     return win;
                 }
             }
             else if ((grid[0][0]==i && grid[2][2]==i) && (grid[1][0]==i && grid[1][2]==i)) {
                 if (grid[1][1]==0) {
                     grid[1][1]=2;
                     b.putPeg("o", 1,1);
                     win=botCheck(player);	
                     return win;
                 }
             }
             else if ((grid[2][0]==i && grid[0][2]==i) && (grid[0][1]==i && grid[2][1]==i)) {
                 if (grid[1][1]==0) {
                     grid[1][1]=2;
                     b.putPeg("o", 1,1);
                     win=botCheck(player);	
                     return win;
                 }
             }
             else if ((grid[2][0]==i && grid[0][2]==i) && (grid[1][0]==i && grid[1][2]==i)) {
                 if (grid[1][1]==0) {
                     grid[1][1]=2;
                     b.putPeg("o", 1,1);
                     win=botCheck(player);	
                     return win;
                 }
             }
             else if ((grid[0][1]==i && grid[2][1]==i) && (grid[1][0]==i && grid[1][2]==i)) {
                 if (grid[1][1]==0) {
                     grid[1][1]=2;
                     b.putPeg("o", 1,1);
                     win=botCheck(player);	
                     return win;
                 }
             }
             // Checks if the bot should click on (0,0)
             else if (((grid[0][1]==i && grid[0][2]==i) || (grid[1][0]==i && grid[2][0]==i) || (grid[1][1]==i && grid[2][2]==i))) {
                 if (grid[0][0]==0) {
                     grid[0][0]=2;
                     b.putPeg("o", 0,0);
                     win=botCheck(player);	
                     return win;
                 }			
             }
             else if (((grid[0][1]==i && grid[0][2]==i) && (grid[1][0]==i && grid[2][0]==i) && (grid[1][1]==i && grid[2][2]==i))) {
                 if (grid[0][0]==0) {
                     grid[0][0]=2;
                     b.putPeg("o", 0,0);
                     win=botCheck(player);	
                     return win;
                 }			
             }
             else if ((grid[0][1]==i && grid[0][2]==i) && (grid[1][0]==i && grid[2][0]==i)) {
                 if (grid[0][0]==0) {
                     grid[0][0]=2;
                     b.putPeg("o", 0,0);
                     win=botCheck(player);	
                     return win;
                 }			
             }
             else if ((grid[0][1]==i && grid[0][2]==i) && (grid[1][1]==i && grid[2][2]==i)) {
                 if (grid[0][0]==0) {
                     grid[0][0]=2;
                     b.putPeg("o", 0,0);
                     win=botCheck(player);	
                     return win;
                 }			
             }
             else if ((grid[1][0]==i && grid[2][0]==i) && (grid[1][1]==i && grid[2][2]==i)) {
                 if (grid[0][0]==0) {
                     grid[0][0]=2;
                     b.putPeg("o", 0,0);
                     win=botCheck(player);	
                     return win;
                 }			
             }
             // Checks if the bot should click on (0,2)
             else if ((grid[0][0]==i && grid[0][1]==i) || (grid[2][0]==i && grid[1][1]==i) || (grid[2][2]==i && grid[1][2]==i)) {
                 if (grid[0][2]==0) {
                     grid[0][2]=2;
                     b.putPeg("o", 0,2);
                     win=botCheck(player);	
                     return win;	
                 }
             }
             else if ((grid[0][0]==i && grid[0][1]==i) && (grid[2][0]==i && grid[1][1]==i) && (grid[2][2]==i && grid[1][2]==i)) {
                 if (grid[0][2]==0) {
                     grid[0][2]=2;
                     b.putPeg("o", 0,2);
                     win=botCheck(player);	
                     return win;	
                 }
             }
             else if ((grid[0][0]==i && grid[0][1]==i) && (grid[2][0]==i && grid[1][1]==i)) {
                 if (grid[0][2]==0) {
                     grid[0][2]=2;
                     b.putPeg("o", 0,2);
                     win=botCheck(player);	
                     return win;	
                 }
             }
             else if ((grid[0][0]==i && grid[0][1]==i) && (grid[2][2]==i && grid[1][2]==i)) {
                 if (grid[0][2]==0) {
                     grid[0][2]=2;
                     b.putPeg("o", 0,2);
                     win=botCheck(player);	
                     return win;	
                 }
             }
             else if ((grid[2][0]==i && grid[1][1]==i) && (grid[2][2]==i && grid[1][2]==i)) {
                 if (grid[0][2]==0) {
                     grid[0][2]=2;
                     b.putPeg("o", 0,2);
                     win=botCheck(player);	
                     return win;	
                 }
             }
             // Checks if the bot should click on (2,0)
             else if ((grid[0][0]==i && grid[1][0]==i) || (grid[0][2]==i && grid[1][1]==i) || (grid[2][2]==i && grid[2][1]==i)) {
                 if (grid[2][0]==0) {
                     grid[2][0]=2;
                     b.putPeg("o", 2,0);
                     win=botCheck(player);	
                     return win;	
                 }			
             }
             else if ((grid[0][0]==i && grid[1][0]==i) && (grid[0][2]==i && grid[1][1]==i) && (grid[2][2]==i && grid[2][1]==i)) {
                 if (grid[2][0]==0) {
                     grid[2][0]=2;
                     b.putPeg("o", 2,0);
                     win=botCheck(player);	
                     return win;	
                 }			
             }
             else if ((grid[0][0]==i && grid[1][0]==i) && (grid[0][2]==i && grid[1][1]==i)) {
                 if (grid[2][0]==0) {
                     grid[2][0]=2;
                     b.putPeg("o", 2,0);
                     win=botCheck(player);	
                     return win;	
                 }			
             }
             else if ((grid[0][0]==i && grid[1][0]==i) && (grid[0][2]==i && grid[1][1]==i)) {
                 if (grid[2][0]==0) {
                     grid[2][0]=2;
                     b.putPeg("o", 2,0);
                     win=botCheck(player);	
                     return win;	
                 }			
             }
             else if ((grid[0][0]==i && grid[1][0]==i) && (grid[2][2]==i && grid[2][1]==i)) {
                 if (grid[2][0]==0) {
                     grid[2][0]=2;
                     b.putPeg("o", 2,0);
                     win=botCheck(player);	
                     return win;	
                 }			
             }
             else if ((grid[0][2]==i && grid[1][1]==i) && (grid[2][2]==i && grid[2][1]==i)) {
                 if (grid[2][0]==0) {
                     grid[2][0]=2;
                     b.putPeg("o", 2,0);
                     win=botCheck(player);	
                     return win;	
                 }			
             }
             // Checks if the bot should click on (2,2)
             else if ((grid[0][0]==i && grid[1][1]==i) || (grid[2][0]==i && grid[2][1]==i) || (grid[0][2]==i && grid[1][2]==i)) {
                 if (grid[2][2]==0) {
                     grid[2][2]=2;
                     b.putPeg("o", 2,2);
                     win=botCheck(player);	
                     return win;	
                 }
             }
             else if ((grid[0][0]==i && grid[1][1]==i) && (grid[2][0]==i && grid[2][1]==i) && (grid[0][2]==i && grid[1][2]==i)) {
                 if (grid[2][2]==0) {
                     grid[2][2]=2;
                     b.putPeg("o", 2,2);
                     win=botCheck(player);	
                     return win;	
                 }
             }
             else if ((grid[0][0]==i && grid[1][1]==i) && (grid[2][0]==i && grid[2][1]==i)) {
                 if (grid[2][2]==0) {
                     grid[2][2]=2;
                     b.putPeg("o", 2,2);
                     win=botCheck(player);	
                     return win;	
                 }
             }
             else if ((grid[0][0]==i && grid[1][1]==i) && (grid[2][0]==i && grid[2][1]==i)) {
                 if (grid[2][2]==0) {
                     grid[2][2]=2;
                     b.putPeg("o", 2,2);
                     win=botCheck(player);	
                     return win;	
                 }
             }
             else if ((grid[0][0]==i && grid[1][1]==i) && (grid[0][2]==i && grid[1][2]==i)) {
                 if (grid[2][2]==0) {
                     grid[2][2]=2;
                     b.putPeg("o", 2,2);
                     win=botCheck(player);	
                     return win;	
                 }
             }
             else if ((grid[2][0]==i && grid[2][1]==i) && (grid[0][2]==i && grid[1][2]==i)) {
                 if (grid[2][2]==0) {
                     grid[2][2]=2;
                     b.putPeg("o", 2,2);
                     win=botCheck(player);	
                     return win;	
                 }
             }
             // Checks if the bot should click on (0,1)
             else if (((grid[0][0]==i && grid[0][2]==i) || (grid[2][1]==i && grid[1][1]==i)) || ((grid[0][0]==i && grid[0][2]==i) && (grid[2][1]==i && grid[1][1]==i))) {
                 if (grid[0][1]==0) {
                     grid[0][1]=2;
                     b.putPeg("o", 0,1);
                     win=botCheck(player);	
                     return win;	
                 }			
             }
             // Checks if the bot should click on (1,0)
             else if (((grid[0][0]==i && grid[2][0]==i) || (grid[1][1]==i && grid[1][2]==i)) || ((grid[0][0]==i && grid[2][0]==i) && (grid[1][1]==i && grid[1][2]==i))) {
                 if (grid[1][0]==0) {
                     grid[1][0]=2;
                     b.putPeg("o", 1,0);
                     win=botCheck(player);	
                     return win;	
                 }			
             }
             // Checks if the bot should click on (1,2)
             else if (((grid[1][0]==i && grid[1][1]==i) || (grid[0][2]==i && grid[2][2]==i)) || ((grid[1][0]==i && grid[1][1]==i) && (grid[0][2]==i && grid[2][2]==i))) {
                 if (grid[1][2]==0) {
                     grid[1][2]=2;
                     b.putPeg("o", 1,2);
                     win=botCheck(player);	
                     return win;	
                 }				
             }
             // Checks if the bot should click on (2,1)
             else if (((grid[0][1]==i && grid[1][1]==i) || (grid[2][0]==i && grid[2][2]==i)) || ((grid[0][1]==i && grid[1][1]==i) && (grid[2][0]==i && grid[2][2]==i))) {
                 if (grid[2][1]==0) {
                     grid[2][1]=2;
                     b.putPeg("o", 2,1);					
                     win=botCheck(player);					
                     return win;	
                 }				
             }
         }
         // If none of the above moves work, the computer randomly chooses a square
         Random rand=new Random();
         row = 1 + rand.nextInt( 2 );
         col = 1 + rand.nextInt( 2 );
         // Checks if it is already picked
         if (grid[row][col]!=0) {
             while (grid[row][col]!=0) {
                 row = 1 + rand.nextInt( 2 );
                 col = 1 + rand.nextInt( 2 );				
             }
         }
         b.putPeg("o", row,col);
         grid[row][col]=2;
         player=2;
         win=checkPlayer(player);
         player=1;
         row=0;
         col=0;
         return win;	
     }
     
     /**
      *  Description: This method contains 4 lines of code which are used by every else if in the bot() method in order to shorten the code. It checks if the bot has won the game yet.
      *	Preconditions: player is an int	
      *  Postconditions: Returns the win variable. If the bot wins, 2 is returned.
      */	
     public static int botCheck(int player) {
         player=2;
         win=checkPlayer(player);
         player=1;
         return win;	
     }
     
     /**
      *  Description: This method checks if there are three pegs of the same type in a row in any location
      *	Preconditions: player is an int	
      *  Postconditions: Returns the win variable. If player 1 wins, win=1, if player 2 win=2. If neither, win=0.
      */		
     public static int checkPlayer (int player) {
         // The following 3 if/else if statements check the horizontal win conditions
         if (grid[0][0]==player && grid[0][1]==player && grid[0][2]==player) {
             win=player;
             return win;
         }		
         else if (grid[1][0]==player && grid[1][1]==player && grid[1][2]==player) {
             win=player;
             return win;
         }		
         else if (grid[2][0]==player && grid[2][1]==player && grid[2][2]==player) {
             win=player;
             return win;
         }	
         // The following 3 if else statements check the vertical win conditions
         else if (grid[0][0]==player && grid[1][0]==player && grid[2][0]==player) {
             win=player;
             return win;
         }		
         else if (grid[0][1]==player && grid[1][1]==player && grid[2][1]==player) {
             win=player;
             return win;
         }
         
         else if (grid[0][2]==player && grid[1][2]==player && grid[2][2]==player) {
             win=player;
             return win;
         }		
         // The next two if else statements check the diagonal win conditions		
         else if (grid[0][0]==player && grid[0][0]==grid[1][1] && grid[0][0]==grid[2][2]) {
             win=player;
             return win;		
         }
         else if (grid[0][2]==player && grid[0][2]==grid[1][1] && grid[0][2]==grid[2][0]) {
             win=player;
             return win;	
         }
         else {
             win=0;
             return win;	
         }
             
     }
     
     /**
      *  Description: This method reads and writes to the file "wins.txt" the number of times the computer has won
      *	Preconditions: The file must be named wins.txt and it must contain a single integer
      *  Postconditions: Displays a message on the board, there must be a board for the message to be displayed
      */		
     public static void fileReadWrite() {
         // The following code reads from a text file. I got it from Mr.Benum's tutorial on the google classroom.
         File textFile = new File("wins.txt");		
         Scanner input = null;    	
         try{
             input = new Scanner(textFile);
         }
         catch (FileNotFoundException e){    		
         }		
         int oldNumber=0;    	
         while(input.hasNextInt()){
             oldNumber = input.nextInt();
 
         }  
         // Increases the number by 1 to show that the bot has won 1 more time
         int newNumber=oldNumber+1;
         input.close();
         
         /* The following code writes to a text file. I got it from: Pramodbablad. “How To Replace Specific String In Text File In Java?” Java Concept Of The Day, 19 Jan. 2017,
          *  javaconceptoftheday.com/modify-replace-specific-string-in-text-file-in-java/. */
         
         try {			
             FileWriter writer;
             try {
                 writer = new FileWriter("wins.txt"); 
                 writer.write(""+newNumber);
                 writer.close();
             } catch (IOException e) {
                 e.printStackTrace();
             }
             b.displayMessage("The computer wins! It has won "+newNumber+" time(s) in total.");
         } catch (NumberFormatException ex) {
             ex.printStackTrace();
         }			
     }
     
 }
