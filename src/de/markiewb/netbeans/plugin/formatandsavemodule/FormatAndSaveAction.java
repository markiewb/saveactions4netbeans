/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugin.formatandsavemodule;

import de.markiewb.netbeans.plugin.common.action.ActionUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

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
