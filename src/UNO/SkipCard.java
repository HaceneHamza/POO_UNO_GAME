package UNO;

public class SkipCard extends CARD implements EFFECT {
    public SkipCard(Color color) {
        super(color, Value.SKIP);
    }
    
    
    public boolean isPlayableOn(CARD card) {

        return this.getColor() == card.getColor() || 
               card.getValue() == Value.SKIP;
    }
    
    
    
    public void applyEffect(GAME game) {
        game.skipNextPlayer();
    }
}
