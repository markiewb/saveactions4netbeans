package de.markiewb.netbeans.plugin.common.action;

import de.markiewb.netbeans.plugin.formatandsavemodule.FormatAndSaveAction;
import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import org.apache.commons.lang.ObjectUtils;
import org.netbeans.core.options.keymap.api.ShortcutAction;
import org.netbeans.core.options.keymap.spi.KeymapManager;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.windows.InputOutput;
import org.openide.windows.TopComponent;

/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
/**
 *
 * @author Bender
 */
public class ActionUtils {

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

    private Action getActionDelegate(final ShortcutAction sa) {
        Class clazz = sa.getClass();
        Field f;
        try {
            f = clazz.getDeclaredField("action");
            f.setAccessible(true);
            Action action = (Action) f.get(sa);
            return action;
        } catch (Throwable thr) {
            if (thr instanceof ThreadDeath) {
                throw (ThreadDeath) thr;
            } // complain
            Logger.getLogger(getClass().getName()).log(Level.FINE, "Some problem getting action " + sa.getDisplayName(), thr);
        }
        return null;
    }

    public List<Action> getAllActions() {
        //http://netbeans-org.1045718.n5.nabble.com/Invoking-Action-programatically-td3042933.html#a3042936
        //http://netbeans-org.1045718.n5.nabble.com/invoke-action-from-code-td3026143.html
        //http://netbeans-org.1045718.n5.nabble.com/Finding-action-by-id-td5006931.html
        List<Action> allActions = new ArrayList<Action>();
        allActions.addAll(getActionsFromNode());
        allActions.addAll(getActionsFromKeyMap());
        return allActions;
    }

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

    public static String getActionName(Action o1) {
        if (null == o1) {
            return "";
        }
        return ObjectUtils.defaultIfNull(o1.getValue(Action.NAME), "").toString();
    }


}
