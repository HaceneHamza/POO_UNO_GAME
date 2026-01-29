package UNO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class DECK {
    private Stack<CARD> cards;
    
    public DECK() {
        this.cards = new Stack<CARD>();
        initializeDeck(); 
    }
    
   public void initializeDeck() {
     
        for (Color color : Color.values()) {
            if (color == Color.WILD) continue;
            
            cards.push(new NumberCard(color, Value.ZERO));
            
        
            for (int number = 1; number <= 9; number++) {
                Value val = Value.fromInt(number);
                cards.push(new NumberCard(color, val));
                cards.push(new NumberCard(color, val));
            }
         
            
            
            
            cards.push(new SkipCard(color));
            cards.push(new SkipCard(color));
            
            
            cards.push(new Draw2Card(color));
            cards.push(new Draw2Card(color));
            
           
            cards.push(new ReverseCard(color));
            cards.push(new ReverseCard(color));
        
        }
        
       
        
        
        for (int i = 0; i < 4; i++) {
            cards.push(new WildCard());
            cards.push(new WildDraw4Card());
        }
        
        shuffle(); 
    }
    
    public void shuffle() {
        Collections.shuffle(cards); 
    }
    public CARD draw() {
        
        if (cards.isEmpty()) {
            return null; 
        }
        return cards.pop();
    }
    
    public void addToDeck(ArrayList<CARD> cardsToAdd) {
        for (CARD card : cardsToAdd) {
            cards.push(card);
        }
        shuffle();
    }
}
