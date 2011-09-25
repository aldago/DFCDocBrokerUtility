package es.plugins.dfcdocbrokerutility;

import es.plugins.dfcdocbrokerutility.utils.Constants;
import es.plugins.dfcdocbrokerutility.utils.DFCDocBrokerUtils;

public class StartupClass implements org.eclipse.ui.IStartup{

	public void earlyStartup() {
		String host=DFCDocBrokerUtils.readDFCprops();
		DFCDocBrokerUtils.writeStatusBarMsg(Constants.statusBarHostPrefix+host.substring(0,host.indexOf(Constants.docbrokerToken)));
	}

}
