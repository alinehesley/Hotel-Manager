package Classes.graficas;

import Classes.Hotel;

import javax.swing.*;

public abstract class MenuBase extends JFrame {
    public static final int larguraPadrao = 960;
    public static final int alturaPadrao = 600;

    private final Hotel hotel;
    private MenuBase parent = null;

    public MenuBase(Hotel hotel, String titulo, MenuBase parent) {
        this.hotel = hotel;
        this.parent = parent;

        setTitle(titulo);
        setSize(larguraPadrao, alturaPadrao);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLocationRelativeTo(null);
    }

    public MenuBase(Hotel hotel, String titulo) {
        this(hotel, titulo, null);
    }

    public Hotel getHotel() {
        return hotel;
    }

    public MenuBase getParent() {
        return parent;
    }

    public void setParent(MenuBase parent) {
        this.parent = parent;
    }

    public void exibirMenu() {
        setVisible(true);
    }

    public void fecharMenu(boolean refresh) {
        setVisible(false);
        dispose();

        if (parent != null && refresh)
            parent.refresh();
    }

    // TODO(thiago): Remover essa função é realmente uma boa ideia?
//    public void fecharMenu() {
//        fecharMenu(true);
//    }

    public void refresh() {}
}
