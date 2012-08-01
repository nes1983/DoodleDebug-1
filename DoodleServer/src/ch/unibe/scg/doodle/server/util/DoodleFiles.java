package ch.unibe.scg.doodle.server.util;

//import static org.hamcrest.CoreMatchers.*;
//import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
//import org.junit.Test;
import org.osgi.framework.Bundle;

public class DoodleFiles {
	public static IPath getFilePath(String file) {
		Bundle bundle = Platform.getBundle("DoodleServer");
		Path path = new Path(file);
		URL fileURL = FileLocator.find(bundle, path, null);
		String result = "";
		try {
			result = FileLocator.resolve(fileURL).getPath();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return Path.fromOSString(result);
	}

}
