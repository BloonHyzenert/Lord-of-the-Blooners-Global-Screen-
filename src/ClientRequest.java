import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class ClientRequest  implements Runnable {
	
	private Socket sock; 	   
	private PrintWriter writer = null;	   
	private BufferedInputStream reader = null;
	private Player player;
	private String toSend;

    private boolean closeConnexion = false;

	public ClientRequest(Socket client){
		sock=client;
	}

	@Override
	public void run() {
		// Traitement de la connexion
		//System.err.println("Lancement du traitement de la connexion cliente");

	      //tant que la connexion est active, on traite les demandes
	      while(!sock.isClosed()){
	         
	         try { 
	     		sock.setSoTimeout(1000);
	            writer = new PrintWriter(sock.getOutputStream());
	            reader = new BufferedInputStream(sock.getInputStream());
	            
	            //On attend la demande du client            
	            String response = read();
	            //System.out.println(response);
	            //InetSocketAddress remote = (InetSocketAddress)sock.getRemoteSocketAddress();
	            if(response!="") {
	            String tabInfos[] = response.split(",");
	            if(Integer.parseInt(tabInfos[0])==0) {	
	            	player = new Player(this,tabInfos[1]);            
	            	toSend = player.getPlayerID() +","+player.getPseudo()+","+player.getTeam().getName()+","+player.getPosition().toString();
	            }
	            else if(player.getPlayerID()==Integer.parseInt(tabInfos[0])) {
	            		player.move(Integer.parseInt(tabInfos[1]), Integer.parseInt(tabInfos[2]));
	            		toSend = player.getPlayerID()+","+player.getPosition().toString();
	            		System.out.println("Le joueur n°"+player.getPlayerID()+" est à la position "+player.getPosition().toString());
	            }
	            		
	            else toSend="Erreur";
	            
	          //System.out.println(toSend);
	            //Envoie la réponse au client
	            writer.write(toSend);
	            writer.flush();
	            }
	            
	            if(closeConnexion || Configuration.END){
	               //System.err.println("COMMANDE CLOSE DETECTEE ! ");
	            	closeConnexion=true;
	 	          player.getTeam().removePlayer(player);
	               writer = null;
	               reader = null;
	               sock.close();
	               break;
	            }
	         }catch(SocketException e){
		    	  closeConnexion=true;  	             
	            break;
	         } catch (IOException e) {
		    	  closeConnexion=true;  
	         }         
	      }
	      
	   }

	   //Méthode pour lire les réponses

	   private String read() throws IOException{      
	      String response = "";
	      int stream=0;
	      byte[] b = new byte[4096];
	      stream = reader.read(b);
	      if(stream==-1) {
	    	  closeConnexion=true;
	      }
	      else response = new String(b, 0, stream);

	      return response;
	   }
}
