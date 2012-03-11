package de.markiewb.netbeans.plugin.common.action;

import java.util.Comparator;
import javax.swing.Action;

/**
 * Comparator which compares {@link Action} by name.
 *
 * @author markiewb
 */
public class ActionComparator implements Comparator<Action> {

    @Override
    public int compare(Action o1, Action o2) {
        if (null == o1 && null == o2) {
            return 0;
        }
        if (null != o1 && null == o2) {
            return +1;
        }
        if (null == o1 && null != o2) {
            return -1;
        }

        String a = ActionUtils.getActionName(o1);
        String b = ActionUtils.getActionName(o2);
        return a.compareToIgnoreCase(b);
    }
}
