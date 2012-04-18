package ch.unibe.scg.doodle.simon;

import java.io.IOException;
import java.net.UnknownHostException;

import org.eclipse.swt.widgets.Display;

import ch.unibe.scg.doodle.views.HtmlShow;
import de.root1.simon.Registry;
import de.root1.simon.Simon;
import de.root1.simon.annotation.SimonRemote;
import de.root1.simon.exceptions.NameBindingException;

@SimonRemote(value = { SimonServerInterface.class })
public class SimonServer implements SimonServerInterface {

	private Registry registry;

	public SimonServer(int port) throws UnknownHostException, IOException,
			NameBindingException {
		this.registry = Simon.createRegistry(port);
		registry.bind("DoodleServer", this);
		
		// XXX: Had problems when trying to access server too soon, waiting for a short interval seems to help, idk why...
//		try {
//			Thread.sleep(50);
//		} catch (InterruptedException e) {
//			throw new RuntimeException(e);
//		}
	}

	@Override
	public void ShowHtml(String html) {
		System.out.println("Server: Received html code.");
		
		// TODO display in eclipse browser
		Runnable htmlShow = new HtmlShow(html);
		Display.getDefault().syncExec(htmlShow);
	}

	public void stop() {
		registry.unbind("DoodleServer");
		registry.stop();
	}

}