package Classes.graficas;

import Classes.Hotel;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Classes.graficas.Book.alturaPadrao;
import static Classes.graficas.Book.larguraPadrao;

public class MenuInicial extends JFrame {
    private JPanel mainPanel;
    private JPanel menuPanel;
    private JPanel contentPanel;
    private JButton cadastrarClienteButton;
    private JButton voltarButton;
    private JButton cadastrarQuartoButton;
    private JButton reservaButton;
    public MenuInicial(){
        setTitle("Menu Principal");
        setSize(larguraPadrao, alturaPadrao);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        Hotel hotel = getHotelPadraoTest();

        // Criação do painel principal
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Criação do painel do menu
        menuPanel = new JPanel();
        cadastrarClienteButton = new JButton("Cadastrar Cliente");
        cadastrarClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exibirMenuCadastroCliente(hotel);
            }
        });
        menuPanel.add(cadastrarClienteButton);

        // Botão de fazer reserva
        reservaButton = new JButton("Fazer Reserva");
        reservaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exibirMenuReserva(hotel);
            }
        });
        menuPanel.add(reservaButton);

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


    private void exibirMenuCadastroCliente(Hotel hotel) {
        CadastroClienteGUI c = new CadastroClienteGUI(hotel);
        c.setVisible(true);
    }
    private void exibirMenuReserva(Hotel hotel) {
        MenuCheckIn menuCheckIn = new MenuCheckIn(hotel);
        menuCheckIn.setVisible(true);
    }

    private void voltarAoMenuPrincipal() {
        contentPanel.removeAll();

        // Criação do painel de conteúdo padrão
        JLabel label = new JLabel("Bem-vindo ao Menu Principal");
        contentPanel.add(label);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private Hotel getHotelPadraoTest(){
        Hotel h = new Hotel(
                "Hotel de POO",
                "Rua dos bobos numero zero",
                "(42) 4242-4242",
                "Hotel 5 estrelas"
        );
        for (int i=0;i<5;i++){
            h.addQuarto(1,1);
            h.addQuarto(1,0);
            h.addQuarto(0,1);
            h.addQuarto(0,3);
            h.addQuarto(0,2);
        }
        return h;
    }

}
