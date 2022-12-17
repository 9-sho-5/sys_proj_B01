package server;

import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.plus.webapp.PlusConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.FragmentConfiguration;
import org.eclipse.jetty.webapp.JettyWebXmlConfiguration;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;

public class AppServer {

	public static void main(String[] args) throws Exception {
		if (args.length != 3) {
			System.out.println("AppServer:");
			System.out.println("   port");
			System.out.println("   context_path");
			System.out.println("   base_directory_path");
			System.exit(1);
	    }
	    int port = Integer.parseInt(args[0]);
	    String contextPath = args[1];
	    String baseDirectoryPath = args[2];
        Configuration[] configurations = {
        	new AnnotationConfiguration(),
        	new WebInfConfiguration(),
        	new WebXmlConfiguration(),
        	new MetaInfConfiguration(),
        	new FragmentConfiguration(),
        	new EnvConfiguration(),
        	new PlusConfiguration(),
        	new JettyWebXmlConfiguration()
        };
		WebAppContext context = new WebAppContext();
		context.setContextPath(contextPath);
		context.setResourceBase(baseDirectoryPath);
		context.setConfigurations(configurations);
		Server server = new Server(port);
		server.setHandler(context);
 		server.start();
		server.join();
	}

}
