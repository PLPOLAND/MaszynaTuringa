package turing;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;

import org.apache.commons.io.IOUtils;

import turing.maszyna.MaszynaTuringa;

public class Tools {
 
    
    public static void pause(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static MaszynaTuringa loadFromFile(String fileName) {
        
        MaszynaTuringa maszynaTuringa = new MaszynaTuringa();

        FileInputStream fis;
        String data = null;
        try {
            fis = new FileInputStream(fileName);
            data = IOUtils.toString(fis, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch(IOException e1){
            e1.printStackTrace();            
            System.exit(-1);
        }
        
        String[] lines = data.split("\n");
        
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].equals("alfabet tasmowy:")) {
                i++;

            } else if(lines[i].equals("alfabet wejsciowy:")) {
                i++;
            }else if(lines[i].equals("slowo wejsciowe:")){
                //TODO sprawdzanie czy słowo wejściowe nie zawiera elementów z poza alfabetu wejsciowego
                i++;
                maszynaTuringa.getTasma().dodajCiagWejsciowy(lines[i]);
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
                    maszynaTuringa.addPrzejscie(przejscie);
                }
            } else{
                i++;
            }

            
        }
        
        return maszynaTuringa;
    }
}
