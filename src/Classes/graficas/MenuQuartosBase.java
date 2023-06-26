package Classes.graficas;

import Classes.Cliente;
import Classes.Hotel;
import Classes.Quarto;
import Classes.helpers.DisabledItemSelectionModel;
import Classes.helpers.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MenuQuartosBase extends MenuBase {
    protected final ListaQuartos listaQuartos;
    protected final ListaClientes listaClientes;
    protected final JPanel leftPanel; // BorderLayout
    protected final JPanel rightPanel; // BorderLayout
    protected final JPanel buttonPanel; // BoxLayout.X_AXIS

    public MenuQuartosBase(Hotel h, String titulo) {
        // Configurações básicas da janela
        super(h, titulo);

        listaClientes = new ListaClientes();
        listaClientes.setLayout(new BorderLayout());
        listaClientes.setPreferredSize(new Dimension(2*getWidth()/5, getHeight()));
        listaClientes.setSelectionModel(new DisabledItemSelectionModel());

        listaQuartos = new ListaQuartos(getHotel());
        listaQuartos.setLayout(new BoxLayout(listaQuartos, BoxLayout.PAGE_AXIS));
        listaQuartos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        listaQuartos.addListSelectionListener(e -> {
            Quarto quarto = (Quarto) listaQuartos.getSelectedValue();

            if (quarto == null)
                listaClientes.clear();
            else
                listaClientes.setLista(quarto.getClientes());
        });

        JTextField searchBar = new JTextField();
        searchBar.getDocument().addDocumentListener(Utils.fromDocumentModify(e -> {
            listaQuartos.refresh(searchBar.getText());
        }));

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.black));

        leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(new JScrollPane(listaQuartos), BorderLayout.CENTER);
        leftPanel.add(searchBar, BorderLayout.SOUTH);

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        panel1.setBackground(Color.blue);

        panel1.setPreferredSize(new Dimension(2*getWidth()/5, getHeight()));

        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);
        rightPanel.add(panel1, BorderLayout.WEST);
        rightPanel.add(listaClientes, BorderLayout.EAST);

        // Main Panel
        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        // Make ScrollableList always take 1/3 of the screen
        leftPanel.setPreferredSize(new Dimension(mainPanel.getWidth() / 5, mainPanel.getHeight()));
        mainPanel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {;
                leftPanel.setPreferredSize(new Dimension(mainPanel.getWidth() / 5, mainPanel.getHeight()));
            }
        });

        // Add the main panel to the frame
        add(mainPanel);
    }


    @Override
    public void refresh() {
        listaQuartos.refresh();
        listaClientes.clear();
    }
}
