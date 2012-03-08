/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.markiewb.netbeans.plugin.formatandsavemodule;

import java.util.prefs.Preferences;
import org.openide.util.NbPreferences;

/**
 *
 * @author Bender
 */
public class Options {

    public static final String FIXIMPORTACTIONCOMMANDNAME = "fiximportaction.commandname";
    public static final String FIXIMPORTACTIONENABLED = "fiximportaction.enabled";
    public static final String FORMATACTIONCOMMANDNAME = "formataction.commandname";
    public static final String FORMATACTIONENABLED = "formataction.enabled";
    public static final String SAVEACTIONCOMMANDNAME = "saveaction.commandname";
    public boolean fiximportActionEnabled;
    public boolean formatActionEnabled;
    public String fiximportAction;
    public String formatAction;
    public String saveAction;

    
    public void load() {
        Preferences pref = NbPreferences.forModule(FormatAndSaveAction.class);
        fiximportActionEnabled = pref.getBoolean(FIXIMPORTACTIONENABLED, true);
        formatActionEnabled = pref.getBoolean(FORMATACTIONENABLED, true);
        fiximportAction = pref.get(FIXIMPORTACTIONCOMMANDNAME, "fix-imports");
        formatAction = pref.get(FORMATACTIONCOMMANDNAME, "format");
        saveAction = pref.get(SAVEACTIONCOMMANDNAME, "&Save");
    }

    public void save() {
        Preferences pref = NbPreferences.forModule(FormatAndSaveAction.class);
        pref.putBoolean(FIXIMPORTACTIONENABLED, fiximportActionEnabled);
        pref.putBoolean(FORMATACTIONENABLED, formatActionEnabled);
        pref.put(FIXIMPORTACTIONCOMMANDNAME, fiximportAction);
        pref.put(FORMATACTIONCOMMANDNAME, formatAction);
        pref.put(SAVEACTIONCOMMANDNAME, saveAction);
    }

    void reset() {
        fiximportActionEnabled = true;
        formatActionEnabled = true;
        fiximportAction = "fix-imports";
        formatAction = "format";
        saveAction = "&Save";
    }
}
