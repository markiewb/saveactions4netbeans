/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 * 
 * Contributor(s):
 * 
 * Portions Copyrighted 2008 Sun Microsystems, Inc.
 * Portions Copyrighted 2012 markiewb.
 */
package org.netbeans.modules.options.keymap;

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
 * Copied from "Keymap Options module". Code is from {@code netbeans-7.1-201112071828-src\options.keymap\src\org\netbeans\modules\options\keymap\ActionsSearchProvider.java},
 * but was a private class.
 *
 * @author Jan Becicka, Dafe Simonek
 */
public class ActionResult implements Runnable {

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
