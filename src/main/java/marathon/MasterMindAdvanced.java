package marathon;

import java.util.Random;
import java.util.Scanner;

public class MasterMindAdvanced {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Random rand = new Random();

        int codeLength = 4; // długość kodu do odgadnięcia
        int maxDigit = 6; // maxymalny zakres cyfr z kodu do odgadnięcia
        int[] secretCode = new int[codeLength]; // tablica zawierająca tajny kod
        int[] userCode = new int[codeLength]; //tablica przechowuje strzał gracza
        boolean guessedStatus = false;

        int guessLimit = 0;
        boolean advancedMode = false;

        System.out.println("Witaj! Zagrajmy w MasterMind!");
        System.out.println("Twoim zadaniem jest zgadnięcie kody składającego się z: " + codeLength + " cyfr od 1 do " + maxDigit);

        System.out.println("Wybierz tryb: \n 1.Prosty, bez ograniczonej ilości strzałów \n 2.Zaawansowany, z ograniczoną ilością strzałów");

        String gameModeUserSelector = scan.nextLine();
        try {
            int gameMode = Character.getNumericValue(gameModeUserSelector.charAt(0)); //wybieranie trybu gry
            while (!(gameMode == 1 || gameMode == 2)) {
                System.out.println("Niepoprawny tryb gry, wybierz tryb gry wpisując 1 lub 2");
                gameModeUserSelector = scan.nextLine();
                gameMode = Character.getNumericValue(gameModeUserSelector.charAt(0));
            }
            if (gameMode == 2) { //deklarowanie ilosci strzałów
                advancedMode = true;
                System.out.println("Podaj limit strzałów:");
                while (guessLimit <= 0) {
                    String guessLimitInput = scan.nextLine();
                    try {
                        guessLimit = Integer.parseInt(guessLimitInput);
                    } catch (NumberFormatException e) {
                        System.out.println("Wpisano niepoprawną wartość, wybierz ilość strzałów wpisując liczbę:");
                    }
                    if (guessLimit <= 0) {
                        System.out.println("Wpisano niepoprawną wartość, wybierz ilość strzałów wpisując liczbę dodatnią:");
                    }
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Niepoprawny tryb gry, wybierz tryb wpisując 1 lub 2");
        }

        System.out.println("Powodzenia!");


        for (int i = 0; i < codeLength; i++) {  //ta pętla generuje "tajny kod" który gracz musi odgadnąć
            secretCode[i] = rand.nextInt(maxDigit) + 1;
        }

        System.out.println();

        while ((!guessedStatus && !advancedMode) || (!guessedStatus && advancedMode && guessLimit > 0)) {
            System.out.println("Podaj swoją próbę: ");
            String playerGuess = scan.nextLine(); //zmienna zawierająca strzał grasza w formacie string

            try {
                if (playerGuess.length() != codeLength) {
                    throw new NumberFormatException();
                }
                for (int i = 0; i < codeLength; i++) {
                    userCode[i] = Character.getNumericValue(playerGuess.charAt(i));
                    if (userCode[i] < 1 || userCode[i] > maxDigit) {
                        throw new NumberFormatException();
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Niepoprawny format kodu! Wprowadź " + codeLength + " cyfr z zakresu od 1 do " + maxDigit);
                continue;
            }

            int correctNumberWrongPosition = 0;
            int correctNumberCorrectPosition = 0;
            boolean[] countedInUserCode = new boolean[codeLength];
            boolean[] countedInSecretCode = new boolean[codeLength];

            for (int i = 0; i < codeLength; i++) {  //sprawdzenie czy cyfra została odgadnięta na dobrym miejscu
                if (userCode[i] == secretCode[i]) {
                    correctNumberCorrectPosition++;
                    countedInSecretCode[i] = true;
                    countedInSecretCode[i] = true;
                }
            }
            for (int i = 0; i < codeLength; i++) { //sprawdzenie czy cyfra została odgadnięta na złym miejscu
                if (!countedInUserCode[i]) {
                    for (int j = 0; j < codeLength; j++) {
                        if (!countedInSecretCode[j] && userCode[i] == secretCode[j]) {
                            correctNumberWrongPosition++;
                            countedInUserCode[i] = true;
                            countedInSecretCode[j] = true;

                        }
                    }
                }
            }

            if (correctNumberCorrectPosition == codeLength) {
                System.out.println("Wygrana - kod odgadnięty!");
                guessedStatus = true;
            } else{
                System.out.println("Poprawne cyfry na poprawnej pozycji: " + correctNumberCorrectPosition);
                System.out.println("Poprawne cyfry na niepoprawnych pozycjach " + correctNumberWrongPosition);
                guessLimit -= 1;
                if (advancedMode && guessLimit == 0) {
                    System.out.println("Przegrana - Wykorzystałeś wszystkie swoje strzały");
                    System.out.println("Tajnym kodem było:");
                    for (int i = 0; i < codeLength; i++) {
                        System.out.print(secretCode[i]);
                    }
                }else if (advancedMode){
                System.out.println("Pozostało: "+ guessLimit + " strzałów");
                }
            }
        }

        scan.close();
    }
}