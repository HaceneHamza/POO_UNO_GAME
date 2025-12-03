import java.util.Collections;
import java.util.Stack;

import javax.smartcardio.Card;

public class DECK {
    private Stack<Card> cards;
    
    public DECK() {
        this.cards = new Stack<Card>();
        initializeDeck(); //Initialize deck on creation
    }
    
    public void initializeDeck() {
        // Add number cards for each color
        for (Color color : Color.values()) {
            // Add number 0 card (only one per color)
            cards.push(new NumberCard(color, 0));
            
            // Add numbers 1-9 (each twice)
            for (int number = 1; number <= 9; number++) {
                cards.push(new NumberCard(color, number));
                cards.push(new NumberCard(color, number));
            }
        }
        
        // Add action cards for each color (each twice)
        for (Color color : Color.values()) {
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
            cards.push(new WildDrawCard());
        }
        
        // for (int i = 0; i < 4; i++) {
        //     cards.push(new WildDrawCard());
        // }
        
        shuffle(); //Shuffle the cards
    }
    
    public void shuffle() {
        Collections.shuffle(cards); //Use built-in shuffle function
    }
    
    public Card draw() {
        //Check if the deck is empty
        if (cards.isEmpty()) {
            initializeDeck();
            // In this design, when the deck is empty we create a completely new set of cards
            // because we don't maintain a separate discard pile 
            // we have the shuffle function inside the initializeDeck function
        }
        return cards.pop(); //Draw and return the top card from the deck

    }
}