package UNO;

public class WildCard extends CARD implements EFFECT {
	public WildCard() {
        super(Color.WILD, Value.WILD);
    }
    

    public boolean isPlayableOn(CARD card) {
        return true;
    }
    

    public void applyEffect(GAME game) {
        Color chosen = game.askPlayerForColor(game.getCurrentPlayer());
        chooseColor(chosen);
        game.nextTurn();
    }
    
    public void chooseColor(Color color) {
        if (color == Color.WILD) {
            throw new IllegalArgumentException("you have to choose a valid color!");
        }
        setColor(color);
    }
    
}
