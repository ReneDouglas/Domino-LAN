package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;

import control.ControlEnviar;
import control.ControlKeyPartida;
import control.ControlMousePartida;
import control.ControlClient;
import control.ControlToquei;
import model.Peca;

import java.awt.Color;
import java.awt.Label;
import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Partida{
	
	private JImagePanel panel;
	private JFrame partida;
	public ArrayList<Peca> pecas;
	private Thread t;
	private String nomeJogador, ipSever;
	private Label lbNomeJogador, lbAdversario, lbStatusVez;
	private JButton btnTocou, btnEnviar;
	private JScrollPane paneMessage;
	private JTextPane textMessange;
	private JTextField TextFieldMessage;
	private boolean suaVez;

	public Partida(String nomJogador, String ipServer) throws RemoteException, NotBoundException{
		
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		partida = new JFrame("Domino");
		partida.setSize(705, 525);
		partida.setResizable(false);
		partida.setLocationRelativeTo(null);
		partida.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		partida.getContentPane().setLayout(null);
		partida.setUndecorated(true);
		
		try {
			panel = new JImagePanel(new File("src/view/sprites/mesa.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		panel.setLayout(null);
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(0, 0, 705, 525);
		panel.setFocusable(true);
		partida.getContentPane().add(panel);
		
		pecas = new ArrayList<Peca>();
		
		panel.addMouseListener(new ControlMousePartida(this));
		panel.addMouseMotionListener(new ControlMousePartida(this));
		panel.addKeyListener(new ControlKeyPartida(this));
		
		this.nomeJogador = nomJogador;
		this.ipSever = ipServer;
		
		this.lbNomeJogador = new Label("Jogador: " + this.nomeJogador);
		panel.add(lbNomeJogador).setBounds(240, 410, 200, 20);
		
		this.lbAdversario= new Label("");
		panel.add(lbAdversario).setBounds(240, 430, 200, 20);
		
		this.lbStatusVez= new Label();
//		this.lbTocou.
		panel.add(lbStatusVez).setBounds(240, 450, 200, 20);
		
		this.btnTocou = new JButton("Toquei");
		this.btnTocou.addActionListener(new ControlToquei(this));
		panel.add(btnTocou).setBounds(240, 480, 150, 30);
		
		this.TextFieldMessage = new JTextField("");
		panel.add(TextFieldMessage).setBounds(450, 407, 190, 26);
		
		this.btnEnviar = new JButton("Envi.");
		this.btnEnviar.addActionListener(new ControlEnviar(this));
		panel.add(btnEnviar).setBounds(640, 407, 60, 26);
		
		this.textMessange = new JTextPane();
		this.textMessange.setEditable(false);
		
		this.paneMessage = new JScrollPane(this.textMessange);
		panel.add(paneMessage).setBounds(450, 432, 250, 90);
		
		this.suaVez = false;
		
		t = new Thread(new ControlClient(this));
		t.start();
		
		partida.setVisible(true);
	}

	public String getNomeJogador() {
		return nomeJogador;
	}

	public String getIpSever() {
		return ipSever;
	}
	
	public JImagePanel getPanel() {
		return panel;
	}

	public JScrollPane getPaneMessage() {
		return paneMessage;
	}

	public JTextPane getTextMessanges() {
		return textMessange;
	}

	public JTextField getTextFieldMessage() {
		return TextFieldMessage;
	}

	public boolean isSuaVez() {
		return suaVez;
	}

	public void setSuaVez(boolean suaVez) {
		this.suaVez = suaVez;
	}

	public Label getLbStatusVez() {
		return lbStatusVez;
	}

	public Label getLbAdversario() {
		return lbAdversario;
	}
	
	
	
}
