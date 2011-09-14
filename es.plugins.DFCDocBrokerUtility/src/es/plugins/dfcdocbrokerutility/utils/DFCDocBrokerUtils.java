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
	
	public static String readDFCprops(){	
		InputStream stream = null;
		
		if (props!=null){
			return props.getProperty(Constants.docbrokerHost);
		}else{
			try {
		    	if (resourceUrl == null) {
		    		getResourceURL();
		    	}
		    	props = new Properties();
		    	stream = resourceUrl.openStream();
		    	props.load(stream);

		    	String host=props.getProperty(Constants.docbrokerHost);
		    	String port=props.getProperty(Constants.docbrokerPort);
		    	if (host==null || host.length()==0){
		    		return null;
		    	}
		    	if (port!=null){
		    		return host + Constants.docbrokerToken + port;
		    	}
		    	return host + Constants.docbrokerToken + Constants.defaultPort;	    	
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
}
