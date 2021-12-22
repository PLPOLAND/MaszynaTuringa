package turing.tasma;

import java.util.ArrayList;

import org.graphstream.graph.implementations.SingleGraph;

public class Tasma {
    final int rozszerzenieTasmy = 32;

    /**Przechowuje kolejne komórki taśmy*/
    ArrayList<Znak> tasma;
    /**służy do wyświetlania grafu taśmy*/
    SingleGraph grafTasmy;
    /**Aktulanie wskazywana komórka w taśmie*/
    Znak aktualnyZnak;
    /** index aktualnego znaku w tasmie */
    int aktualnyIndex = 1;

    public Tasma(){
        grafTasmy = new SingleGraph("Tasma");
        grafTasmy.display(false);
        tasma = new ArrayList<>();
        for (int i = 0; i < rozszerzenieTasmy; i++) {
            Znak nastepnyZnak = new Znak(grafTasmy, "tasma"+i, i);
            if(i!=0){
                tasma.get(tasma.size()-1).ustawNastępnik(nastepnyZnak);
            }
            tasma.add(nastepnyZnak);
        }
        
        aktualnyZnak = tasma.get(aktualnyIndex);
        aktualnyZnak.kolorujJakoAktywny();

    }
    /**
     * Rozszerza taśmę o odpowiednią ilość pól z prawej strony taśmy
     */
    public void rozszerzZPrawej() {
        for (int i = tasma.size(); i < tasma.size() + rozszerzenieTasmy; i++) {
            Znak nastepnyZnak = new Znak(grafTasmy, "tasma" + i, i);
            if (i != 0) {
                tasma.get(tasma.size() - 1).ustawNastępnik(nastepnyZnak);
            }
            tasma.add(nastepnyZnak);
        }
    }
    /**
     * Rozszerza taśmę o odpowiednią ilość pól od przodu taśmy
     */
    public void rozszerzZLewej() {
        Znak pierwszyZnak = tasma.get(0);
        int fstId = Integer.parseInt(pierwszyZnak.getNode().getId().substring(5));
        for (int i =  fstId-1; i > fstId - rozszerzenieTasmy - 1; i--) {
            Znak nastepnyZnak = new Znak(grafTasmy, "tasma" + i, i);
            nastepnyZnak.ustawZnak(i+"");
            if (i != 0) {
                nastepnyZnak.ustawNastępnik(tasma.get(0));
                // tasma.get(tasma.size() - 1).ustawNastępnik(nastepnyZnak);
            }
            tasma.add(0, nastepnyZnak);//dodaj z przodu taśmy
        }
        aktualnyIndex += rozszerzenieTasmy;
    }

    /**
     * Ustawia nowy znak na aktualnej "komórce" taśmy
     * @param nowyZnak
     */
    public void setZnak(String nowyZnak) {
        aktualnyZnak.ustawZnak(nowyZnak);
    }

    /**
     * Zwraca aktualną pozycję 
     * @return akulany znak
     */
    public String getZnak() {
        return aktualnyZnak.getZnak();
        
    }

    /**
     * Zmienia aktualnie wskazywany znak na ten z prawej.
     * 
     * W razie natrafienia na znak krańcowy, rozszerza taśmę
     * 
     * @return nowy aktualny znak
     */
    public Znak poruszWPrawo(){
        
        aktualnyZnak.kolorujJakoNieAktywny();
        aktualnyIndex++;
        if(aktualnyIndex == tasma.size() - 1){
            this.rozszerzZPrawej();
        }
        aktualnyZnak = tasma.get(aktualnyIndex);
        aktualnyZnak.kolorujJakoAktywny();

        return aktualnyZnak;
    }

    /**
     * Zmienia aktualnie wskazywany znak na ten z lewej. 
     * 
     * W razie natrafienia na znak krańcowy, rozszerza taśmę
     * 
     * @return nowy aktualny znak
     */
    public Znak poruszWLewo(){
        aktualnyZnak.kolorujJakoNieAktywny();
        aktualnyIndex--;
        if (aktualnyIndex == 0) {
            this.rozszerzZLewej();
        }
        aktualnyZnak = tasma.get(aktualnyIndex);
        aktualnyZnak.kolorujJakoAktywny();

        return aktualnyZnak;
    }

    public void dodajCiagWejsciowy(String wejscie) {
        for (int i = 0; i < wejscie.length(); i++) {
            this.setZnak(wejscie.charAt(i)+"");
            this.poruszWPrawo();
        }
        for (int i = 0; i < wejscie.length(); i++) {
            this.poruszWLewo();
        }

    }
   
}
