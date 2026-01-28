
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
		

		
		public void playCard(CARD card)
		{
			removeCard(card);
			announceUno();
		}
		
		public boolean isHasPlayableCards(CARD card)
		{
			int playableCard = 0;
			for (int i = 0; i<hand.size();i++)
			{
				if(hand.get(i).isPlayableOn(card)) playableCard++;
			}
			return playableCard > 0;
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

