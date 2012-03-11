package de.markiewb.netbeans.plugin.savewithsaveactions;

import java.util.prefs.Preferences;
import org.openide.util.NbPreferences;

/**
 * Simple POJO for loading/saving options. Note: No setter/getter were introduced for the ease of usage. It is a plain
 * data holder.
 *
 * @author markiewb
 */
public class Options {

    public static final String FIXIMPORTACTIONCOMMANDNAME = "fiximportaction.commandname";
    public static final String FIXIMPORTACTIONCOMMANDNAME_DEFAULT = "fix-imports";
    public static final String FIXIMPORTACTIONENABLED = "fiximportaction.enabled";
    public static final boolean FIXIMPORTACTIONENABLED_DEFAULT = true;
    public static final String FORMATACTIONCOMMANDNAME = "formataction.commandname";
    public static final String FORMATACTIONCOMMANDNAME_DEFAULT = "format";
    public static final String FORMATACTIONENABLED = "formataction.enabled";
    public static final boolean FORMATACTIONENABLED_DEFAULT = true;
    public static final String SAVEACTIONCOMMANDNAME = "saveaction.commandname";
    public static final String SAVEACTIONCOMMANDNAME_DEFAULT = "&Save";
    public boolean fiximportActionEnabled;
    public boolean formatActionEnabled;
    public String fiximportAction;
    public String formatAction;
    public String saveAction;

    public void load() {
        Preferences pref = NbPreferences.forModule(SaveWithSaveActionsAction.class);
        fiximportActionEnabled = pref.getBoolean(FIXIMPORTACTIONENABLED, FIXIMPORTACTIONENABLED_DEFAULT);
        formatActionEnabled = pref.getBoolean(FORMATACTIONENABLED, FORMATACTIONENABLED_DEFAULT);
        fiximportAction = pref.get(FIXIMPORTACTIONCOMMANDNAME, FIXIMPORTACTIONCOMMANDNAME_DEFAULT);
        formatAction = pref.get(FORMATACTIONCOMMANDNAME, FORMATACTIONCOMMANDNAME_DEFAULT);
        saveAction = pref.get(SAVEACTIONCOMMANDNAME, SAVEACTIONCOMMANDNAME_DEFAULT);
    }

    public void save() {
        Preferences pref = NbPreferences.forModule(SaveWithSaveActionsAction.class);
        pref.putBoolean(FIXIMPORTACTIONENABLED, fiximportActionEnabled);
        pref.putBoolean(FORMATACTIONENABLED, formatActionEnabled);
        pref.put(FIXIMPORTACTIONCOMMANDNAME, fiximportAction);
        pref.put(FORMATACTIONCOMMANDNAME, formatAction);
        pref.put(SAVEACTIONCOMMANDNAME, saveAction);
    }

    void reset() {
        fiximportActionEnabled = FIXIMPORTACTIONENABLED_DEFAULT;
        formatActionEnabled = FORMATACTIONENABLED_DEFAULT;
        fiximportAction = FIXIMPORTACTIONCOMMANDNAME_DEFAULT;
        formatAction = FORMATACTIONCOMMANDNAME_DEFAULT;
        saveAction = SAVEACTIONCOMMANDNAME_DEFAULT;
    }
}
