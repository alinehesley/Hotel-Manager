package Classes;

import Classes.graficas.Book;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // TODO: Colocar o hotel do arquivo ao invés do hotel padrão
            Hotel hotel = Book.getHotelPadrao();

            Book menuPrincipalGUI = new Book(hotel);
            menuPrincipalGUI.setVisible(true);
        });
    }
}
