package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Application;

public class World {

    /**
     * Aplikacja działa w 2 trybach:
     * - manualny
     * - autonomiczny
     * Aby włączyć tryb manualny jako argumenty
     * należy podać ciąg ruchów np "f r f r l b".
     * Tryb automatyczy włączany jest przez podanie jednego argumentu "skynet"
     */
    public static void main(String[] args) {
        try {
            Application.launch(App.class, args);
        }
        catch (Exception e) {
            System.err.println("Kółko graniaste, czworokanciaste, " + e.getMessage() + ", a my wszyscy bęc.");
            System.exit(0);
        }
    }
}
