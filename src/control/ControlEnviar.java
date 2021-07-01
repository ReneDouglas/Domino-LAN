package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import view.Partida;
import model.InterfaceRMI;

public class ControlEnviar implements ActionListener{
	
	private Registry registry;
	private InterfaceRMI server;
	private Partida tela;
	
	public ControlEnviar(Partida partida) throws RemoteException, NotBoundException {
		
		this.tela = partida;
		this.registry = LocateRegistry.getRegistry( this.tela.getIpSever(), InterfaceRMI.SERVER_PORT );
		
		this.server = (InterfaceRMI) registry.lookup( "Domino" );
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if (!this.tela.getTextFieldMessage().getText().equals("")) {
			
			try {
				this.server.enviarMessage(">> " + this.tela.getNomeJogador() + " diz:\n" + this.tela.getTextFieldMessage().getText() + "\n\n");
				this.tela.getTextFieldMessage().setText("");
				
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
}
