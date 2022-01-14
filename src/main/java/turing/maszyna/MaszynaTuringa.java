package turing.maszyna;

import java.util.ArrayList;
import java.util.HashMap;

import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.MultiGraph;

import turing.maszyna.Przejscie.Kierunek;
import turing.tasma.Tasma;

public class MaszynaTuringa {

    private MultiGraph graf;

    private HashMap<String,Stan> stany; // <id, Stan>
    Stan aktywnyStan = null;

    private Tasma tasma;

    private String slowoWejsciowe = "";

    public MaszynaTuringa(){
        graf = new MultiGraph("MaszynaTuringa");
        graf.addAttribute("ui.stylesheet","graph {padding: 100px; } " );
        graf.display();
        stany = new HashMap<>();
        tasma = new Tasma();
        
    }

    public void addStan(String id) {
        Stan nowyStan = new Stan(id,graf);
        stany.put(id, nowyStan);
    }
    /**
     * Dodaje przejscie do maszyny
     * @param przejscie przejscie zapisane w slowny sposob jak w pliku
     * @param alfabetWejsciowy
     * @param alfabetTasmowy
     */
    public void addPrzejscie(String przejscie, ArrayList<String> alfabetWejsciowy, ArrayList<String> alfabetTasmowy){
        Przejscie nowePrzejscie = new Przejscie(przejscie, stany);

        if(!alfabetWejsciowy.contains(nowePrzejscie.znakCzytany) && !alfabetTasmowy.contains(nowePrzejscie.znakDoNapisania)){
            throw new IllegalArgumentException("Przejscie: \""+przejscie+"\" zawiera niedozwolony znak");
        }

        nowePrzejscie.generujKrawedz(graf);
        stany.get(nowePrzejscie.biezacyStan.node.getId()).addPrzejscie(nowePrzejscie);
    }

    public Tasma getTasma() {
        return this.tasma;
    }

    public void setStanPoczatkowy(String nazwaStanu) {

        Stan nowyStan = new Stan("Begin", graf);
        nowyStan.node.removeAttribute("ui.label");
        nowyStan.node.addAttribute("ui.style", "size: 0; fill-color: #FFFFFF; stroke-mode: plain; stroke-width:1px; stroke-color:#000000;");
        stany.put("Begin",nowyStan);
        Przejscie nowPrzejscie = new Przejscie("Begin a "+nazwaStanu+" a P", stany);
        Edge e = nowPrzejscie.generujKrawedz(graf);
        e.removeAttribute("ui.label");

        stany.get(nazwaStanu).pokolorujNaAktywny();
        aktywnyStan = stany.get(nazwaStanu);
    }

    public void setStanAkceptujacy(String string) {
        stany.get(string).setAkceptujacy();
    }
    /**
     * @return true jeśli aktualny stan jest stanem akceptujacym
     */
    public boolean ended() {
        return aktywnyStan.isAkceptujacy();
    }
    /**
     * Spróbuj wykonać przejscie
     * @throws Exception
     */
    public void wykonajPrzejscie() throws Exception {
      
        Przejscie przejscie = aktywnyStan.znajdzMozliwePrzejscie(tasma.getZnak());
        if (przejscie != null) {
            
            
            if (przejscie.kierunek== Kierunek.PRAWO) {
                tasma.poruszWPrawo(przejscie.znakDoNapisania);
            }
            else if (przejscie.kierunek == Kierunek.LEWO) {
                tasma.poruszWLewo(przejscie.znakDoNapisania);
            }
            
            aktywnyStan.pokolorujNaNieAktywna();
            aktywnyStan = przejscie.docelowyStan;
            aktywnyStan.pokolorujNaAktywny();
            
        }
        else{
            throw new Exception("BŁĄD MASZYNY - brak dostępnego poprawnego przejscia dla aktualnego stanu maszyny ");
        }


    }

    public String getSlowoWejsciowe() {
        return this.slowoWejsciowe;
    }
    public void setSlowoWejsciowe(String slowo){
        this.slowoWejsciowe = slowo;
        tasma.dodajCiagWejsciowy(slowo);
    }
    /**
     * 
     * @return aktualne słowo na taśmie 
     */
    public String getSlowoObliczone() {
        return tasma.toString();
    }
    
}