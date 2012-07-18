package ch.unibe.scg.doodle.properties;

import java.io.File;
import java.util.Properties;

public class DoodleDebugProperties {

	private static Properties properties;

	private static Properties properties() {
		if (properties == null)
			properties = PropertiesUtil.getDoodleDebugProperties();
		return properties;
	}

	/**
	 * Class intended to be static only.
	 */
	private DoodleDebugProperties() {

	}

	public static boolean betaMode() {
		return checkIfTrue("beta");
	}

	public static String getFeedbackMailAddress() {
		return properties().getProperty("feedbackMail");
	}

	public static boolean developMode() {
		return checkIfTrue("developMode");
	}

	public static boolean openInBrowser() {
		return checkIfTrue("openInBrowser");
	}

	private static boolean checkIfTrue(String key) {
		return properties().getProperty(key).equals("true");
	}

	public static File tempFileForOutput() {
		// Win7: C:\Users\<user>\AppData\Local\Temp\doodledebug\output.html
		return new File(System.getProperty("java.io.tmpdir")
				+ "/doodledebug/output.html");
	}

}