package turing.maszyna;

import java.util.ArrayList;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

/**
 * Stan
 */
public class Stan {
    Node node;
    MultiGraph graph;
    ArrayList<Przejscie> przejscia;

    public Stan(String id, MultiGraph singleGraph){
        przejscia = new ArrayList<>();
        this.graph = singleGraph;
        node = graph.addNode(id);
        node.setAttribute("ui.style", "size: 20; fill-color: #FFFFFF; stroke-mode: plain; stroke-width:1px; stroke-color:#000000;");
        node.setAttribute("akceptujacy", false);
        setLabel(id);
    }
    
    public void setLabel(String l){
        this.node.addAttribute("ui.label", l);
    }


    public Node getNode() {
        return this.node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public ArrayList<Przejscie> getPrzejscia() {
        return this.przejscia;
    }

    public void addPrzejscie(Przejscie przejscie) {
        this.przejscia.add(przejscie);
    }

    public void pokolorujNaAktywna() {

        this.node.addAttribute("ui.style", "fill-color:#77FF77;");
    }

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


}