
package UNO;
import java.util.ArrayList;
import java.util.Scanner;
public class PLAYER {



		private String name; 
		private ArrayList<CARD> hand;
		private boolean isTurn;
		
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
				return list.get(index - 1);
			}
			else return null;
		}
		
		private void announceUno()
		{
			if(hand.size() == 1)
			System.out.println("UNO");
		}
		
		
		
		
		
		
		
		
		
	

}

