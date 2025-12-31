package UNO;

public class Draw2Card extends CARD implements EFFECT {
    public Draw2Card(Color color) {
        super(color, Value.DRAW_TWO);
    }
    
   
    public boolean isPlayableOn(CARD card) {

        return this.getColor() == card.getColor() || 
               card.getValue() == Value.DRAW_TWO;
    }
    
    public void applyEffect(GAME game) {
        game.drawCards(2);
        game.skipNextPlayer();
    }
}
