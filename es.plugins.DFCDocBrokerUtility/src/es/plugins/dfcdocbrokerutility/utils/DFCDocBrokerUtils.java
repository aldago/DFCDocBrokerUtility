package es.plugins.dfcdocbrokerutility.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.emc.ide.external.dfc.procedurerunner.FilePathUtil;

import es.plugins.dfcdocbrokerutility.Activator;

public class DFCDocBrokerUtils {

	private static URL resourceUrl=null;
	private static Properties props = null;
	
	private static String getDFCpropsPath(){
		URL pluginLocation=com.emc.ide.external.dfc.Activator.getDefault().getBundle().getEntry("/");
	    URL absolutePluginLocation = null;
	    
		try {
			absolutePluginLocation = FileLocator.toFileURL(pluginLocation);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return FilePathUtil.convertFilePathToPlatformSpecificPath(absolutePluginLocation.getFile());
	    
	}
	private static void getResourceURL(){
		String path=getDFCpropsPath();
		try {
			resourceUrl = new URL(Constants.fileRef+path+Constants.docConfig);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	private static String getHostPort(){
    	String host=props.getProperty(Constants.docbrokerHost);
    	String port=props.getProperty(Constants.docbrokerPort);
    	String gRepository=props.getProperty(Constants.globalRegRepository);
    	String gUsername=props.getProperty(Constants.globalRegUsername);
    	String gPassword=props.getProperty(Constants.globalRegPassword);
    	
    	String result=null;
    	
    	if (host==null || host.length()==0){
    		return result;
    	}
    	if (port!=null){
    		result = host + Constants.docbrokerToken + port;
    	}
    	
    	result = host + Constants.docbrokerToken + Constants.defaultPort;	
    	
    	if (gRepository!=null){//Global Registry Info
    		result+=Constants.docbrokerToken + gRepository + Constants.docbrokerToken + gUsername + Constants.docbrokerToken + gPassword;
    	}
    	
    	return result;	 
	}
	
	public static String readDFCprops(){	
		InputStream stream = null;
		
		if (props!=null){
			return getHostPort();
		}else{
			try {
		    	if (resourceUrl == null) {
		    		getResourceURL();
		    	}
		    	props = new Properties();
		    	stream = resourceUrl.openStream();
		    	props.load(stream);

		    	return getHostPort();    	
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
	    			try {
	    				stream.close();
	    			} catch (IOException e) {
	    				e.printStackTrace();
	    			}
	    		}
		}
		return null;
	}
	
	public static void writeDFCprops(String value){
		FileOutputStream fos=null;
		try {
			String[] values=value.split(Constants.docbrokerToken);
			if (resourceUrl==null){
				getResourceURL();
			}
			fos = new FileOutputStream(resourceUrl.getFile());
	    	if (props==null){
	    		props = new Properties();
	    	}
			props.setProperty(Constants.docbrokerHost, values[0]);
			props.setProperty(Constants.docbrokerPort, values[1]);
			//Global Registry
			if (values.length>2){
				props.setProperty(Constants.globalRegRepository, values[2]);
				props.setProperty(Constants.globalRegUsername, values[3]);
				props.setProperty(Constants.globalRegPassword, values[4]);
			}
			props.store(fos, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void initProps(IPreferencesService prefs, String pluginID){
		String storedValues=prefs.getString(pluginID,Constants.propKey, null, null);
		String currentValue=readDFCprops();
		
		if (currentValue!=null){
			if (!storedValues.contains(currentValue)){
				if (storedValues.length()<1){
					storedValues=currentValue;
				}else{
					storedValues=storedValues+Constants.docbrokerListToken+currentValue;
				}
				
			}
		}
		
	}
	
	public static String getProps(){
		return Platform.getPreferencesService().getString(Activator.PLUGIN_ID,Constants.propKey, null, null);
	}
	
	public static void writeStatusBarMsg(final String message){
		final Display display = Display.getDefault();
		
		new Thread(){
			public void run(){
				display.syncExec(new Runnable() {

					public void run() {
						IWorkbench wb = PlatformUI.getWorkbench();
						IWorkbenchWindow win = wb.getActiveWorkbenchWindow();

						IWorkbenchPage page = win.getActivePage();

						IWorkbenchPart part = page.getActivePart();
						IWorkbenchPartSite site = part.getSite();

						IViewSite vSite = (IViewSite)site;

						IActionBars actionBars = vSite.getActionBars();

						if (actionBars == null) return ;

						IStatusLineManager statusLineManager = actionBars.getStatusLineManager();

						if (statusLineManager == null) return ;

						statusLineManager.setMessage(message);
					}
				});
			}
		}.start();
	}
}
