package control;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import view.Partida;
import model.InterfaceRMI;
import model.Peca;

public class ControlClient implements Runnable{
	
	private Registry registry;
	private InterfaceRMI server;
	public ArrayList<Peca> pecas;
	private Partida partida;
	private Peca pecaVO;
	private boolean jogando;
	
	public ControlClient(Partida partida) {
		try {
			
			this.partida = partida;
			this.jogando = true;
			this.pecas = new ArrayList<Peca>();
			
			registry = LocateRegistry.getRegistry( this.partida.getIpSever(), 6789 );
			
			server = (InterfaceRMI) registry.lookup( "Domino" );
			server.entrar(this.partida.getNomeJogador());
			
		} catch (RemoteException e) {
			
			JOptionPane.showMessageDialog(this.partida.getPanel(), "Erro ao conectar");
			
			e.printStackTrace();
		} catch (NotBoundException e) {
			
			JOptionPane.showMessageDialog(this.partida.getPanel(), "Erro ao conectar");
			
			e.printStackTrace();
		}
		
	}
	
	public void run() {
		
		try {
			pecas = server.getPecas();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < pecas.size(); i++) {
			
			pecaVO = new Peca(pecas.get(i).id, pecas.get(i).posX, pecas.get(i).posY, pecas.get(i).virada, pecas.get(i).img);
			//System.out.println("id: "+pecas.get(i).id+"|img: "+pecas.get(i).img);
			pecaVO.setIcone(pecas.get(i).img);
			
			if(i == 0){
				
				pecaVO.posX = 10;
				pecaVO.setBounds(pecaVO.posX, pecaVO.posY, pecaVO.getIcon().getIconWidth(), pecaVO.getIcon().getIconHeight());
			}
			else{
				
				pecaVO.posX = 10 +(i*30);
				pecaVO.setBounds(pecaVO.posX, pecaVO.posY, pecaVO.getIcon().getIconWidth(), pecaVO.getIcon().getIconHeight());
			}
			
			partida.pecas.add(pecaVO);
			partida.getPanel().add(partida.pecas.get(i));
			partida.getPanel().setComponentZOrder(partida.pecas.get(i), 0);
			
			
		}
		
		partida.getPanel().repaint();
		
		while(jogando){
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			try {
				
				getVencedor();
				
				atualizarPeca();
				
				atualizarTabuleiro();
				
				atualizarMessages();
				
				atualizarStatus();
				
				
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	private void atualizarTabuleiro() throws RemoteException {
		
		ArrayList<Peca> pecasJogadas = server.getPecasJogadas();
		
//		partida.getPanel().removeAll();
		partida.getPanel().repaint();
		
		for (Peca peca : pecasJogadas) {
			
			partida.getPanel().add(peca);
			partida.getPanel().setComponentZOrder(peca, 0);
			
		}
		partida.getPanel().repaint();
		
	}
	
	private void atualizarPeca() throws RemoteException {
		
		for (int i = 0; i < partida.pecas.size(); i++) {
			
			if(partida.pecas.get(i).foiJogada == true){
				
				Peca pecaTemp = partida.pecas.get(i);
				
				partida.getPanel().remove(partida.pecas.get(i));
				partida.pecas.remove(i);
				
				pecaTemp.nomeJogador = this.partida.getNomeJogador();
				
				server.atualizarPeca(pecaTemp);
				break;
				
			}
		}
	}
	
	protected void getVencedor() throws RemoteException {
		String vencedor = server.getVencedor();
//		System.out.println(vencedor);
		if (!vencedor.equals("")) {
			JOptionPane.showMessageDialog(this.partida.getPanel(), "O Jogador " + vencedor + " venceu!");
			jogando = false;
			
		}
		
	}
	
	protected void atualizarMessages() throws RemoteException {
		this.partida.getTextMessanges().setText(server.getMessage());
		
	}
	
	protected void atualizarStatus() throws RemoteException {
		
		String nomeAdversario_E_vez[] = server.getAdversario_E_Vez(this.partida.getNomeJogador());
		
		if (nomeAdversario_E_vez[1].equals(this.partida.getNomeJogador())) {
			this.partida.setSuaVez(true);
			this.partida.getLbStatusVez().setText("Sua vez!");
			
		}else if (nomeAdversario_E_vez[0].equals("Aguardando")) {
			
			this.partida.getLbStatusVez().setText(nomeAdversario_E_vez[0] + " Adversário!");
			
		} else {
			
			if (!nomeAdversario_E_vez[1].equals(this.partida.getNomeJogador()) && !nomeAdversario_E_vez[1].contains("tocou!")) {
				this.partida.setSuaVez(false);
				this.partida.getLbStatusVez().setText(nomeAdversario_E_vez[1] + " vai jogar!");
				
			}else if (nomeAdversario_E_vez[1].contains("tocou!") && !this.partida.getNomeJogador().equals(nomeAdversario_E_vez[1].substring(0, nomeAdversario_E_vez[1].length()-7))) {
				this.partida.getLbStatusVez().setText(nomeAdversario_E_vez[1]);
				this.partida.setSuaVez(true);
				
			}else if (nomeAdversario_E_vez[1].contains("tocou!") && this.partida.getNomeJogador().equals(nomeAdversario_E_vez[1].substring(0, nomeAdversario_E_vez[1].length()-7))) {
				this.partida.setSuaVez(false);
			}
			
		}
			
		
		String nomeAdversario = server.getAdversario(this.partida.getNomeJogador());
		
		if (!nomeAdversario.equals("") && this.partida.getLbAdversario().getText().equals("")) {
			
			this.partida.getLbAdversario().setText("Adversário: " + nomeAdversario);
			
		}
		
		
	}
	
}
