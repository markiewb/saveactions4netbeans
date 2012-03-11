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
