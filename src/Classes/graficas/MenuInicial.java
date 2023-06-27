package Classes.graficas;

import Classes.Hotel;

import javax.swing.*;

import java.awt.*;

public class MenuInicial extends MenuBase {
    private JPanel mainPanel;
    private JPanel menuPanel;
    private JPanel contentPanel;
    private JButton clientesButton;
    private JButton quartosButton;

    public MenuInicial(Hotel hotel) {
        super(hotel, "Menu Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Criação do painel principal
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Criação do painel do menu
        menuPanel = new JPanel();

        // Menu de clientes
        clientesButton = new JButton("Menu Clientes");
        clientesButton.addActionListener(e -> criarMenu(MenuClientes.class, hotel));
        menuPanel.add(clientesButton);

        // Menu de quartos
        quartosButton = new JButton("Menu Quartos");
        quartosButton.addActionListener(e -> criarMenu(MenuQuartos.class, hotel));
        menuPanel.add(quartosButton);

        // Criação do painel de conteúdo
        contentPanel = new JPanel();
        contentPanel.setLayout(new FlowLayout());
        JLabel label = new JLabel("Bem-vindo ao Menu Principal");
        contentPanel.add(label);

        // Adiciona os painéis ao painel principal
        mainPanel.add(menuPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Adiciona o painel principal à janela
        add(mainPanel);
    }

    private void criarMenu(Class<? extends MenuBase> menuClass, Hotel hotel) {
        MenuBase menu;
        try {
            menu = menuClass.getDeclaredConstructor(Hotel.class).newInstance(hotel);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        menu.setParent(this);
        menu.exibirMenu();
    }

    private void voltarAoMenuPrincipal() {
        contentPanel.removeAll();

        // Criação do painel de conteúdo padrão
        JLabel label = new JLabel("Bem-vindo ao Menu Principal");
        contentPanel.add(label);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // TODO(thiago): Essa função deve ser estática?
    public static Hotel getHotelPadrao(){
        Hotel h = new Hotel(
                "Hotel de POO",
                "Rua dos bobos numero zero",
                "(42) 4242-4242",
                "Hotel 5 estrelas"
        );
        for (int i=0;i<10;i++){
            h.addQuarto(1,1);
            h.addQuarto(1,0);
            h.addQuarto(0,1);
            h.addQuarto(0,3);
            h.addQuarto(0,2);
        }
        return h;
    }
}
