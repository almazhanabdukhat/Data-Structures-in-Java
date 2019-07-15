

public class TaxiMove {
	  private static class Move {
	      private int pos1;
	      private int pos2;
	      private int fuel = 100; //initial fuel level is 100;
	      private double balance;
	      Move next;

	      //default constructor sets a Move at (0,0) with default 100 fuel level and 0 balance   
	      Move(){ 
	      }

	      //prameter constructor sets Move with specific coordiantes and default balance and fuel levels 
	      Move(int pos1, int pos2){ 
	        this.pos1 = pos1;
	        this.pos2 = pos2;
	      }
	    
	  
	      public int getX() { 
	        return pos1;
	      }
	      
	      public int getY() {
	        return pos2;
	      }
	     
	      public double getBalance( ) {
	        return (int) (balance * 100) / 100.0; //returns balance rounded to one decimal place
	      }
	         
	      public int getFuel() { 
	        return fuel; 
	      }

	      //validPosition method checks whether the integer can store the position on the grid
	      //the integer can be between 0 and 50 inclusive
	      public boolean validPosition (int pos1, int pos2){
	          boolean valid = (pos1 >= 0 && pos1 <= 50 && pos2 >= 0 && pos2 <= 50) ; 
	            return valid;
	      }
	        
	      
	      public void setBalance(double balance) {
	        this.balance = balance; 
	      } 
	      
	      //exception is thrown if fuel level exceeds the maximum of 100
	      public void setFuel(int fuel) throws IllegalArgumentException { 
	        if (fuel > 100) 
	          throw new IllegalArgumentException ("Fuel level cannot exceed 100");
	        else 
	          this.fuel = fuel; 
	      } 
	      
	      //returns the next Move reference
	      public Move getNext() { 
	        return this.next; 
	      }
	      
	      //sets the next reference to a specific Move
	      public void setNext(Move m) { 
	        this.next = m; 
	      }
	      
	      /* validMoveCheck method a) calls  validPosition to validate a,b for storing the grid position 
	      b) calculates the Manhattan distance between the last and new positions by adding the absolute
	      differences of their coordinates -  if integer is invalid or distance to the new Move is greater 
	      than the current fuel level outputs false */
	      public boolean validMoveCheck(int a, int b) {
	      if (!(validPosition(a,b))|| (Math.abs(pos1-a) + Math.abs(pos2-b)) > fuel)
	        return false; 
	      else  
	          return true;
	      }
	    }


	    private Move header; // This is a "ghost" Move
	    private Move last; // This should store the last actual Move, but not the tail (this is a real Move)
	    private int size = 1;
	  
	    // constructors follow per JAVA convention 
	    public TaxiMove(int pos1, int pos2) {
	    // Initialize with your definition of Move
	    header = new Move();
	    last = new Move(pos1,pos2);
	    header.setNext(last); //links header and the last since there are no other nodes in-between
	    }
	    
	    /* With no parameter, the linked list is a singly linked list with a simple element : cab at 0,0
	     with maximum fuel level (100) and 0 balance. */
	    public TaxiMove(){ 
	    this(0,0); //calls the overloaded constructor with the parameters (0,0)
	    }

	    public int size () { return size; }
	    
	    
	    public void fillGas() throws Exception {
	    //fillGas method a) adds and links a new Move with the same position as for the last Move
	      Move newMove = new Move(last.getX(), last.getY());
	      //b) deducts 0.5 per unit of gas that were filled 
	      double balance = last.getBalance();
	      if (balance > 0) { //if balance is negative cannot refill the fuel
	        if (balance < 50) { //if cannot fill the fuel to 100, fill the fuel wih current balance
	          newMove.setFuel((int) balance * 2);
	          newMove.setBalance(balance - (int)balance); 
	        } 
	        else { //1 fuel costs 0.5 balance units: can set fuel to 100 only if balance >= 50
	          newMove.setFuel(100);
	          newMove.setBalance (balance - 50); 
	        }
	        last.setNext(newMove); //link the newMove 
	        newMove.setNext(null);
	        last = newMove;
	        size++; 
	      }
	    }
	      
	    public void add(int a, int b) throws Exception  {
	      int blocks = (Math.abs(last.getX()-a) + Math.abs(last.getY()-b));
	      double balance = last.getBalance();
	      boolean refill = false;
	      //if the Move is not valid, validates the integers to store the position and checks whether
	      //after calling fillGas method fuel will be sufficient to move the blocks: a) or b) should be true
	      if (!last.validMoveCheck(a,b)) {
	        //a) the balance is sufficient for updating fuel level to maximum level of 100 or
	        if (last.validPosition(a, b) && balance > 50 ||
	          //b) fuel level will be sufficient after filling the fuel with available balance 
	            last.validPosition(a, b) && (balance < 50 && balance > 0) &&  (int) balance * 2 > blocks ) {
	          //if a) or b) is true, the fillGas method is called and refill variable is set to true
	          fillGas();
	          refill = true;
	          }
	          // if integers to store the position are invalid and a) and b) are both false, throws exception
	          else 
	            throw new IllegalArgumentException("Invalid Move");
	       }
	       if (last.validMoveCheck(a,b) || refill) { // if the Move is valid before or after calling fillGas
	        Move newMove= new Move (a,b); //create a new Move with specific coordinates
	        newMove.setFuel(last.getFuel() - blocks); //fuel level decreases per block moved
	        newMove.setBalance(balance + blocks * 0.8); //balance increases 0.8 per block moved
	        last.setNext(newMove); //new Move is linked and the last node is updated
	        newMove.setNext(null);
	        last = newMove;
	        size++;  
	      }
	    }
	      
	   
	    // method prints all moves in the linked list as a String
	    public void printMoves() {
	      String result = ""; 
	      Move currentMove = header.getNext(); 
	      while(currentMove != null){  //the last node in the singly linked list refers to null          
	        System.out.print("(" + currentMove.getX() + "," + currentMove.getY() + ")"); //print the coordinates 
	        if (currentMove.getNext() != null){ 
	          System.out.print(" → ");
	        }
	        currentMove = currentMove.getNext(); //traverse the singly linked list to print all the Moves
	      }
	      System.out.println();
	    }
	

	//Addition to the assignment: main method to check the class implementation

	public static void main (String [] args) throws Exception{
	TaxiMove t = new TaxiMove();
	t.add(3, 4);
	t.add(4, 5);
	t.add(2, 0);
	t.add(15, 27);
	t.add(20, 30);
	t.add(25, 36);
	t.add(35, 40);
	t.add(5, 40);
	t.add(0, 0);
	t.add(25, 50);


	t.printMoves();
	System.out.println("size: " + t.size());

	}


	
	    

}






	  

