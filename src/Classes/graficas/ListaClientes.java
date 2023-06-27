package Classes.graficas;

import Classes.Cliente;
import Classes.ClienteDependente;
import Classes.Hotel;
import Classes.helpers.CustomListCellRenderer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ListaClientes extends JList {
    private List<Cliente> lista;
    private boolean highlightDependents;

    public ListaClientes() {
        this(new ArrayList<Cliente>());
    }

    public ListaClientes(List<Cliente> lista) {
        super();
        this.lista = lista;
        this.highlightDependents = true;

        this.setCellRenderer(new CustomListCellRenderer() {
            @Override
            public String getDisplayText(Object value) {
                if (highlightDependents && value instanceof ClienteDependente)
                    return ((Cliente)value).getNome() + " *";
                if (value instanceof Cliente)
                    return ((Cliente)value).getNome();

                return value.toString();
            }
        });

        refresh();
    }

    public ListaClientes(Hotel hotel) {
        this(hotel.getClientes());
    }

    public void refresh(String filter) {
        final ArrayList<Cliente> listaClientes = new ArrayList<>(this.lista);

        if (filter == null || filter.length() == 0) {
            // Sem filtragem
        } else if (filter.charAt(0) >= '0' && filter.charAt(0) <= '9') {
            // Filtragem por CPF
            listaClientes.removeIf(cliente -> {
                return !cliente.getCpf().startsWith(filter.replaceAll("[^0-9]", ""));
            });
        } else {
            // Filtragem por Nome
            listaClientes.removeIf(cliente -> {
                return !cliente.getNome().toLowerCase().startsWith(filter.toLowerCase());
            });
        }

        this.setModel(new AbstractListModel<Cliente>() {
            @Override
            public int getSize() {
                return listaClientes.size();
            }

            @Override
            public Cliente getElementAt(int i) {
                return listaClientes.get(i);
            }
        });
    }

    public void refresh() {
        refresh(null);
    }

    public void setLista(List<Cliente> lista) {
        this.lista = lista;
        this.refresh();
    }

    public boolean getHighlightDependents() {
        return highlightDependents;
    }

    public void setHighlightDependents(boolean b) {
        this.highlightDependents = b;
    }

    public void clear() {
        this.lista = new ArrayList<>();
        this.refresh();
    }
}
