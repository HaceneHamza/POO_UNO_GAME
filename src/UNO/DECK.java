package UNO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class DECK {
    private Stack<CARD> cards;
    
    public DECK() {
        this.cards = new Stack<CARD>();
        initializeDeck(); //Initialize deck on creation
    }
    
    public void initializeDeck() {
        // Add number cards for each color
        for (Color color : Color.values()) {
            if (color == Color.WILD) continue;
            // Add number 0 card (only one per color)
            cards.push(new NumberCard(color, Value.ZERO));
            
            // Add numbers 1-9 (each twice)
            for (int number = 1; number <= 9; number++) {
                Value val = Value.values()[number];
                cards.push(new NumberCard(color, val));
                cards.push(new NumberCard(color, val));
            }
        }
        
        // Add action cards for each color (each twice)
        for (Color color : Color.values()) {
            if (color == Color.WILD) continue;
            // Skip cards
            cards.push(new SkipCard(color));
            cards.push(new SkipCard(color));
            
            //  Draw2 cards
            cards.push(new Draw2Card(color));
            cards.push(new Draw2Card(color));
            
            // Reverse cards
            cards.push(new ReverseCard(color));
            cards.push(new ReverseCard(color));
        }
        
        // Add wild cards (4 of each type)
        for (int i = 0; i < 4; i++) {
            cards.push(new WildCard());
            cards.push(new WildDraw4Card());
        }
        
        shuffle(); //Shuffle the cards
    }
    
    public void shuffle() {
        Collections.shuffle(cards); //Use built-in shuffle function
    }
    
    public CARD draw() {
        //Check if the deck is empty
        if (cards.isEmpty()) {
            return null; // Return null, let GAME handle reshuffle from discard
        }
        return cards.pop(); //Draw and return the top card from the deck
    }
    
    public void addToDeck(ArrayList<CARD> cardsToAdd) {
        for (CARD card : cardsToAdd) {
            cards.push(card);
        }
        shuffle();
    }
}
