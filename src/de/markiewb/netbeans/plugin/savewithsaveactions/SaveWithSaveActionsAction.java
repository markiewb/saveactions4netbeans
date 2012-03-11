/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugin.savewithsaveactions;

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
id = "de.markiewb.netbeans.plugin.savewithsaveactions.SaveWithSaveActionsAction")
@ActionRegistration(displayName = "#CTL_SaveWithSaveActionsAction")
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 1550),
    @ActionReference(path = "Shortcuts", name = "DOS-S")
})
@Messages("CTL_SaveWithSaveActionsAction=Save &with save actions")
/**
 * Executes several actions like {@code fix imports} or {@code format} before saving the files content. The actions are
 * configured in {@link Options}.
 *
 * @author markiewb
 */
public final class SaveWithSaveActionsAction implements ActionListener {

    /**
     * Searches for the {@link Action} with the given {@code searchName}.
     *
     * @param actions list of actions to search in
     * @param searchName
     * @return {@code null} if nothing found
     */
    private Action findAction(List<Action> actions, String searchName) {
        for (Action action : actions) {
            String name = ActionUtils.getActionName(action);

            if (name.equals(searchName)) {
                return action;
            }
        }
        return null;
    }

    /**
     * Lookups up the save actions and executes them in sequential order.
     */
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

    /**
     * Read enabled save actions from the options.
     *
     * @return list of save actions. The original save action will always be at the end of the list.
     */
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
