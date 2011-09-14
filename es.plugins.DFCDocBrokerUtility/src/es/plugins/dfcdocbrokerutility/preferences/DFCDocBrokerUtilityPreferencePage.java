package es.plugins.dfcdocbrokerutility.preferences;

import java.util.regex.Pattern;

import org.eclipse.jface.preference.*;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import es.plugins.dfcdocbrokerutility.Activator;
import es.plugins.dfcdocbrokerutility.utils.Constants;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class DFCDocBrokerUtilityPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	private IWorkbench wbench;
	
	public DFCDocBrokerUtilityPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription(Constants.preferencesPageDesc);
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		TableFieldEditor folderCommandsTableEditor = new CommandTableEditor(Constants.P_DOCBROKERS, Constants.P_DOCBROKERS, getFieldEditorParent(),wbench);
		addField(folderCommandsTableEditor);
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
		this.wbench=workbench;
	}
	
	private static class CommandTableEditor extends TableFieldEditor {
		CommandTableEditor(String key, String item, Composite parent, IWorkbench workbench) {
			super(key, Constants.preferenceTableKey,new String[] {Constants.lblHost},new int[] {150}, parent, workbench);
		}

		@Override
		protected String createList(String[][] commands) {
	           StringBuilder stringBuilder = new StringBuilder();
	            for (int i = 0; i < commands.length; i++) {
	                    if (i > 0) {
	                            stringBuilder.append(Constants.docbrokerListToken);
	                    }
	                    String[] command = commands[i];
	                    for (int j = 0; j < command.length;j++) {
	                            if (j > 0) {
	                                    stringBuilder.append(Constants.docbrokerListFieldToken);
	                            }
	                            stringBuilder.append(command[j]);
	                    }
	            }
	            return stringBuilder.toString();
		}

		@Override
		protected String[][] parseString(String commandsString) {
            if (commandsString != null && commandsString.length() > 0) {
                String[] commands = commandsString.split(Pattern.quote(Constants.docbrokerListToken));
                String[][] parsedCommands = new String[commands.length][];
                for (int i = 0; i < commands.length; i++) {
                        String command = commands[i];
                        if (command.indexOf(Constants.docbrokerListFieldToken) == -1) {
                                parsedCommands[i] = new String[] {command, "*", command};
                        } else {
                                String[] fields = command.split(Pattern.quote(Constants.docbrokerListFieldToken));
                                parsedCommands[i] = new String[fields.length];
                                for (int j = 0; j < fields.length; j++) {
                                        parsedCommands[i][j] = fields[j];
                                }
                        }
                }
                return parsedCommands;
        }
        return new String[0][0];
		}

		@Override
		protected String[] getNewInputObject() {
			return new String[] {Constants.preferenceStoreField};
		}
		
 
		
	}
}