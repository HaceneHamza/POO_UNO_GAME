package UNO;

import java.util.ArrayList;

public class BOT extends PLAYER {
    
    public BOT(String name) {
        super(name);
    }
    
    public CARD selectPlayableCard(CARD topCard) {
        ArrayList<CARD> hand = getHand();
  
        for (CARD card : hand) {
            if (card.isPlayableOn(topCard)) {
                return card;
            }
        }
        
      
        return null;
    }
}
