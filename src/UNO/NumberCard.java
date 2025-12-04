package UNO;

public class NumberCard extends CARD{
    public NumberCard(Color color, Value value) {
        super(color, value);
        if (value.ordinal() > Value.NINE.ordinal()) {
            throw new IllegalArgumentException("NumberCard must have a numeric value (0-9)");
        }
    }
    
    
    public boolean isPlayableOn(CARD card) {

        return this.getColor() == card.getColor() || 
               this.getValue() == card.getValue();
    }
}


