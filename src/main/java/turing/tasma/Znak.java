package turing.tasma;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import turing.Stale;


/**
 * Klasa reprezentująca pojedyńczy znak na taśmie
 */
public class Znak{
    /**Odnośnik do grafu na którym znak jest wyświetlany */
    private SingleGraph graf;
    /**Odnośnik do elementu grafu który odpowiada za wyświetlanie tego znaku */
    private Node node;


    
    /**Przechowuje lokalizację x -znaku na grafie taśmy */
    int x;
    /**Przechowuje lokalizację y - znaku na grafie taśmy */
    int y;

    public Znak(SingleGraph _graf,String id, int position){
        this.graf = _graf;
        node = graf.addNode(id);
        boolean dodawnanie_z_lewej = position <0 ; //jeśli position jest <0 to znaczy, że dodajemy znaki z lewej
        
        if (dodawnanie_z_lewej) {
            position = Math.abs(position) - 1;
        }
        int kierunek = 0;
        kierunek = (position / Stale.ZNAKOW_TASMY_W_LINI) % 2;
        kierunek = Math.abs(kierunek);


        if (kierunek == 0 ) {//poprawne zawijanie 
            x = position %  Stale.ZNAKOW_TASMY_W_LINI; 
        }
        else if (kierunek== 1 ){
            x = Stale.ZNAKOW_TASMY_W_LINI - (position %  Stale.ZNAKOW_TASMY_W_LINI) - 1;
        }

        if(!dodawnanie_z_lewej) // zawiń do góry jeśli dodajemy z lewej strony taśmy
            y = -((position)/ Stale.ZNAKOW_TASMY_W_LINI) * 2;
        else
            y = ((position) / Stale.ZNAKOW_TASMY_W_LINI) * 2 +2;


        node.addAttribute("x", x);
        node.addAttribute("y", y);
        node.addAttribute("ui.style", "shape: box;size:" + 15 + "px; fill-color: #FFFFFF; stroke-mode: plain; stroke-width:1px; stroke-color:#000000;");
        node.addAttribute("ui.label", "#");
    }
    /**
     * Przenieś "znak" na podane koordynaty
     * @param x
     * @param y
     */
    public void move(int x, int y){
        node.addAttribute("x", x);
        node.addAttribute("y", y);
    }
    /**
     * Ustawia znak na "#"
     */
    public void ustawPusty() {
        node.setAttribute("ui.label", "#");
    }
    /**
     * Ustawia przechowywany symbol na podany w argumencie
     * @param znak - symbol do ustawienia
     */
    public void ustawZnak(String znak){
        node.setAttribute("ui.label", znak);
    }
    /**
     * 
     * @return aktualnie przechowywany symbol w znaku
     */
    public String getZnak(){
        return node.getAttribute("ui.label");
    }

    /**
     * Tworzy krawędź od tego znaku do znaku podanego w argumencie
     * @param n
     */
    public void ustawNastepnik(Znak n){
        graf.addEdge(node.getId()+""+n.getId(), node, n.getNode(), false);
    }
    /**
     * Metoda do tworzenia krawędzi w grafie (tylko dla "Znaku" przedstawiającego poprzednio przechowywany znak na danym "Znaku")
     * @param n
     */
    public void ustawNastepnikDlaStaregoZnaku(Znak n){
        graf.addEdge(node.getId()+""+n.getId(), node, n.getNode(), true);
    }
    /**
     * Usuwa wszystkie krawędzie dla tego znaku
     */
    public void usunPoloczenia(){
        for (Edge edge : node.getEdgeSet()) {
            graf.removeEdge(edge);
        }
    }
    /**
     * Zwraca id "node-a " który reprezentuje ten "Znak"
     * @return String - id node-a
     */
    public String getId(){
        return node.getId();
    }
    /**
     * 
     * @return Node - Node reprezentujący ten "Znak"
     */
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
     * Koloruje znak który przedstawia "stary znak" 
     */
    public void kolorujJakoStary() {
        node.addAttribute("ui.style", "fill-color:#FF5555;");
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
        return "tasma" + --id;
    }

    /**
     * 
     * @return id znaku pobrane z id node-a i przekonwertowane na int
     */
    public int getIdNumber() {
        return Integer.parseInt(this.getNode().getId().substring(5));
    }

    


}
