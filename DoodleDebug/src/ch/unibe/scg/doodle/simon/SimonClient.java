package ch.unibe.scg.doodle.simon;

import java.net.UnknownHostException;


import de.root1.simon.Lookup;
import de.root1.simon.Simon;
import de.root1.simon.annotation.SimonRemote;
import de.root1.simon.exceptions.EstablishConnectionFailed;
import de.root1.simon.exceptions.LookupFailedException;

@SimonRemote(value = {SimonClientInterface.class})
public class SimonClient implements SimonClientInterface {
	
	private Lookup lookup;
	private SimonServerInterface server;

	public SimonClient(int port) throws LookupFailedException, EstablishConnectionFailed, UnknownHostException {
		this.lookup = Simon.createNameLookup("localhost", port);
		server = (SimonServerInterface) lookup.lookup("DoodleServer");
	}

	@Override
	public void sendHtml(String html) {
		server.showHtml(html);
	}

	public void stop() {
		lookup.release(server);
	}

}