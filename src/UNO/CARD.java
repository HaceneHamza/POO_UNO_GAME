package UNO;

public abstract class CARD {
    private Color color;
    private Value value;
    
    public CARD(Color color, Value value) {
        this.color = color;
        this.value = value;
    }
    
    public Color getColor() {
        return color;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }
    
    public Value getValue() {
        return value;
    }
    
    public abstract boolean isPlayableOn(CARD card);
    

    public String toString() {
        return color + " " + value;
    }

}

