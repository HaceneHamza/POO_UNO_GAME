package UNO;

public class WildDrawCard extends CARD implements EFFECT {
	public WildDrawCard() {
        super(Color.WILD, Value.WILD);
    }
    

    public boolean isPlayableOn(CARD card) {
        return true;
    }
    

    public void applyEffect(GAME game) {
        Color chosen = game.askPlayerForColor();
        chooseColor(chosen);
        game.nextTurn();
    }
    
    public void chooseColor(Color color) {
        if (color == Color.WILD) {
            throw new IllegalArgumentException("Must choose a real color");
        }
        setColor(color);
    }
    
}
