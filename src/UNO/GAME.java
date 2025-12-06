package UNO;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class GAME {
    private DECK deck;
    private ArrayList<PLAYER> players;
    private Stack<CARD> discard;
    private int currentPlayerIndex;
    private int direction; 

    public GAME(ArrayList<PLAYER> players) {
        this.players = players;
        this.deck = new DECK();
        this.discard = new Stack<CARD>();
        this.direction = 1;

        // Deal 7 cards to each player
        for (PLAYER p : players) {
            for (int i = 0; i < 7; i++) {
                p.drawCard(deck.draw());
            }
        }

        // Start the discard pile with one card
        CARD first = deck.draw();
        discard.push(first);

        // Set starting player
        this.currentPlayerIndex = 0;
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setTurn(i == currentPlayerIndex);
        }
    }

    public CARD getTopCard() {
        return discard.peek();
    }

    public PLAYER getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void nextTurn() {
        players.get(currentPlayerIndex).setTurn(false);
        currentPlayerIndex = (currentPlayerIndex + direction + players.size()) % players.size();
        players.get(currentPlayerIndex).setTurn(true);
    }

    public void reverseDirection() {
        direction = -direction;
        nextTurn();
    }

    public void skipNextPlayer() {
        nextTurn();
        nextTurn();
    }

    public void drawCards(int n) {
        int target = (currentPlayerIndex + direction + players.size()) % players.size();
        PLAYER p = players.get(target);
        for (int i = 0; i < n; i++) {
            CARD card = drawCardFromDeck();
            if (card != null) {
                p.drawCard(card);
            }
        }
    }

    public Color askPlayerForColor() {
        System.out.println(getCurrentPlayer().getName() + ", choose a color: 1.RED 2.BLUE 3.GREEN 4.YELLOW");
        Scanner sc = new Scanner(System.in);
        int choice = 1;
        try {
            choice = sc.nextInt();
        } catch (Exception e) {
            choice = 1;
        }
        switch (choice) {
            case 2:
                return Color.BLUE;
            case 3:
                return Color.GREEN;
            case 4:
                return Color.YELLOW;
            default:
                return Color.RED;
        }
        
    }

    public boolean playCard(PLAYER player, CARD card) {
        if (!card.isPlayableOn(getTopCard())) {
            return false;
        }

        player.playCard(card);
        discard.push(card);

        if (card instanceof EFFECT) {
            ((EFFECT) card).applyEffect(this);
        } else {
            nextTurn();
        }

        return true;
    }

    public DECK getDeck() {
        return deck;
    }

    public ArrayList<PLAYER> getPlayers() {
        return players;
    }
    
    public void checkAndApplyUNOPenalty(PLAYER player) {
        // Check if player has 1 card but didn't announce UNO
        if (player.shouldPenalizeMissingUNO()) {
            System.out.println("PENALTY: " + player.getName() + " forgot to say UNO! Drawing 2 penalty cards.");
            for (int i = 0; i < 2; i++) {
                CARD card = drawCardFromDeck();
                if (card != null) {
                    player.drawCard(card);
                }
            }
            player.setUNOAnnounced(false); // Reset for next time
        }
    }
    
    private CARD drawCardFromDeck() {
        CARD card = deck.draw();
        if (card == null) {
            // Reshuffle discard pile
            reshuffleDiscardIntoDeck();
            card = deck.draw();
        }
        return card;
    }
    
    private void reshuffleDiscardIntoDeck() {
        if (discard.size() <= 1) {
            System.out.println("ERROR: Not enough cards in discard pile to reshuffle!");
            return;
        }
        
        // Keep the top card, add the rest back to deck
        CARD topCard = discard.pop();
        ArrayList<CARD> cardsToReshufle = new ArrayList<>(discard);
        discard.clear();
        discard.push(topCard);
        
        System.out.println("Deck empty! Reshuffling discard pile into draw pile...");
        deck.addToDeck(cardsToReshufle);
    }
}
