package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class TelaInicial extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JButton startServer, startClient, entrar;
	private JTextField nomeJogador, ipServer;

	public TelaInicial() {
		
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		setSize(400, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setUndecorated(true);
		
		JButton sair = new JButton("Sair");
		sair.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		add(sair).setBounds(this.getWidth()-70, 0, 70, 30);
		
		startServer = new JButton("Start Server");
		startServer.addActionListener(new StartServer());
		add(startServer).setBounds(100, 100, 200, 50);
		
		startClient= new JButton("Start Client");
		startClient.addActionListener(new StartClient());
		add(startClient).setBounds(100, 200, 200, 50);
		
		setVisible(true);
	}
	
	
	class StartServer implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				
				new TelaServer();
				dispose();
				
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			dispose();
			
		}
		
	}
	
	class StartClient implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			remove(startServer);
			remove(startClient);
			
			JLabel labelNomeJogador = new JLabel("Digite seu nome: ");
			add(labelNomeJogador).setBounds(10, 50, 150, 20);
			
			nomeJogador = new JTextField("Jogador1");
			add(nomeJogador).setBounds(150, 50, 200, 30);
			
			JLabel labelIpServer = new JLabel("Digite IP do Server: ");
			add(labelIpServer).setBounds(10, 110, 150, 20);
			
			ipServer = new JTextField("localhost");
			add(ipServer).setBounds(150, 110, 200, 30);
			
			entrar = new JButton("Entrar");
			entrar.addActionListener(new Entrar());
			add(entrar).setBounds(100, 200, 200, 50);
			
			repaint();
			
		}
		
	}
	
	class Entrar implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				new Partida(nomeJogador.getText().toString(), ipServer.getText().toString());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dispose();
			
		}
		
	}
	
	public static void main(String[] args) {
		new TelaInicial();
	}

}
