package de.markiewb.netbeans.plugin.savewithsaveactions;

import de.markiewb.netbeans.plugin.common.action.ActionUtils;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import org.netbeans.modules.options.keymap.ActionResult;

/**
 * Action which will execute the given {@link Action}s in sequential order.
 *
 * @author markiewb
 */
class MultiAction implements Runnable {

    public static final Logger LOGGER = Logger.getLogger(MultiAction.class.getName());
    private final List<Action> actions;

    public MultiAction(List<Action> actions) {
        this.actions = actions;
    }

    @Override
    public void run() {
        LOGGER.log(Level.INFO, "execute {0} actions", actions.size());
        for (Action action : actions) {

            LOGGER.log(Level.INFO, "execute action: {0}", ActionUtils.getActionName(action));
            if (null != action) {
                new ActionResult(action).run();
            } else {
                LOGGER.warning("action is null");
            }
        }
    }
}
