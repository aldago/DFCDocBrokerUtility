package es.plugins.dfcdocbrokerutility.preferences;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;

import es.plugins.dfcdocbrokerutility.utils.Constants;


public class AddDocBrokerEntryPage extends WizardPage implements Listener{
	private Text portText;
	private Text hostText;
	
	protected String getPortText(){
		return this.portText.getText();
	}
	
	protected String getHostText(){
		return this.hostText.getText();
	}
	
	public AddDocBrokerEntryPage(IWorkbench workbench, IStructuredSelection selection) {
		super(Constants.docbrokerWizardTitle);
		setTitle(Constants.docbrokerWizardTitle);
		setDescription(Constants.docborkerWizardDesc);
	}


	public void createControl(Composite parent) {
	    Composite composite = new Composite(parent, SWT.NONE);
	    
	    GridLayout gl = new GridLayout();
	    int ncol = 2;
	    gl.numColumns = ncol;
	    composite.setLayout(gl);
	    
	    new Label (composite, SWT.NONE).setText(Constants.lblHost);
	    hostText = new Text(composite, SWT.BORDER|SWT.MULTI);
	    hostText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	    hostText.setEditable(true);
	    hostText.setFocus();
	    
	    new Label (composite, SWT.NONE).setText(Constants.lblPort);
	    portText = new Text(composite, SWT.BORDER|SWT.MULTI);
	    portText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	    portText.setEditable(true);
	    
	    setControl(composite);
	    addListeners();
	}

	private void addListeners(){		
		hostText.addListener(SWT.KeyUp, this);
		portText.addListener(SWT.KeyUp, this);
		
		hostText.addListener(SWT.KeyDown, this);
		portText.addListener(SWT.KeyDown, this);
	}
	

	public void handleEvent(Event event) {
		if (event.type==SWT.KeyDown && event.keyCode==SWT.TAB){
			hostText.setText(hostText.getText().trim());
			portText.setText(portText.getText().trim());
			if (hostText.isFocusControl()){
				portText.forceFocus();
				portText.setSelection(portText.getText().length());
			}else{
				hostText.forceFocus();
				hostText.setSelection(hostText.getText().length());
			}
			event.doit=false;
		}else{
			if (event.type==SWT.KeyDown && event.keyCode==SWT.CR){
				event.doit=false;
				getWizard().performFinish();
			}
		}
		setPageComplete(isPageComplete());
		getWizard().getContainer().updateButtons();	
	}

}

