package Classes.graficas;

import Classes.Cliente;
import Classes.Hotel;
import Classes.helpers.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MenuClientesBase extends MenuBase {
    protected final ListaClientes listaClientes;
    protected final ClientePainel clientePainel;
    protected final JPanel leftPanel; // BorderLayout
    protected final JPanel rightPanel; // BorderLayout
    protected final JPanel buttonPanel; // BoxLayout.X_AXIS

    public MenuClientesBase(Hotel h, String titulo) {
        // Configurações básicas da janela
        super(h, titulo);

        clientePainel = new ClientePainel();
        clientePainel.setLayout(new BoxLayout(clientePainel, BoxLayout.Y_AXIS));

        listaClientes = new ListaClientes(getHotel());
        listaClientes.setLayout(new BoxLayout(listaClientes, BoxLayout.PAGE_AXIS));
        listaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        listaClientes.addListSelectionListener(e -> {
            Cliente cliente = (Cliente) listaClientes.getSelectedValue();

            if (cliente != null)
                clientePainel.setCliente(cliente);
        });

        // Hack para permitir que o usuário selecione o item já selecionado
        listaClientes.addMouseListener(Utils.fromMouseClick(e -> {
            if (listaClientes.locationToIndex(e.getPoint()) != listaClientes.getSelectedIndex())
                return;

            Cliente cliente = (Cliente) listaClientes.getSelectedValue();
            if (cliente != null && cliente != clientePainel.getCliente())
                clientePainel.setCliente(cliente);
        }));

        JTextField searchBar = new JTextField();
        searchBar.getDocument().addDocumentListener(Utils.fromDocumentModify(e -> {
            listaClientes.refresh(searchBar.getText());
        }));

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.black));

        leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(new JScrollPane(listaClientes), BorderLayout.CENTER);
        leftPanel.add(searchBar, BorderLayout.SOUTH);

        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);
        rightPanel.add(clientePainel, BorderLayout.CENTER);

        // Main Panel
        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        // Make ScrollableList always take 1/3 of the screen
        leftPanel.setPreferredSize(new Dimension(mainPanel.getWidth() / 3, mainPanel.getHeight()));
        mainPanel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                leftPanel.setPreferredSize(new Dimension(mainPanel.getWidth() / 3, mainPanel.getHeight()));
            }
        });

        // Add the main panel to the frame
        add(mainPanel);
    }


    @Override
    public void refresh() {
        listaClientes.refresh();
        clientePainel.setCliente(null);
    }
}
