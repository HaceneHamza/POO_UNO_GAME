package UNO;

public class WildDraw4Card extends CARD implements EFFECT {
    public WildDraw4Card() {
        super(Color.WILD, Value.WILD_DRAW_FOUR);
    }
    
    
    public boolean isPlayableOn(CARD card) {

        return true;
    }
    
    
    public void applyEffect(GAME game) {
    	
        Color chosen = game.askPlayerForColor();
        chooseColor(chosen);
        
        game.drawCards(4);
        game.skipNextPlayer();
    }
    
    public void chooseColor(Color color) {
        if (color == Color.WILD) {
            throw new IllegalArgumentException("Must choose a real color");
        }
        setColor(color);
    }
}
