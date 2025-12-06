package UNO;

import java.util.ArrayList;

public class BOT extends PLAYER {
    
    public BOT(String name) {
        super(name);
    }
    
    /**
     * BOT's strategy: Pick the first playable card, or draw if none available
     */
    public CARD selectPlayableCard(CARD topCard) {
        ArrayList<CARD> hand = getHand();
        
        // Try to find a playable card
        for (CARD card : hand) {
            if (card.isPlayableOn(topCard)) {
                return card;
            }
        }
        
        // No playable card found
        return null;
    }
}
