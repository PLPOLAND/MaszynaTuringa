package turing;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;

import turing.maszyna.MaszynaTuringa;

public class Tools {
 
    private Tools(){}
    
    /**
     * Pauzuje wykonanie kodu na podany czas
     * @param time czas podany w milisekundach
     */
    public static void pause(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * Wczytuje Maszynę Turniga z pliku o pdanej ścieżce
     * @param fileName Ścieżka pliku
     * @return obiekt reprezentujący maszynę turninga wczytaną z podanego pliku
     */
    public static MaszynaTuringa loadFromFile(String fileName) {
        
        
        FileInputStream fis;
        String data = null;
        try {
            fis = new FileInputStream(fileName);
            data = IOUtils.toString(fis, StandardCharsets.UTF_8);
        } catch (FileNotFoundException e) {
            System.err.println("Plik o ścieżce: "+fileName+"nie został znaleziony! Sprawdź czy podana ścieżka jest prawidłowa! \n Koniec Programu");
            System.exit(-1);
        } catch(IOException e1){
            e1.printStackTrace();            
            System.exit(-1);
        }

        
        String[] lines = data.split("\n");
        
        for (int i =0; i<lines.length;i++) {
            lines[i] = lines[i].replaceAll("[\\p{C}]", "");
        }
        
        MaszynaTuringa maszynaTuringa = new MaszynaTuringa();
        
        ArrayList<String> alfabetTasmowy = new ArrayList<>();
        ArrayList<String> alfabetWejsciowy = new ArrayList<>();
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].equals("alfabet tasmowy:")) {
                i++;
                int j = 0;
                while (j < lines[i].length()) {
                    alfabetTasmowy.add(lines[i].charAt(j)+"");
                    j++;
                }
            } else if(lines[i].equals("alfabet wejsciowy:")) {
                i++;
                int j = 0;
                while (j < lines[i].length()) {
                    alfabetWejsciowy.add(lines[i].charAt(j) + "");
                    j++;
                }
            }else if(lines[i].equals("slowo wejsciowe:")){
                i++;


                for (int j = 0; j < lines[i].length(); j++) {
                    if (!alfabetWejsciowy.contains(lines[i].charAt(j) + "")) {
                        System.err.println("Słowo wejsciowe zawiera znak z poza alfabetu wejsciowego");
                        System.exit(1);
                    }
                }

                maszynaTuringa.setSlowoWejsciowe(lines[i]);
            } else if(lines[i].equals("stany:")){
                i++;
                String[] stany = lines[i].split(" ");
                for (int j = 0; j < stany.length; j++) {
                    maszynaTuringa.addStan(stany[j]);
                }
            } else if(lines[i].equals("stan poczatkowy:")){
                i++;
                maszynaTuringa.setStanPoczatkowy(lines[i]);
                
            }else if(lines[i].equals("stany akceptujace:")){
                i++;
                String[] stany = lines[i].split(" ");
                for (int j = 0; j < stany.length; j++) {
                    maszynaTuringa.setStanAkceptujacy(stany[j]);
                }
            }else if(lines[i].equals("relacja przejscia:")){
                i++;
                String[] przejscia = new String[lines.length-i];
                for (int j = 0; j < przejscia.length; j++ , i++) {
                    przejscia[j] = lines[i];
                }
                for (String przejscie : przejscia) {
                    try {
                        maszynaTuringa.addPrzejscie(przejscie, alfabetTasmowy, alfabetTasmowy);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                        System.exit(1);
                    }
                }
            } else{
                i++;
            }

            
        }
        
        return maszynaTuringa;
    }
    /**
     * Zatrzymuje działanie programu do czasu wciśniecia dowolnego klawisza.
     * 
     */
    public static void hitAKey() {
        hitAKkey("",false);
    }
    /**
     * Zatrzymuje działanie programu do czasu wciśniecia dowolnego klawisza.
     * @param msg wiadomosc do wyswietlenia podczas oczekiwania
     */
    public static void hitAKkey(String msg, boolean print) {
        if (print) {
            System.out.println();
            for (int i = 0; i < msg.length() + 15; i++) {
                System.out.print("-");
            }
            System.out.println("\n\t" + msg);
            for (int i = 0; i < msg.length() + 15; i++) {
                System.out.print("-");
            }
        }
        try {
            System.in.read();
            while (System.in.available() != 0) {
                System.in.read();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
