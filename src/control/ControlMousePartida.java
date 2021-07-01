package control;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import model.InterfaceRMI;
import view.Partida;

public class ControlMousePartida extends MouseAdapter {
	private Registry registry;
	private InterfaceRMI server;
	private Partida pane;
	
	public ControlMousePartida(Partida pane) throws RemoteException, NotBoundException {
		this.pane = pane;
		this.registry = LocateRegistry.getRegistry( this.pane.getIpSever(), InterfaceRMI.SERVER_PORT );
		
		this.server = (InterfaceRMI) registry.lookup( "Domino" );
	}
	
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		
		if (this.pane.isSuaVez()) {
			for (int i = 0; i < pane.pecas.size(); i++) {
				if((e.getX() > pane.pecas.get(i).posX && e.getX() < (pane.pecas.get(i).posX + 40)) && (e.getY() > pane.pecas.get(i).posY && e.getY() < (pane.pecas.get(i).posY + 30)))
				{
					pane.pecas.get(i).arrastar = true;
					break;
				}
			}
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		super.mouseDragged(e);
		
		if (this.pane.isSuaVez()) {
			for (int i = 0; i < pane.pecas.size(); i++) {
				if(pane.pecas.get(i).arrastar == true){
					
					pane.pecas.get(i).posX = e.getX() - (pane.pecas.get(i).getIcon().getIconWidth()/2);
					pane.pecas.get(i).posY = e.getY() - (pane.pecas.get(i).getIcon().getIconHeight()/2);
					pane.pecas.get(i).setLocation(pane.pecas.get(i).posX, pane.pecas.get(i).posY);
					//pane.pecas.get(i).setBounds(pane.pecas.get(i).posX, pane.pecas.get(i).posY = e.getY(), pane.pecas.get(i).getIcon().getIconWidth(), pane.pecas.get(i).getIcon().getIconHeight());
					//pane.panel.repaint();
				}
			}
			
		}
		
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
		
		if (this.pane.isSuaVez()) {
			for (int i = 0; i < pane.pecas.size(); i++) {
				if(pane.pecas.get(i).arrastar == true){
					pane.pecas.get(i).arrastar = false;
					
					if (pane.pecas.get(i).posY < 400) {
						pane.pecas.get(i).foiJogada = true;
						
						try {
							
							server.setVez(this.pane.getNomeJogador());
							server.setTocou("");
							
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						pane.setSuaVez(false);
						
					}
					
				}
			}
		}
	}
}
