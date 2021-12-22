package turing.tasma;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class Znak{
    private SingleGraph graf;
    private Node node;

    public Znak(SingleGraph _graf,String id, int position){
        this.graf = _graf;
        node = graf.addNode(id);
        node.addAttribute("x", position % 100);
        node.addAttribute("y", -(position/100) * 2);
        node.addAttribute("ui.style", "shape: box;size:" + 15 + "px; fill-color: #FFFFFF; stroke-mode: plain; stroke-width:1px; stroke-color:#000000;");
        node.addAttribute("ui.label", "#");
    }

    public void ustawPusty() {
        node.setAttribute("ui.label", "#");
    }

    public void ustawZnak(String znak){
        node.setAttribute("ui.label", znak);
    }

    public String getZnak(){
        return node.getAttribute("ui.label");
    }

    public void ustawNastępnik(Znak n){
        graf.addEdge(node.getId()+""+n.getId(), node, n.getNode(), false);
    }

    public String getId(){
        return node.getId();
    }

    public Node getNode() {
        return node;
    }
    /**
     * koloruje znak na taśmie
     */
    public void kolorujJakoAktywny() {
        node.addAttribute("ui.style", "fill-color:#77FF77;");
    }
    /**
     * Odkolorowuje znak na taśmie
     */
    public void kolorujJakoNieAktywny(){
        node.addAttribute("ui.style", "fill-color:#FFFFFF;");
    }


    /**
     * 
     * @return id następnego znaku
     */
    public String getIdPrawegoZnaku() {
        int id = Integer.parseInt(this.getNode().getId().substring(5));
        return "tasma" + ++id;
    }
    
    /**
     * 
     * @return id poprzedniego znaku
     */
    public String getIDLewegoZnaku(){
        int id = Integer.parseInt(this.getNode().getId().substring(5));
        return "tasma" + ++id;
    }

    public int getIdNumber() {
        return Integer.parseInt(this.getNode().getId().substring(5));
    }


}
