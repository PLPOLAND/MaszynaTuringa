package turing;

import java.io.IOException;

import turing.maszyna.MaszynaTuringa;

public class Main {
    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        MaszynaTuringa maszyna = null;
        if (args.length>0) {//wczytaj ścieżkę pliku z argumentów
            maszyna = Tools.loadFromFile(args[0]);
        }
        else {
            maszyna = Tools.loadFromFile("inputFiles/zad32.txt");
        }

        int mode = wybierzTryb()- '0';

        int iteracjaObliczenia = 0;
        if(mode == 3)
            Tools.hitAKkey("Naciśnij ENTER, aby wykonać kolejne obliczenie",true);
        else
            Tools.hitAKkey("Naciśnij ENTER, aby rozpocząć działanie",true);

        while (!maszyna.ended()) {
            iteracjaObliczenia++;
            try {

                if (mode == 3) {
                    Tools.hitAKey();
                    maszyna.wykonajPrzejscie();
                } 
                else if (mode != 4 && iteracjaObliczenia > 1000) {
                    Tools.pause(1);
                    maszyna.wykonajPrzejscie();
                } 
                else if (mode == 4) {
                    maszyna.wykonajPrzejscie();
                } 
                else if (mode == 2) {
                    Tools.pause(25);
                    maszyna.wykonajPrzejscie();
                } 
                else {
                    Tools.pause(200);
                    maszyna.wykonajPrzejscie();
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                Tools.hitAKkey("Naciśnij ENTER, aby zakończyć program", true);
                System.exit(0);
            }
        }

        System.out.println("Maszyna zakończyła obliczenia!");
        System.out.print("Obliczenie zakończone po: " +iteracjaObliczenia);
        if (iteracjaObliczenia == 1) {
            System.out.println(" iteracji");
        } else {
            System.out.println(" iteracjach");
        }

        System.out.println("Słowo wejściowe: "+ maszyna.getSlowoWejsciowe());
        System.out.println("Słowo obliczone: " + maszyna.getSlowoObliczone());


        Tools.hitAKkey("Naciśnij ENTER, aby zakończyć program",true);
        System.exit(0);
    }
    /**
     * Prosi użytkownika o podanie trybu w jakim ma uruchomić się maszyna Turinga
     * @return
     */
    private static int wybierzTryb() {

        System.out.println("Uruchom w trybie: ");
        System.out.println("1\tzwyklym");
        System.out.println("2\tprzyspieszonym");
        System.out.println("3\tkrokowym");
        System.out.println("4\tultra przyspieszonym");

        try {
            int tmp = System.in.read();
            while (System.in.available()!=0) {
                System.in.read();
            }
            return tmp;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
