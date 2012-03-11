/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugin.savewithsaveactions;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.JComponent;
import org.netbeans.spi.options.OptionsPanelController;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;

@OptionsPanelController.SubRegistration(location = "Editor",
displayName = "#AdvancedOption_DisplayName_Saveactions",
id = "de.markiewb.netbeans.plugin.savewithsaveactions.SaveactionsOptionsPanelController",
keywords = "#AdvancedOption_Keywords_Saveactions",
keywordsCategory = "Editor/de.markiewb.netbeans.plugin.savewithsaveactions.SaveactionsOptionsPanelController")
@org.openide.util.NbBundle.Messages({"AdvancedOption_DisplayName_Saveactions=Save actions", "AdvancedOption_Keywords_Saveactions=Configure the save actions"})
public final class SaveactionsOptionsPanelController extends OptionsPanelController {

    private SaveactionsPanel panel;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private boolean changed;

    public void update() {
        getPanel().load();
        changed = false;
    }

    public void applyChanges() {
        getPanel().store();
        changed = false;
    }

    public void cancel() {
        // need not do anything special, if no changes have been persisted yet
    }

    public boolean isValid() {
        return getPanel().valid();
    }

    public boolean isChanged() {
        return changed;
    }

    public HelpCtx getHelpCtx() {
        return null; // new HelpCtx("...ID") if you have a help set
    }

    public JComponent getComponent(Lookup masterLookup) {
        return getPanel();
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        pcs.removePropertyChangeListener(l);
    }

    private SaveactionsPanel getPanel() {
        if (panel == null) {
            panel = new SaveactionsPanel(this);
        }
        return panel;
    }

    void changed() {
        if (!changed) {
            changed = true;
            pcs.firePropertyChange(OptionsPanelController.PROP_CHANGED, false, true);
        }
        pcs.firePropertyChange(OptionsPanelController.PROP_VALID, null, null);
    }
}
