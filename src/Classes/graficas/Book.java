package Classes.graficas;

import Classes.Hotel;

import javax.swing.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Book extends MenuBase {
    private JPanel mainPanel;
    private JPanel menuPanel;
    private JPanel contentPanel;
    private JButton clientesButton;
    private JButton quartosButton;

    public Book(Hotel hotel) {
        super(hotel, "Book");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                getHotel().salvaArquivosCliente();
                getHotel().salvaArquivoQuarto();
            }
        });

        // Criação do painel principal
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.add(Box.createVerticalGlue());

        // Criação do painel do menu
        menuPanel = new JPanel();

        // Criação do painel de conteúdo
        contentPanel = new JPanel();
        contentPanel.setLayout(new FlowLayout());
        JLabel label = new JLabel("Gerenciamento do Hotel");
        label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 30));
        contentPanel.add(label);

        // Menu de clientes
        clientesButton = new JButton("Menu dos Clientes");
        clientesButton.addActionListener(e -> criarMenu(MenuClientes.class, hotel));
        menuPanel.add(clientesButton);

        // Menu de quartos
        quartosButton = new JButton("Menu dos Quartos");
        quartosButton.addActionListener(e -> criarMenu(MenuQuartos.class, hotel));
        menuPanel.add(quartosButton);

        // Menu de reserva
        quartosButton = new JButton("Reservar um Quarto");
        quartosButton.addActionListener(e -> criarMenu(MenuReserva.class, hotel));
        menuPanel.add(quartosButton);

        // Adiciona os painéis ao painel principal
        mainPanel.add(contentPanel);
        mainPanel.add(menuPanel);
        mainPanel.add(Box.createVerticalGlue());

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
}
