package turing.maszyna;

import java.util.ArrayList;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

/**
 * Stan - reprezentuje stan maszyny turinga
 */
public class Stan {
    /** Przechowuje  element grafu który reprezentuje*/
    Node node;  
    /** Referencja do grafu w którym występuje */
    MultiGraph graph;
    /** Przechowuje możliwe przejścia dla danego punktu */
    ArrayList<Przejscie> przejscia;

    public Stan(String id, MultiGraph singleGraph){
        przejscia = new ArrayList<>();
        this.graph = singleGraph;
        node = graph.addNode(id);
        node.setAttribute("ui.style", "size: 20; fill-color: #FFFFFF; stroke-mode: plain; stroke-width:1px; stroke-color:#000000;");
        node.setAttribute("akceptujacy", false);
        setLabel(id);
    }
    
    /**Ustawia nazwę wyświetlaną w Stanie na grafie*/
    public void setLabel(String l){
        this.node.addAttribute("ui.label", l);
    }


    public Node getNode() {
        return this.node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
    /**
     * 
     * @return ArrayList przechowujący kolejne dozwolone przejścia z tego stanu maszyny
     */
    public ArrayList<Przejscie> getPrzejscia() {
        return this.przejscia;
    }

    /**
     * Dodaje podane w argumencie przejście do listy dozwolonych przejść z tego stanu
     * @param przejscie
     */
    public void addPrzejscie(Przejscie przejscie) {
        this.przejscia.add(przejscie);
    }

    /**
     * Koloruje reprezentację stanu na kolor oznaczający, żę stan jest stanem aktywnym
     */
    public void pokolorujNaAktywny() {
        if(this.isAkceptujacy()){
            this.node.addAttribute("ui.style", "fill-color:#FFAA77;");
        }
        else{
            this.node.addAttribute("ui.style", "fill-color:#77FF77;");
        }
    }

    /**
     * Koloruje reprezentację stanu na kolor oznaczający, żę stan jest stanem nie aktywnym
     */
    public void pokolorujNaNieAktywna(){
        this.node.addAttribute("ui.style", "fill-color:#FFFFFF;");
    }

    /**
     * Ustawia stan jako stan akceputjący (po natrafieniu maszyna powinna się na nim zatrzymać)
     */
    public void setAkceptujacy() {
        this.node.addAttribute("ui.style", "fill-color:#FF1111;");
        this.node.setAttribute("akceptujacy", true);
    }
    /**
     * Sprawdza czy ten stan jest stanem akceptującym maszyny Turinga
     * @return boolean
     */
    public boolean isAkceptujacy() {
        return (boolean) this.node.getAttribute("akceptujacy");
    }

    /**
     * Sprawdza Czy isnieje akceptowane przejście dla podanego znaku.
     * @param znak
     * @return
     */
    public Przejscie znajdzMozliwePrzejscie(String znak) {

        for (Przejscie przejscie : this.przejscia) {
            if(przejscie.isZnakOk(znak)){
                return przejscie;
            }
        }
        
        return null;
    }



}