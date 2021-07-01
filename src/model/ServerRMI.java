package model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;

import model.DAOs.PecaDAO;

public class ServerRMI extends UnicastRemoteObject implements InterfaceRMI{
	private static final long serialVersionUID = 1L;
	private ArrayList<Peca> pecasNoJogo;
	private ArrayList<String> jogadores;
	private String nomeJogadorTocou = "";
	private String vencedor = "", messageChat = "", Adversario_E_Vez[] = {"Aguardando", ""};//indice = 0 -> nomeAdversario - indice = 1 -> nomeJogadorVez
	
	
	public ServerRMI(int porta) throws RemoteException {
		super(porta);
		
		for (int i = 0; i < 28; i++) {	
			PecaDAO.inserir(new Peca(i+1, 0, 440, false, "peca_"+(i+1)+".png"));
		}
		
		this.pecasNoJogo = new ArrayList<Peca>();
		this.jogadores = new ArrayList<String>();
		
	}
	
	@Override
	public synchronized ArrayList<Peca> getPecas() {
		
		ArrayList<Peca> pecasJogador = new ArrayList<Peca>();
		ArrayList<Peca> pecasTemp = PecaDAO.listar();
		
		for (int i = 0; i < 7; i++) {
			
			Collections.shuffle(pecasTemp);
			
			pecasJogador.add(pecasTemp.get(i));
			
			pecasNoJogo.add(pecasTemp.get(i));
			
			PecaDAO.excluir(pecasTemp.get(i));
			
//			pecasTemp.remove(i); Se fosse com BD seria necessário
			
		}
		
		return pecasJogador;
	}


	@Override
	public synchronized void atualizarPeca(Peca pecaVO) throws RemoteException {
			
		for (int i = 0; i < pecasNoJogo.size(); i++) {
			
			if(pecaVO.id == pecasNoJogo.get(i).id){
				pecasNoJogo.set(i, pecaVO);
				break;
			}
			
		}
			
	}


	@Override
	public synchronized ArrayList<Peca> getPecasJogadas() throws RemoteException {
		ArrayList<Peca> pecasJogadas = new ArrayList<Peca>();
		
		for (int i = 0; i < pecasNoJogo.size(); i++) {
			
			if(pecasNoJogo.get(i).foiJogada){
				
				pecasJogadas.add(pecasNoJogo.get(i));
			}
			
		}
		
		return pecasJogadas;
	}

	@Override
	public synchronized void entrar(String nomeJogador) throws RemoteException {
		
		this.jogadores.add(nomeJogador);
		
	}

	@Override
	public synchronized String getVencedor() throws RemoteException {
		
		int contJogador1 = 0, contJogador2 = 0;
//		System.out.println("verificando vencedor");
//		System.out.println(jogadores);
		
		if (jogadores.size() == 2) {
			
			for (Peca peca : pecasNoJogo) {
//				System.out.println(peca.nomeJogador);
				
				if (peca.nomeJogador.equals(jogadores.get(0))) {
					contJogador1 += 1;
					
					if (contJogador1 == 7) {
						vencedor = jogadores.get(0);
						jogadores.clear();
						pecasNoJogo.clear();
						
						break;
						
					}
					
				}
				
				if (peca.nomeJogador.equals(jogadores.get(1))) {
					contJogador2 += 1;
					
					if (contJogador2 == 7) {
						vencedor = jogadores.get(1);
						jogadores.clear();
						pecasNoJogo.clear();
						
						break;
						
					}
				}
			}
		}
		return this.vencedor;
	}

	public ArrayList<String> getJogadores() {
		return jogadores;
	}

	@Override
	public synchronized void enviarMessage(String message) throws RemoteException {
		
		this.messageChat = message += this.messageChat;
		
	}

	@Override
	public synchronized String getMessage() throws RemoteException {
		
		return this.messageChat;
	}

	@Override
	public synchronized String[] getAdversario_E_Vez(String nomeJogador) throws RemoteException {
		
		if (jogadores.size() == 2 && Adversario_E_Vez[0].equals("Aguardando") && nomeJogador.equals(jogadores.get(0))) {
			Adversario_E_Vez[0] = jogadores.get(1);
			Adversario_E_Vez[1] = jogadores.get(0);
			
			System.out.println(nomeJogadorTocou);
		}if (!nomeJogadorTocou.equals("")) {
			
			Adversario_E_Vez[1] = nomeJogadorTocou + " tocou!";
			
//			if (nomeJogador.equals(nomeJogadorTocou) && nomeJogador.equals(jogadores.get(0))) {
//				Adversario_E_Vez[1] = jogadores.get(1) + " tocou!";
//				
//			}else if (nomeJogador.equals(nomeJogadorTocou) && nomeJogador.equals(jogadores.get(1))) {
//				Adversario_E_Vez[1] = jogadores.get(0) + " tocou!";
//				
//			}
			
		}
		
		
		return this.Adversario_E_Vez;
	}

	@Override
	public synchronized void setVez(String nomeJogador) throws RemoteException {
		
		if (nomeJogador.equals(jogadores.get(0))) {
			Adversario_E_Vez[1] = jogadores.get(1);
			
		}else {
			Adversario_E_Vez[1] = jogadores.get(0);
		}
		
	}

	@Override
	public synchronized String getAdversario(String nomeJogador) throws RemoteException {
		String adversario = "";
		if (jogadores.size() == 2) {
			
			
			if (nomeJogador.equals(jogadores.get(0))) {
				adversario = jogadores.get(1);
				
			}else {
				adversario = jogadores.get(0);
			}
			
			
		}
		return adversario;
		
	}

	@Override
	public synchronized void setTocou(String nomeJogador) throws RemoteException {
		System.out.println(nomeJogador);
		this.nomeJogadorTocou = nomeJogador;
		
	}
}
