package UNO;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.Random;

public class GAME {
    private DECK deck;
    private ArrayList<PLAYER> players;
    private Stack<CARD> discard;
    private int currentPlayerIndex;
    private int direction; 

    
    private void  initializeGame() {
        this.deck = new DECK();
        this.discard = new Stack<CARD>();
        this.direction = 1;

        // tserbi lcartat
        for (PLAYER p : players) {
            for (int i = 0; i < 7; i++) {
                p.drawCard(deck.draw());
            }
        }

        // initialize the deck
        CARD first = deck.draw();
        discard.push(first);

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

    public Color askPlayerForColor(PLAYER player) {
        System.out.println(getCurrentPlayer().getName() + ", choose a color: 1.RED 2.BLUE 3.GREEN 4.YELLOW");
        Random rand = new Random();
        int choice = 5;
        if(player instanceof BOT) 
        {
        	int randomInt = rand.nextInt(3) + 1; 
        	choice = randomInt;
        }
        else {
        Scanner sc = new Scanner(System.in);
        
        while(choice > 4 || choice < 1)
        {
        	try {
        	choice = sc.nextInt();
            sc.nextLine();
            if (choice < 1 || choice > 4) {
            	throw new IllegalArgumentException("choice must be between 1 and 4!");
            }
        	 }  
        	 catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + " Try again");
        	 }
        	 catch (Exception e) {
                 System.out.println("Invalid input! Try again.");
                 sc.nextLine();
             }
        }
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
        if (player.shouldPenalizeMissingUNO()) {
            System.out.println("penalty: " + player.getName() + " forgot to say UNO! drawing 2 cards.");
            for (int i = 0; i < 2; i++) {
                CARD card = drawCardFromDeck();
                if (card != null) {
                    player.drawCard(card);
                }
            }
            player.setUNOAnnounced(false);
        }
    }
    
    private CARD drawCardFromDeck() {
        CARD card = deck.draw();
        if (card == null) {
            reshuffleDiscardIntoDeck();
            card = deck.draw();
        }
        return card;
    }
    
    private void reshuffleDiscardIntoDeck() {
        if (discard.size() <= 1) {
            System.out.println("Not enough cards in discard pile to reshuffle!");
            return;
        }
        
        // keep the top card and reshuffle the rest
        CARD topCard = discard.pop();
        ArrayList<CARD> cardsToReshufle = new ArrayList<>(discard);
        discard.clear();
        discard.push(topCard);
        
        System.out.println("The Deck is empty! reshuffling cards from discard pile into draw pile");
        deck.addToDeck(cardsToReshufle);
    }
    
