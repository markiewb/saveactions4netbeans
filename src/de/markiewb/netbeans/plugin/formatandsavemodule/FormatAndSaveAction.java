/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugin.formatandsavemodule;

import de.markiewb.netbeans.plugin.common.action.ActionComparator;
import de.markiewb.netbeans.plugin.common.action.ActionUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.Action;
import org.netbeans.core.options.keymap.api.ShortcutAction;
import org.netbeans.core.options.keymap.spi.KeymapManager;
import org.openide.awt.*;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.NbPreferences;
import org.openide.windows.*;

@ActionID(category = "File",
id = "de.markiewb.netbeans.plugin.formatandsavemodule.FormatAndSaveAction")
@ActionRegistration(displayName = "#CTL_FormatAndSaveAction")
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 1550),
    @ActionReference(path = "Shortcuts", name = "DOS-S")
})
@Messages("CTL_FormatAndSaveAction=Save &with save actions")
public final class FormatAndSaveAction implements ActionListener {

    private Action findAction(List<Action> actions, String searchName) {
        for (Action action : actions) {
            String name = ActionUtils.getActionName(action);

            if (name.equals(searchName)) {
                return action;
            }
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<String> list = readOptions();


        List<Action> allActions = new ActionUtils().getAllActions();

        //search for the actions
        List<Action> actions = new ArrayList<Action>();
        for (String key : list) {
            actions.add(findAction(allActions, key));
        }

        //execute the actions
        new MultiAction(actions).run();
    }

    private List<String> readOptions() {
        Options options = new Options();
        options.load();
        
        List<String> list = new ArrayList<String>();
        if (options.fiximportActionEnabled) {
            list.add(options.fiximportAction);
        }
        if (options.formatActionEnabled) {
            list.add(options.formatAction);
        }
        list.add(options.saveAction);
        return list;
    }
}
