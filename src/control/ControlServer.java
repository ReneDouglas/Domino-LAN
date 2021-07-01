package control;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import model.InterfaceRMI;
import model.ServerRMI;
import view.TelaServer;

public class ControlServer {
	private TelaServer telaServer;
	private ServerRMI server;
	private Registry serverRegistry;
	private Thread threadServer;
	private String conteudoStatusServer;

	public ControlServer(TelaServer tela) {
		this.telaServer = tela;
		
		this.threadServer = new Thread(new Runnable() {
			@Override
			public void run() {
				int numJogadoresOld = 0;
				ArrayList<String> jogadores; 
				
				while (true) {
					
					jogadores = server.getJogadores();
					
					
					if (jogadores.size() != numJogadoresOld && jogadores.size() > 0) {
						
						conteudoStatusServer = conteudoStatusServer.substring(0, conteudoStatusServer.length()-7);
						conteudoStatusServer += "<br><br>Jogador " + jogadores.get(jogadores.size() - 1) + " está conectado!</html>";
						
						telaServer.getStatusSever().setText( conteudoStatusServer );
						
						numJogadoresOld++;
						
					}else if (jogadores.size() == 0) {
						
						telaServer.getStatusSever().setText("<html>Servidor em execução!</html>");
						numJogadoresOld = 0;
						
					}
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						System.out.println("Exception na thread do Servidor");
						e.printStackTrace();
					}
				}
			}
		});

	}

	public void start() throws RemoteException {

		serverRegistry = LocateRegistry.createRegistry(InterfaceRMI.SERVER_PORT);

		try {
			server = new ServerRMI(InterfaceRMI.SERVER_PORT); // Cria o objeto remoto.
			serverRegistry.rebind(InterfaceRMI.SERVER_NAME, server); // Cadastra o serviço.
			
			telaServer.getStatusSever().setText("<html>Servidor em execução!</html>");
			
			this.conteudoStatusServer = this.telaServer.getStatusSever().getText();
			
			threadServer.start();
			
		} catch (Exception e) {
			telaServer.getStatusSever().setText("Erro ao iniciar o Server!");
			
			e.printStackTrace();
		}

	}
	
	public void stop() throws AccessException, RemoteException, NotBoundException {
		serverRegistry.unbind(InterfaceRMI.SERVER_NAME);
		
	}
}