    public void setup()
    {
    	Scanner sc = new Scanner(System.in);
        
        System.out.println("===== Welcome to UNO =====");
        System.out.println("This game requires 4 players.");
        System.out.print("choose how many human players? (1-4): ");
        int humanPlayers = 7; // SUIIIIII
        while(humanPlayers > 4 || humanPlayers < 1)
        {
        	 try {
        	humanPlayers = sc.nextInt();
            sc.nextLine();
            if (humanPlayers < 1 || humanPlayers > 4) {
            	throw new IllegalArgumentException("Choice must be between 1 and 4 ");
            }
        	 }  
        	 catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + " Try again.");
        	 }
        	 catch (Exception e) {
                 System.out.println("Invalid input! try again.");
                 sc.nextLine();
             }
        }
       
        	players =  new ArrayList<>();
        // adding human players 
        for (int i = 0; i < humanPlayers; i++) {
            System.out.print("Enter Player " + (i + 1) + " name: ");
            String name = sc.nextLine();
            if (name.isEmpty()) {
                name = "Player " + (i + 1);
            }
            PLAYER p = new PLAYER(name);
            players.add(p);
        }
        
        // filling the rest with bots
        int botsNeeded = 4 - humanPlayers;
        for (int i = 0; i < botsNeeded; i++) {
            BOT bot = new BOT("Bot-" + (i + 1));
            players.add(bot);
        }

        initializeGame();
    }
    
    public void play()
    {
    	Scanner sc = new Scanner(System.in);
    	
    	System.out.println("\n===== Game Started =====");
        System.out.println("Players: ");
        for (PLAYER p : players) {
            String botLabel = (p instanceof BOT) ? " (BOT)" : "";
            System.out.println("  - " + p.getName() + botLabel);
        }
        System.out.println();
        
        
        // loop ta3 l game
        boolean gameOver = false;
        while (!gameOver) {
            PLAYER current = this.getCurrentPlayer();
            boolean isBot = current instanceof BOT;
            
            System.out.println("\n--- " + current.getName() + "'s Turn ---");
            System.out.println("Top card: " + this.getTopCard());
            System.out.println("Your hand (" + current.getCardCount() + " cards):");
            
            ArrayList<CARD> hand = current.getHand();
            for (int i = 0; i < hand.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + hand.get(i));
            }
            
            if (isBot) {
                // if it's bot's turn ? play automatically
                BOT bot = (BOT) current;
                CARD card = bot.selectPlayableCard(this.getTopCard());
                
                if (card != null) {
                	this.playCard(bot, card);
                    System.out.println(bot.getName() + " played: " + card);
                    
                    // check if bot won
                    if (bot.isWinner()) {
                        System.out.println("\n=== " + bot.getName() + " wins! ===");
                        gameOver = true;
                    } else if (bot.getCardCount() == 1) {
                        bot.setUNOAnnounced(true); // bot automatically says UNO 
                        System.out.println(bot.getName() + " said UNO!");
                    }
                } else {
                    CARD drawn = this.getDeck().draw();
                    if (drawn != null) {
                        bot.drawCard(drawn);
                        System.out.println(bot.getName() + " drew a card.");
                    }
                    this.nextTurn();
                }
            } else {
                // ida machi bot , human yal3ab
                boolean turnComplete = false;
                while (!turnComplete) {
                    System.out.print("Play a card (1-" + hand.size() + ") : ");
                    int choice = 0;
                    try {
                    	if (!current.isHasPlayableCards(this.getTopCard())) {
                            // Draw a card
                            CARD drawn = this.getDeck().draw();
                            if (drawn != null) {
                                current.drawCard(drawn);
                                System.out.println(current.getName() + " drew a card.");
                            } else {
                                System.out.println("No cards available!");
                            }
                            
                            this.nextTurn();
                            turnComplete = true;
                        }
                        else { 
                        	 choice = sc.nextInt();
                             sc.nextLine();
                        	if (choice < 1 || choice > hand.size()) {
                            throw new IllegalArgumentException("Choice must be between 1 and " + hand.size());
                        }
                        else {
                            // try to play the selected card
                            CARD card = hand.get(choice - 1);
                            if (!this.playCard(current, card)) {
                                throw new IllegalArgumentException("This card cannot be played on " + this.getTopCard());
                            }
                            System.out.println(current.getName() + " played: " + card);
                            
                            // check if the player has won
                            if (current.isWinner()) {
                                System.out.println("\n=== " + current.getName() + " wins! ===");
                                gameOver = true;
                                turnComplete = true;
                            } else if (current.getCardCount() == 1) {
                                // if one card remained 
                                System.out.print("You have 1 card left!");
                                String unoResponse = sc.nextLine();
                                if (unoResponse.equals("yes") || unoResponse.equals("y")) {
                                    current.setUNOAnnounced(true);
                                    System.out.println("UNO!");
                                } else {
                                    current.setUNOAnnounced(false);
                                }
                                turnComplete = true;
                            } else {
                                turnComplete = true;
                            }
                        }
                        }
                        
                    } catch (IllegalArgumentException e) {
                        System.out.println("ERROR: " + e.getMessage() + " Try again.");
                    } catch (Exception e) {
                        System.out.println("ERROR: Invalid input! Try again.");
                        sc.nextLine();
                    }
                }
            }
            
            // check for penalty 
            if (!gameOver) {
                for (PLAYER p : players) {
                    if (p != current) {
                    	this.checkAndApplyUNOPenalty(p);
                    }
                }
            }
            
            // checking for l winner
            if (!gameOver) {
                for (PLAYER p : players) {
                    if (p.isWinner()) {
                        System.out.println("\n=== " + p.getName() + " wins! ===");
                        gameOver = true;
                        break;
                    }
                }
            }
        }
        
        System.out.println("thanks for playing UNO!");
    }
}
