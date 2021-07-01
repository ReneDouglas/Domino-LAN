package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import control.ControlServer;

public class TelaServer extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private ControlServer controlServer;
	private JLabel labelStatusSever;

	public TelaServer() throws RemoteException {
		
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
		
		labelStatusSever = new JLabel();
		
		add(labelStatusSever).setBounds(10, 50, 300, 300);
		
		this.controlServer = new ControlServer(this);
		
		JButton sair = new JButton("Sair");
		sair.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					controlServer.stop();
					
				} catch (AccessException e1) {
					e1.printStackTrace();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				} catch (NotBoundException e1) {
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});
		add(sair).setBounds(this.getWidth()-70, 0, 70, 30);
		
		this.controlServer.start();
		
		setVisible(true);
		
	}

	public JLabel getStatusSever() {
		return labelStatusSever;
	}
	
	

}
