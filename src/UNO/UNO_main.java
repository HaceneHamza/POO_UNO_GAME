package UNO;

import java.util.ArrayList;
import java.util.Scanner;

public class UNO_main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("=== Welcome to UNO ===");
        System.out.println("This game requires exactly 4 players.");
        System.out.print("How many human players? (1-4): ");
        int humanPlayers = 2;
        try {
            humanPlayers = sc.nextInt();
            sc.nextLine();
            if (humanPlayers < 1 || humanPlayers > 4) {
                humanPlayers = 2;
            }
        } catch (Exception e) {
            humanPlayers = 2;
            sc.nextLine();
        }
        
        // Create players
        ArrayList<PLAYER> players = new ArrayList<>();
        
        // Add human players
        for (int i = 0; i < humanPlayers; i++) {
            System.out.print("Enter Player " + (i + 1) + " name: ");
            String name = sc.nextLine().trim();
            if (name.isEmpty()) {
                name = "Player " + (i + 1);
            }
            PLAYER p = new PLAYER(name);
            players.add(p);
        }
        
        // Fill remaining spots with bots
        int botsNeeded = 4 - humanPlayers;
        for (int i = 0; i < botsNeeded; i++) {
            BOT bot = new BOT("Bot-" + (i + 1));
            players.add(bot);
        }
        
        System.out.println("\n=== Game Started ===");
        System.out.println("Players: ");
        for (PLAYER p : players) {
            String botLabel = (p instanceof BOT) ? " (BOT)" : "";
            System.out.println("  - " + p.getName() + botLabel);
        }
        System.out.println();
        
        // Initialize game
        GAME game = new GAME(players);
        
        // Game loop
        boolean gameOver = false;
        while (!gameOver) {
            PLAYER current = game.getCurrentPlayer();
            boolean isBot = current instanceof BOT;
            
            System.out.println("\n--- " + current.getName() + "'s Turn ---");
            System.out.println("Top card: " + game.getTopCard());
            System.out.println("Your hand (" + current.getCardCount() + " cards):");
            
            ArrayList<CARD> hand = current.getHand();
            for (int i = 0; i < hand.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + hand.get(i));
            }
            
            if (isBot) {
                // BOT's turn - automatic play
                BOT bot = (BOT) current;
                CARD card = bot.selectPlayableCard(game.getTopCard());
                
                if (card != null) {
                    game.playCard(bot, card);
                    System.out.println(bot.getName() + " played: " + card);
                    
                    // Check if bot won immediately
                    if (bot.isWinner()) {
                        System.out.println("\n=== " + bot.getName() + " wins! ===");
                        gameOver = true;
                    } else if (bot.getCardCount() == 1) {
                        bot.setUNOAnnounced(true); // Bot auto-announces
                        System.out.println(bot.getName() + " said UNO!");
                    }
                } else {
                    CARD drawn = game.getDeck().draw();
                    if (drawn != null) {
                        bot.drawCard(drawn);
                        System.out.println(bot.getName() + " drew a card.");
                    }
                    game.nextTurn();
                }
            } else {
                // Human player's turn
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
                            // Draw a card
                            CARD drawn = game.getDeck().draw();
                            if (drawn != null) {
                                current.drawCard(drawn);
                                System.out.println(current.getName() + " drew a card.");
                            } else {
                                System.out.println("ERROR: No cards available!");
                            }
                            
                            game.nextTurn();
                            turnComplete = true;
                        } else {
                            // Try to play the selected card
                            CARD card = hand.get(choice - 1);
                            if (!game.playCard(current, card)) {
                                throw new IllegalArgumentException("This card cannot be played on " + game.getTopCard() + "!");
                            }
                            System.out.println(current.getName() + " played: " + card);
                            
                            // Check if player won immediately
                            if (current.isWinner()) {
                                System.out.println("\n=== " + current.getName() + " wins! ===");
                                gameOver = true;
                                turnComplete = true;
                            } else if (current.getCardCount() == 1) {
                                // If down to 1 card, ask for UNO announcement
                                System.out.print("You have 1 card left! Say UNO? (yes/no): ");
                                String unoResponse = sc.nextLine().trim().toLowerCase();
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
                        
                    } catch (IllegalArgumentException e) {
                        System.out.println("ERROR: " + e.getMessage() + " Try again.");
                    } catch (Exception e) {
                        System.out.println("ERROR: Invalid input! Try again.");
                        sc.nextLine();
                    }
                }
            }
            
            // Check for UNO penalty on other players before next turn (only if game isn't over)
            if (!gameOver) {
                for (PLAYER p : players) {
                    if (p != current) {
                        game.checkAndApplyUNOPenalty(p);
                    }
                }
            }
            
            // Check for winner (if not already detected)
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
        
        System.out.println("\nThanks for playing UNO!");
    }
}


