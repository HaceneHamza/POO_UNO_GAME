
package UNO;
import java.util.ArrayList;
import java.util.Scanner;
public class PLAYER {

	private String name; 
	private ArrayList<CARD> hand;
	private boolean isTurn;
	private boolean hasAnnouncedUNO; // Track if player said UNO when down to 1 card
	
	public PLAYER() {
		this.name = "";
		this.hand = new ArrayList<CARD>();
		this.isTurn = false;
		this.hasAnnouncedUNO = false;
	}
	
	public PLAYER(String name) {
		this.name = name;
		this.hand = new ArrayList<CARD>();
		this.isTurn = false;
		this.hasAnnouncedUNO = false;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
		
		public void setTurn(boolean isTurn)
		{
			this.isTurn = isTurn;
		}
		
		public String getName()
		{
			return this.name;
		}
		
		public ArrayList<CARD> getHand()
		{
			return this.hand;
		}
		
		public boolean isTurn()
		{
		  return this.isTurn;
		}
		
	    public boolean isWinner()
	    {
	    	return this.hand.isEmpty();
	    }
		
		public void removeCard(CARD card)
		{
			hand.remove(card);
		}
		
		public void drawCard(CARD card)
		{
			hand.add(card);
		}
		
	    public int getCardCount()
	    {
	    	return hand.size();
	    }
		

		
		public ArrayList<CARD> getPlayableCards()
		{
			ArrayList<CARD> list = new ArrayList<>();
			for (int i = 0; i<hand.size();i++)
			{
				CARD card = hand.get(i);
				//if() list.add(card);
			}
			return list;
		}
		
		public void playCard(CARD card)
		{
			removeCard(card);
			announceUno();
		}
		
		public CARD selectPlayableCard()
		{
			ArrayList<CARD> list = getPlayableCards();
			if(!list.isEmpty())
			{
				System.out.println("Choose a Card please ");
				for(int i = 0; i<list.size();i++)
				{
					System.out.println((i+1) + "_ " + list.get(i));
				}
				Scanner sc = new Scanner(System.in);
				int index = sc.nextInt();
				sc.close();
				return list.get(index - 1);
			}
			else return null;
		}
		
		private void announceUno()
		{
			if(hand.size() == 1)
			System.out.println("UNO");
		}
		
		public void setUNOAnnounced(boolean announced) {
			this.hasAnnouncedUNO = announced;
		}
		
		public boolean hasUNOAnnounced() {
			return this.hasAnnouncedUNO;
		}
		
		public boolean shouldPenalizeMissingUNO() {
			// If player has 1 card and hasn't announced UNO, they get penalized
			return hand.size() == 1 && !hasAnnouncedUNO;
		}
		
		
		
		
		
		
		
		
		
	

}

