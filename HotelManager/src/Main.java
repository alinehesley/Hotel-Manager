import Classes.graficas.MenuInicial;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuInicial menuPrincipalGUI = new MenuInicial();
            menuPrincipalGUI.setVisible(true);
        });

    }
}
