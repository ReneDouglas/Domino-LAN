package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import model.InterfaceRMI;
import view.Partida;

public class ControlToquei implements ActionListener{
	private Registry registry;
	private InterfaceRMI server;
	private Partida tela;
	
	
	public ControlToquei(Partida tela) throws RemoteException, NotBoundException {
		
		this.tela = tela;
		this.registry = LocateRegistry.getRegistry( this.tela.getIpSever(), InterfaceRMI.SERVER_PORT );
		
		this.server = (InterfaceRMI) registry.lookup( "Domino" );
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("dsdsdsd");
		try {
			
			server.setTocou(this.tela.getNomeJogador());
			
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

}
