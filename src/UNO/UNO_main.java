package UNO;

import java.util.ArrayList;
import java.util.Scanner;

public class UNO_main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        

        System.out.println("=== Welcome to UNO ===");
        System.out.print("How many players? (2-10): ");
        int numPlayers = 2;
        try {
            numPlayers = sc.nextInt();
            sc.nextLine(); 
            if (numPlayers < 2 || numPlayers > 10) {
                numPlayers = 2;
            }
        } catch (Exception e) {
            numPlayers = 2;
        }
        
       
        ArrayList<PLAYER> players = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter Player " + (i + 1) + " name: ");
            String name = sc.nextLine().trim();
            if (name.isEmpty()) {
                name = "Player " + (i + 1);
            }
            PLAYER p = new PLAYER(name);
            players.add(p);
        }
        
      
        GAME game = new GAME(players);
        
        System.out.println("\n=== Game Started ===\n");
        
     
        boolean gameOver = false;
        while (!gameOver) {
            PLAYER current = game.getCurrentPlayer();
            
            System.out.println("\n--- " + current.getName() + "'s Turn ---");
            System.out.println("Top card: " + game.getTopCard());
            System.out.println("Your hand (" + current.getCardCount() + " cards):");
            
            ArrayList<CARD> hand = current.getHand();
            for (int i = 0; i < hand.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + hand.get(i));
            }
            
          
            boolean turnComplete = false;
            while (!turnComplete) {
                System.out.print("Play a card (1-" + hand.size() + ") or 0 to draw: ");
                int choice = 0;
                try {
                    choice = sc.nextInt();
                    sc.nextLine();
                    
                    if (choice < 0 || choice > hand.size()) {
                        throw new IllegalArgumentException("Choice must be between 0 and " + hand.size());
                    }
                    
                    if (choice == 0) {
          
                        CARD drawn = game.getDeck().draw();
                        current.drawCard(drawn);
                        System.out.println(current.getName() + " drew a card.");
                        game.nextTurn();
                        turnComplete = true;
                    } else {
                
                        CARD card = hand.get(choice - 1);
                        if (!game.playCard(current, card)) {
                            throw new IllegalArgumentException("This card cannot be played on " + game.getTopCard() + "!");
                        }
                        System.out.println(current.getName() + " played: " + card);
                        turnComplete = true;
                    }
                    
                } catch (IllegalArgumentException e) {
                    System.out.println("ERROR: " + e.getMessage() + " Try again.");
                } catch (Exception e) {
                    System.out.println("ERROR: Invalid input! " + e.getMessage() + " Try again.");
                    sc.nextLine(); 
                }
            }
            
            // Check for winner
            for (PLAYER p : players) {
                if (p.isWinner()) {
                    System.out.println("\n=== " + p.getName() + " wins! ===");
                    gameOver = true;
                    break;
                }
            }
        }
        
        System.out.println("\nThanks for playing UNO!");
    }
}

