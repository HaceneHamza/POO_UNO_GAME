package UNO;

public enum Value {
	ZERO(0), ONE(1), TWO(2), THREE(3), FOUR(4), 
    FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9),
    SKIP(-1), REVERSE(-1), DRAW_TWO(-1), WILD(-1), WILD_DRAW_FOUR(-1);

    private final int numVal;
    
    Value(int numVal) { this.numVal = numVal; }
    
    public static Value fromInt(int n) {
        for (Value v : Value.values()) 
        {
            if (v.numVal == n) return v;
        }
        return null;
    }
}