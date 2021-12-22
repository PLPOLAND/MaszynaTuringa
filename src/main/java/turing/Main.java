package turing;

import turing.maszyna.MaszynaTuringa;

public class Main {
    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        MaszynaTuringa maszyna = Tools.loadFromFile("inputFiles/projektowy.txt");
        
    }
}
