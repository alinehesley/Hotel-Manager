package Classes.graficas;

import Classes.Cliente;
import Classes.Hotel;
import Classes.Quarto;
import Classes.helpers.CustomListCellRenderer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

// TODO(thiago): Refatorar as classes ListaClientes e ListaQuartos para uma classe genérica Lista?
public class ListaQuartos extends JList {
    private List<Quarto> lista;

    public ListaQuartos(List<Quarto> lista) {
        super();
        this.lista = lista;

        refresh();
    }

    public ListaQuartos(Hotel hotel) {
        this(hotel.getQuartos());
    }

    public void refresh(String filter) {
        final ArrayList<Quarto> listaQuartos = new ArrayList<>(this.lista);

        if (filter != null && filter.length() > 0) {
            listaQuartos.removeIf(quarto -> {
                return !Integer.toString(quarto.getNumero()).startsWith(filter);
            });
        }

        this.setModel(new AbstractListModel<Quarto>() {
            @Override
            public int getSize() {
                return listaQuartos.size();
            }

            @Override
            public Quarto getElementAt(int i) {
                return listaQuartos.get(i);
            }
        });
    }

    public void refresh() {
        refresh(null);
    }
    public void setLista(List<Quarto> lista) {
        this.lista = lista;
        this.refresh();
    }

    public void clear() {
        this.lista = new ArrayList<>();
        this.refresh();
    }
}
