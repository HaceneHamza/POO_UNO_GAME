package UNO;

public class ReverseCard extends CARD {
    public ReverseCard(Color color) {
        super(color, Value.REVERSE);
    }
    
    @Override
    public boolean isPlayableOn(CARD card) {

        return this.getColor() == card.getColor() || 
               card.getValue() == Value.REVERSE;
    }
    public void applyEffect(GAME game) {
        game.reverseDirection();
    }
}
