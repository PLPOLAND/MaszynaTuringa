package turing.tasma;

import java.util.ArrayList;

import org.graphstream.graph.implementations.SingleGraph;

import turing.Stale;

/**
 * Klasa odpowiadająca za obsługę taśmy maszyny turinga i wyświetlanie jej reprezentacji.
 */
public class Tasma {
    

    /**Przechowuje kolejne komórki taśmy*/
    ArrayList<Znak> tasma;
    /**służy do wyświetlania grafu taśmy*/
    SingleGraph grafTasmy;
    /**Aktulanie wskazywana komórka w taśmie*/
    Znak aktualnyZnak;
    /** index aktualnego znaku w tasmie */
    int aktualnyIndex = 1;
    /** przechowuje i wyświetla "stary znak" */
    Znak staryZnak;

    public Tasma(){
        grafTasmy = new SingleGraph("Tasma");
        grafTasmy.display(false);
        tasma = new ArrayList<>();
        for (int i = 0; i < Stale.ROZSZERZENIE_TASMY; i++) {
            Znak nastepnyZnak = new Znak(grafTasmy, "tasma"+i, i);
            if(i!=0){
                tasma.get(tasma.size()-1).ustawNastepnik(nastepnyZnak);
            }
            tasma.add(nastepnyZnak);
        }

        
        aktualnyZnak = tasma.get(aktualnyIndex);
        aktualnyZnak.kolorujJakoAktywny();

    }

    /**
     * Przesuwa node-a wyświetlającego stary znak w miejsce nad znakiem o podanym indexie
     * @param index
     */
    private void przesunStaryZnak(int index) {
        if (staryZnak == null) {
            staryZnak = new Znak(grafTasmy, "stary", 0);
            staryZnak.kolorujJakoStary();
        }
        
        staryZnak.ustawZnak(aktualnyZnak.getZnak());
        staryZnak.usunPoloczenia();
        staryZnak.move(tasma.get(index).x, tasma.get(index).y+1);
        staryZnak.ustawZnak(tasma.get(index).getZnak());
        staryZnak.ustawNastepnikDlaStaregoZnaku(tasma.get(index));
    }
    /**
     * Rozszerza taśmę o odpowiednią ilość pól z prawej strony taśmy
     */
    public void rozszerzZPrawej() {
        int nowyRozmiarTasmy = tasma.get(tasma.size()-1).getIdNumber() + Stale.ROZSZERZENIE_TASMY;
        for (int i = tasma.size(); i < nowyRozmiarTasmy; i++) {
            Znak nastepnyZnak = new Znak(grafTasmy, "tasma" + i, i);
            if (i != 0) {
                tasma.get(tasma.size() - 1).ustawNastepnik(nastepnyZnak);
            }
            tasma.add(nastepnyZnak);
        }
        System.out.println("-------TASMA ROZSZERZONA Z PRAWEJ STRONY -------");
    }
    /**
     * Rozszerza taśmę o odpowiednią ilość pól od przodu taśmy
     */
    public void rozszerzZLewej() {
        Znak pierwszyZnak = tasma.get(0);
        int fstId = Integer.parseInt(pierwszyZnak.getNode().getId().substring(5));
        for (int i =  fstId-1; i > fstId - Stale.ROZSZERZENIE_TASMY - 1; i--) {
            Znak nastepnyZnak = new Znak(grafTasmy, "tasma" + i, i);
            // nastepnyZnak.ustawZnak(i+"");
            if (i != 0) {
                nastepnyZnak.ustawNastepnik(tasma.get(0));
                // tasma.get(tasma.size() - 1).ustawNastępnik(nastepnyZnak);
            }
            tasma.add(0, nastepnyZnak);//dodaj z przodu taśmy
        }
        aktualnyIndex += Stale.ROZSZERZENIE_TASMY;
        System.out.println("-------TASMA ROZSZERZONA Z LEWEJ STRONY -------");
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
     * Porusza głowę w PRAWO z wcześniejszą zmianą znaku
     * @param znak - nowy znak
     * @return 
     */
    public Znak poruszWPrawo(String znak){
        przesunStaryZnak(aktualnyIndex);
        aktualnyZnak.ustawZnak(znak);
        return poruszWPrawo();
    }
     
    /**
     * Porusza głowę w LEWO z wcześniejszą zmianą znaku
     * 
     * @param znak - nowy znak
     * @return
     */
    public Znak poruszWLewo(String znak){
        przesunStaryZnak(aktualnyIndex);
        aktualnyZnak.ustawZnak(znak);
        return poruszWLewo();
    }

    /**
     * Zmienia aktualnie wskazywany znak na ten z prawej.
     * 
     * W razie natrafienia na znak krańcowy, rozszerza taśmę
     * 
     * @return nowy aktualny znak
     */
    private Znak poruszWPrawo(){
        
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
    private Znak poruszWLewo(){
        aktualnyZnak.kolorujJakoNieAktywny();
        aktualnyIndex--;
        if (aktualnyIndex == 0) {
            this.rozszerzZLewej();
        }
        aktualnyZnak = tasma.get(aktualnyIndex);
        aktualnyZnak.kolorujJakoAktywny();

        return aktualnyZnak;
    }
    /**
     * Pozwala na łatwe dodanie ciągu znaków do taśmy 
     * @param wejscie
     */
    public void dodajCiagWejsciowy(String wejscie) {
        for (int i = 0; i < wejscie.length(); i++) {
            this.setZnak(wejscie.charAt(i)+"");
            this.poruszWPrawo();
        }
        for (int i = 0; i < wejscie.length(); i++) {
            this.poruszWLewo();
        }

    }

    /**
     * @return String - zawiera znaki zapisane na taśmie, pomijając wszystkie "#".
     */
    @Override
    public String toString() {
        String slowo = "";

        for (Znak znak : tasma) {
            if (!znak.getZnak().equals("#")) {
                slowo+= znak.getZnak();
            }
        }

        return slowo;
    }
   
}
