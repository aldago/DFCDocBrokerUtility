package es.plugins.dfcdocbrokerutility.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import es.plugins.dfcdocbrokerutility.Activator;
import es.plugins.dfcdocbrokerutility.utils.Constants;
import es.plugins.dfcdocbrokerutility.utils.DFCDocBrokerUtils;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		if (store.getString(Constants.P_DOCBROKERS).length()<=0){
			String defaultDocbroker=DFCDocBrokerUtils.readDFCprops();
			if (defaultDocbroker!=null)
				store.setDefault(Constants.P_DOCBROKERS, defaultDocbroker);
			store.setValue(Constants.P_DOCBROKERS, defaultDocbroker);
		}
	}	
}