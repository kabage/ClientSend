import java.io.IOException;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

public class CmdClient {
	AbstractXMPPConnection conn2;
	String imsi, key1, key2;
	int is_connected = 0;
	String service_name = "candr.com";
	String server = "176.58.109.141"; // 192.168.43.56
	int port = 5222;

	public static void main(String[] args) {

		new Cli(args).parse();
	}

	public void listenforIncoming() {

		StanzaListener listener = new StanzaListener() {
			@Override
			public void processPacket(Stanza arg0) throws NotConnectedException {
				// TODO Auto-generated method stub
				// String from = arg0.getFrom();
				
				System.out.println(arg0.toString());
			}
		};
		conn2.addSyncStanzaListener(listener, null);
	}

	public void connectToServer() {

		try {
			conn2.connect();
		} catch (SmackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn2.login();
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SmackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void connectsendMessage(String key1, String key2, String imsi,
			String phone_number, String message, String log_tag) {

		XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration
				.builder().setUsernameAndPassword(key1, key2)
				.setServiceName(service_name).setHost(server).setPort(5222)
				.setConnectTimeout(10000)
				.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
				.build();

		conn2 = new XMPPTCPConnection(config);

		conn2.addConnectionListener(new ConnectionListener() {

			@Override
			public void reconnectionSuccessful() {
				// TODO Auto-generated method stub

			}

			@Override
			public void reconnectionFailed(Exception arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void reconnectingIn(int arg0) {
				// TODO Auto-generated method stub
				System.out.println(String.valueOf(arg0));
			}

			@Override
			public void connectionClosedOnError(Exception arg0) {
				// TODO Auto-generated method stub
				connectToServer();
			}

			@Override
			public void connectionClosed() {
				// TODO Auto-generated method stub
				connectToServer();
			}

			@Override
			public void connected(XMPPConnection arg0) {
				// TODO Auto-generated method stub
				System.out.println("connected to server");
				listenforIncoming();
				is_connected = 1;
			}

			@Override
			public void authenticated(XMPPConnection arg0, boolean arg1) {
				// TODO Auto-generated method stub
				System.out.println("the user has been authenticated");

			}
		});
		try {
			conn2.connect();

		} catch (SmackException e) {
			System.out.println(e.toString());

			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.toString());

			e.printStackTrace();
		} catch (XMPPException e) {
			System.out.println(e.toString());

			e.printStackTrace();
		}
		try {
			conn2.login();
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SmackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sendMessage(imsi, phone_number, message, log_tag);

		while (true) {

		}

	}

	public void sendMessage(String imsi, String phone_number, String message,
			String log_tag) {
		ChatManager chatmanager = ChatManager.getInstanceFor(conn2);

		Chat newChat = chatmanager.createChat(imsi + "@candr.com");

		Message newMessage = new Message();
		newMessage.addBody("phone", phone_number);
		newMessage.setBody(message);
		newMessage.setTo(imsi + "@candr.com");
		try {
			newChat.sendMessage(newMessage);
		} catch (NotConnectedException e) {
			// TODO Auto-generated catch block
			System.out.println("could not send the message" + "... "
					+ e.toString());
			e.printStackTrace();
		}

		System.out.println(log_tag);
	}

}
