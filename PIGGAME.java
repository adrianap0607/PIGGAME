import java.util.Random;
import java.util.Scanner;

class Dice {
    private int faceValue;
    private final int maxValue;

    public Dice(int maxValue) {
        this.maxValue = maxValue;
        roll(); // Lanzar el dado al crearlo para tener un valor inicial
    }

    public void roll() {
        faceValue = new Random().nextInt(maxValue) + 1; // se genera un  valor aleatorio entre 1 y maxValue
    }

    public int getFaceValue() {
        return faceValue; // Devuelve el valor actual de la cara del dado
    }
}

class Player {
    private int score;

    public Player() {
        score = 0; //  el puntaje del jugador comienza en 0
    }

    public void rollDice(Dice d1, Dice d2) {
        d1.roll();
        d2.roll();

        int value1 = d1.getFaceValue();
        int value2 = d2.getFaceValue();

        if (value1 == 1 || value2 == 1) {
            score = 0; // Si alguno de los dados tiene valor 1, se debe de reiniciar el puntaje del jugador
        } else {
            score += value1 + value2; // Sumar el valor de los dados al puntaje del jugador
        }
    }

    public void stopRolling() {
        System.out.println("Puntos obtenidos: " + score); // Mostrar los puntos obtenidos en el turno de cada jugador 
    }

    public int getScore() {
        return score; // Devuelve el puntaje acumulado del jugador
    }

    public void resetScore() {
        score = 0; // Reiniciar el puntaje del jugador a 0
    }
}

class Game {
    private final Player[] players;
    private int currentPlayerIndex;
    private final Dice dice1;
    private final Dice dice2;
    private final Scanner scanner;

    public Game() {
        players = new Player[2];
        players[0] = new Player();
        players[1] = new Player();

        currentPlayerIndex = 0; // Comenzar con el primer jugador
        dice1 = new Dice(6); // Crear el primer dado con 6 caras
        dice2 = new Dice(6); // Crear el segundo dado con 6 caras
        scanner = new Scanner(System.in); // Inicializar el objeto Scanner para entrada de usuario
    }

    public void play() {
        while (players[0].getScore() < 100 && players[1].getScore() < 100) {
            Player currentPlayer = players[currentPlayerIndex];

            System.out.println("Turno del jugador " + (currentPlayerIndex + 1) + ":");

            System.out.println("¿Deseas ceder el turno o lanzar los dados?");
            System.out.println("1. Ceder el turno");
            System.out.println("2. Lanzar los dados");

            int choice = scanner.nextInt();
            if (choice == 2) {
                currentPlayer.rollDice(dice1, dice2); // Lanzar los dados y acumular puntos
                currentPlayer.stopRolling(); // Mostrar los puntos obtenidos en el turno
            }

            nextPlayer(); // Cambiar al siguiente jugador
        }

        Player winner = players[0].getScore() >= 100 ? players[0] : players[1];
        System.out.println("¡Felicidades, Jugador " + (winner == players[0] ? "1" : "2") + ", has ganado la partida!");

        System.out.println("¿Desean volver a jugar?");
        System.out.println("1. Sí");
        System.out.println("2. No");

        int playAgain = scanner.nextInt();
        if (playAgain == 1) {
            // Reiniciar el juego
            resetGame();
            play(); // Volver a jugar
        } else {
            System.out.println("¡Gracias por jugar!");
        }
    }

    private void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 2; // Cambiar al siguiente jugador en turno circularmente
    }

    private void resetGame() {
        for (Player player : players) {
            player.resetScore(); // se Reinicia el puntaje de todos los jugadores a 0
        }
    }
}

public class PIGGAME {
    public static void main(String[] args) {
        Game game = new Game();
        game.play(); // Comienza el juego
    }
}

