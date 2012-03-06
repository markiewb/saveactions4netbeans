/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugin.showallactions;

import de.markiewb.netbeans.plugin.common.action.ActionComparator;
import de.markiewb.netbeans.plugin.common.action.ActionUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;

@ActionID(category = "Window",
id = "de.markiewb.netbeans.plugin.showallactions.ShowAllActions")
@ActionRegistration(displayName = "#CTL_ShowAllActions")
@ActionReferences({
    @ActionReference(path = "Menu/Window/Other", position = 1205)
})
@Messages("CTL_ShowAllActions=Show all actions")
public final class ShowAllActions implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        List<Action> allActions = new ActionUtils().getAllActions();

        //show all action for debugging purposes
        Collections.sort(allActions, new ActionComparator());
        InputOutput iO = IOProvider.getDefault().getIO("Show all actions", true);

        showAllActions(allActions, iO);
    }

    private void logAction(Action action, InputOutput iO) {


        Object name = null;
        if (null != action) {

            List<String> list = Arrays.asList(Action.NAME, Action.ACTION_COMMAND_KEY, Action.DEFAULT, Action.SHORT_DESCRIPTION, Action.LONG_DESCRIPTION);


            StringBuilder sb = new StringBuilder();
            for (String key : list) {

                Object value = action.getValue(key);
                sb.append(String.format("%s=%s, ", key, value));
            }

            name = sb.toString();
        }

        final String message = "action: " + name + action;
        if (null == iO) {
            Logger.getLogger(ActionUtils.class.getName()).log(Level.INFO, message);
        } else {
            iO.getOut().println(message);
        }
    }

    public void showAllActions(List<Action> allActions, InputOutput iO) {


        Collections.sort(allActions, new ActionComparator());

        for (Action action : allActions) {
            logAction(action, iO);
        }
    }
}
