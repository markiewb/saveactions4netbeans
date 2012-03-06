/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugin.formatandsavemodule;

import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JEditorPane;
import javax.swing.text.TextAction;
import org.openide.awt.StatusDisplayer;
import org.openide.cookies.EditorCookie;
import org.openide.text.CloneableEditor;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 *
 * @author Bender
 */
class ActionResult implements Runnable {
    /**
     * UI logger to notify about invocation of an action
     */
    private static Logger UILOG = Logger.getLogger("org.netbeans.ui.actions"); // NOI18N
    // NOI18N
    private Action command;

    public ActionResult(Action command) {
        this.command = command;
    }

    private static ActionEvent createActionEvent(Action action) {
        Object evSource = null;
        int evId = ActionEvent.ACTION_PERFORMED;
        // text (editor) actions
        if (action instanceof TextAction) {
            EditorCookie ec = Utilities.actionsGlobalContext().lookup(EditorCookie.class);
            if (ec == null) {
                return null;
            }
            JEditorPane[] editorPanes = ec.getOpenedPanes();
            if (editorPanes == null || editorPanes.length <= 0) {
                return null;
            }
            evSource = editorPanes[0];
        }
        if (evSource == null) {
            evSource = TopComponent.getRegistry().getActivated();
        }
        if (evSource == null) {
            evSource = WindowManager.getDefault().getMainWindow();
        }
        return new ActionEvent(evSource, evId, null);
    }

    public void run() {
        // be careful, some actions throws assertions etc, because they
        // are not written to be invoked directly
        // be careful, some actions throws assertions etc, because they
        // are not written to be invoked directly
        try {
            Action a = command;
            ActionEvent ae = createActionEvent(command);
            Object p = ae.getSource();
            if (p instanceof CloneableEditor) {
                JEditorPane pane = ((CloneableEditor) p).getEditorPane();
                Action activeCommand = pane.getActionMap().get(command.getValue(Action.NAME));
                if (activeCommand != null) {
                    a = activeCommand;
                }
            }
            a.actionPerformed(ae);
            uiLog(true);
        } catch (Throwable thr) {
            uiLog(false);
            if (thr instanceof ThreadDeath) {
                throw (ThreadDeath) thr;
            }
            Object name = command.getValue(Action.NAME);
            String displayName = "";
            if (name instanceof String) {
                displayName = (String) name;
            }
            Logger.getLogger(getClass().getName()).log(Level.FINE, displayName + " action can not be invoked.", thr);
            StatusDisplayer.getDefault().setStatusText(NbBundle.getMessage(getClass(), "MSG_ActionFailure", displayName));
        }
    }

    private void uiLog(boolean success) {
        LogRecord rec = new LogRecord(Level.FINER, success ? "LOG_QUICKSEARCH_ACTION" : "LOG_QUICKSEARCH_ACTION_FAILED"); // NOI18N
        // NOI18N
        rec.setParameters(new Object[]{command.getClass().getName(), command.getValue(Action.NAME)});
        rec.setLoggerName(UILOG.getName());
        UILOG.log(rec);
    }
    
}
