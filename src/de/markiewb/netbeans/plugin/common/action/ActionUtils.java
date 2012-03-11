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
package de.markiewb.netbeans.plugin.common.action;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import org.netbeans.core.options.keymap.api.ShortcutAction;
import org.netbeans.core.options.keymap.spi.KeymapManager;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.windows.TopComponent;

/**
 * Helper function to work with {@link Action}s.
 *
 * @author markiewb
 */
public class ActionUtils {

    /**
     * Return the {@code toString}-content of the given object. This method is NPE-safe. When the object is {@code null}
     * then the fallback value will be returned.
     *
     * @param value
     * @param fallback
     * @return
     */
    private static String toStringWithDefault(Object object, String fallback) {
        if (null == object) {
            return fallback;
        }
        return object.toString();
    }

    /**
     * Gets the action from the available actions from the currently actived nodes. Code is from {@code netbeans-7.1-201112071828-src\options.keymap\src\org\netbeans\modules\options\keymap\ActionsSearchProvider.java}
     *
     * @return
     */
    private List<Action> getActionsFromNode() {
        List<Action> result = new ArrayList<Action>();

        // try also actions of activated nodes
        Node[] actNodes = TopComponent.getRegistry().getActivatedNodes();
        for (Node node : actNodes) {

            Action[] acts = node.getActions(false);
            for (Action action : acts) {
                if (action != null) {
                    result.add(action);
                }
            }
        }
        return result;
    }

    /**
     * Gets the actions from the {@link ShortcutAction}. Code is from {@code netbeans-7.1-201112071828-src\options.keymap\src\org\netbeans\modules\options\keymap\ActionsSearchProvider.java}
     *
     * @param shortcutAction
     * @return
     */
    private Action getActionDelegate(final ShortcutAction shortcutAction) {
        Class clazz = shortcutAction.getClass();
        Field f;
        try {
            f = clazz.getDeclaredField("action");
            f.setAccessible(true);
            Action action = (Action) f.get(shortcutAction);
            return action;
        } catch (Throwable thr) {
            if (thr instanceof ThreadDeath) {
                throw (ThreadDeath) thr;
            } // complain
            Logger.getLogger(getClass().getName()).log(Level.FINE, "Some problem getting action " + shortcutAction.getDisplayName(), thr);
        }
        return null;
    }

    /**
     * Get all available actions. <p>See {@link ActionUtils#getActionsFromKeyMap()} and {@link ActionUtils#getActionsFromNode()
     * }.</p>
     *
     * @return
     */
    public List<Action> getAllActions() {

        List<Action> allActions = new ArrayList<Action>();
        allActions.addAll(getActionsFromNode());
        allActions.addAll(getActionsFromKeyMap());
        return allActions;
    }

    /**
     * Gets the actions from all available {@link KeymapManager}. Code is from {@code netbeans-7.1-201112071828-src\options.keymap\src\org\netbeans\modules\options\keymap\ActionsSearchProvider.java}
     * <p>Note: This code requires an implementation dependency to {@code Keymap Options}-module (which is a ugly hack), because the API is not
     * published nor this module is a friendly module.</p>
     * 
     * @return
     */
    private List<Action> getActionsFromKeyMap() {
        List<Action> result = new ArrayList<Action>();

        for (KeymapManager m : Lookup.getDefault().lookupAll(KeymapManager.class)) {
            for (Set<ShortcutAction> actions : m.getActions().values()) {
                for (ShortcutAction sa : actions) {

                    Action action = getActionDelegate(sa);

                    if (action != null) {
                        result.add(action);
                    }

                }
            }
        }
        return result;
    }

    /**
     * Get the name of the {@link Action}. When the action is {@code null} then an empty string will be returned.
     *
     * @param action
     * @return
     */
    public static String getActionName(Action action) {
        if (null == action) {
            return "";
        }
        return toStringWithDefault(action.getValue(Action.NAME), "");
    }
}
