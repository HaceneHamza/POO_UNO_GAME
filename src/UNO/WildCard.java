package UNO;

public class WildCard extends CARD implements EFFECT {
    public WildCard() {
        super(Color.WILD, Value.WILD);
    }
    
    @Override
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
