package Classes;

import Classes.graficas.MenuInicial;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // TODO: Colocar o hotel do arquivo ao invés do hotel padrão
            Hotel hotel = MenuInicial.getHotelPadrao();

            MenuInicial menuPrincipalGUI = new MenuInicial(hotel);
            menuPrincipalGUI.setVisible(true);
        });
    }
}
