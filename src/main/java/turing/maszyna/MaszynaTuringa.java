package turing.maszyna;

import java.util.HashMap;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

import turing.maszyna.Przejscie.Kierunek;
import turing.tasma.Tasma;

public class MaszynaTuringa {

    public MultiGraph graf;

    private HashMap<String,Stan> stany; // <id, Stan>

    private Tasma tasma;
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

    public void addPrzejscie(String przejscie){
        Przejscie nowePrzejscie = new Przejscie(przejscie, stany);
        nowePrzejscie.generujKrawedz(graf);
        stany.get(nowePrzejscie.biezacyStan.node.getId()).addPrzejscie(nowePrzejscie);
    }

    public Tasma getTasma() {
        return this.tasma;
    }

    public void setStanPoczatkowy(String string) {

        Stan nowyStan = new Stan("Begin", graf);
        nowyStan.node.removeAttribute("ui.label");
        nowyStan.node.addAttribute("ui.style", "size: 0; fill-color: #FFFFFF; stroke-mode: plain; stroke-width:1px; stroke-color:#000000;");
        stany.put("Begin",nowyStan);
        Przejscie nowPrzejscie = new Przejscie("Begin a "+string+" a P", stany);
        Edge e = nowPrzejscie.generujKrawedz(graf);
        e.removeAttribute("ui.label");

        stany.get(string).pokolorujNaAktywna();
    }

    public void setStanAkceptujacy(String string) {
        stany.get(string).setAkceptujacy();
    }
    
}