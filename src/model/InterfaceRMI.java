package model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface InterfaceRMI extends Remote{
	
	public static final String SERVER_NAME = "Domino";
	public static final int SERVER_PORT = 6789;
	
	public ArrayList<Peca> getPecas() throws RemoteException;
	public void atualizarPeca(Peca pecaVO) throws RemoteException;
	public ArrayList<Peca> getPecasJogadas() throws RemoteException;
	public void entrar(String nomeJogador) throws RemoteException;
	public String getVencedor() throws RemoteException;
	public void enviarMessage(String message) throws RemoteException;
	public String getMessage() throws RemoteException;
	public String[] getAdversario_E_Vez(String nomeJodador) throws RemoteException;
	public void setVez(String nomeJogador) throws RemoteException;
	public String getAdversario(String nomeJogador) throws RemoteException;
	public void setTocou(String nomeJogador) throws RemoteException;
	

}
