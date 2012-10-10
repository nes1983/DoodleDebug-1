package ch.unibe.scg.doodle.simon;

import java.io.IOException;
import java.net.URLClassLoader;
import java.net.UnknownHostException;
import java.util.Collection;

import ch.unibe.scg.doodle.DooMockup;
import ch.unibe.scg.doodle.Doodler;
import ch.unibe.scg.doodle.plugins.RenderingPlugin;
import ch.unibe.scg.doodle.rendering.RenderingRegistry;
import ch.unibe.scg.doodle.server.DoodleClientWorkspace;
import ch.unibe.scg.doodle.server.DoodleServer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

import de.root1.simon.Lookup;
import de.root1.simon.Registry;
import de.root1.simon.Simon;
import de.root1.simon.annotation.SimonRemote;
import de.root1.simon.exceptions.NameBindingException;

@SimonRemote(value = { SimonServerInterface.class })
public class SimonServer implements SimonServerInterface {

	/**
	 * Official IANA port for SIMON
	 */
	private static final int PORT = 4753;
	private Registry registry;
	private SimonClientInterface client;
	private Lookup lookup;
	/**
	 * In JSON format
	 */
	private XStream jstream;
	/**
	 * In XML format
	 */
	private XStream xstream;

	public static SimonServer instance;

	public SimonServer() throws UnknownHostException, IOException,
			NameBindingException {
		this.registry = Simon.createRegistry(PORT);
		registry.bind("DoodleServer", this);

		instance = this;

		refreshClientClassloading();
	}

	/**
	 * Refresh remote classes by creating a new XStream and a new Classloader.
	 */
	public void refreshClientClassloading() {
		URLClassLoader classLoader = DoodleClientWorkspace
				.getClientClassLoader();

		this.jstream = new XStream(new JettisonMappedXmlDriver());
		jstream.setMode(XStream.NO_REFERENCES);
		jstream.setClassLoader(classLoader);

		this.xstream = new XStream();
		xstream.setClassLoader(classLoader);
	}

	public void stop() {
		registry.unbind("DoodleServer");
		registry.stop();
		if (client != null)
			lookup.release(client);
	}

	/**
	 * 
	 * @param objectAsXML
	 * @param xml
	 *            If false: JSON
	 */
	@Override
	public void renderObject(String objectAsXML, boolean xml) {
		Object o;
		if (xml) {
			o = xstream.fromXML(objectAsXML);
		} else {
			o = jstream.fromXML(objectAsXML);
		}
		DooMockup.dle(o);
	}

	@Override
	public void renderObjects(String objectAsXML, String objectArrayAsXML,
			boolean xml) {
		Object o;
		Object[] os;
		if (xml) {
			o = xstream.fromXML(objectAsXML);
			os = (Object[]) xstream.fromXML(objectArrayAsXML);
		} else {
			o = jstream.fromXML(objectAsXML);
			os = (Object[]) jstream.fromXML(objectArrayAsXML);
		}
		DooMockup.dle(o, os);
	}

	@Override
	public void couldNotSend(String canonicalName) {
		Doodler.instance().couldNotRenderMessage(canonicalName);
	}

	@Override
	public void clearOutput() {
		DoodleServer.instance().clearOutput();
	}

	@Override
	public void firstRun() {
		DoodleServer.instance().firstRun();
	}

	@Override
	public void addPlugins(String pluginsAsXML) {
		Collection<RenderingPlugin> plugins = (Collection<RenderingPlugin>) jstream
				.fromXML(pluginsAsXML);
		RenderingRegistry.addPlugins(plugins);
	}

}
