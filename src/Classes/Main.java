package Classes;

import Classes.graficas.Book;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // TODO: Colocar o hotel do arquivo ao invés do hotel padrão
            Hotel hotel = new Hotel(
                    "Hotel de POO",
                    "Rua dos bobos numero zero",
                    "(42) 4242-4242",
                    "Hotel 5 estrelas"
            );

            Book menuPrincipalGUI = new Book(hotel);
            menuPrincipalGUI.setVisible(true);
        });
    }
}
