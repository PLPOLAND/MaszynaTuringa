package turing.maszyna;

import java.util.HashMap;

import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.MultiGraph;

public class Przejscie {
    Stan biezacyStan = null;
    String znakCzytany = null;
    Stan docelowyStan = null;
    String znakDoNapisania = null;
    Kierunek kierunek = Kierunek.NIEUSTAWIONO;

    public enum Kierunek {
        NIEUSTAWIONO,
        LEWO,
        PRAWO
    };

    public Przejscie(Stan _biezacyStan, Stan _docelowyStan, String _znakCzytany, String _znakDoNapisania, Kierunek _kierunek){
        biezacyStan = _biezacyStan;
        znakCzytany = _znakCzytany;
        docelowyStan = _docelowyStan;
        znakDoNapisania = _znakDoNapisania;
        kierunek = _kierunek;
    }   
    public Przejscie(String lineIn, HashMap<String,Stan> stany){
        String[] znaki = lineIn.split(" ");
        biezacyStan = stany.get(znaki[0]);
        znakCzytany = znaki[1];
        docelowyStan = stany.get(znaki[2]);
        znakDoNapisania = znaki[3];
        kierunek = znaki[4].equals("L") ? Kierunek.LEWO : Kierunek.PRAWO;
    }

    public Edge generujKrawedz(MultiGraph graph) {
        Edge krawedz =null;
        int i = 0;
        while (krawedz==null) {
            try {
                krawedz= graph.addEdge(biezacyStan.getNode().getId()+"->"+docelowyStan.getNode().getId()+i, biezacyStan.getNode(), docelowyStan.getNode(), true);
            } catch (org.graphstream.graph.IdAlreadyInUseException e) {
                i++;
            }
        }

        krawedz.addAttribute("ui.label", znakCzytany+","+znakDoNapisania+","+ (kierunek==Kierunek.LEWO ? "L" : "P"));
        krawedz.addAttribute("ui.style", "text-offset:10,10;");
        return krawedz;
    }

    /**
     * Sprawdza czy podany znak jest akceptowany dla tego przejścia
     * @param znak
     * @return
     */
    public boolean isZnakOk(String znak) {
        return znakCzytany.equals(znak);
    }
}
