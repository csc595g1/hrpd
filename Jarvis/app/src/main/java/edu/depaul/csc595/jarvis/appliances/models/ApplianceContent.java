package edu.depaul.csc595.jarvis.appliances.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ApplianceContent {

    /**
     * An array of sample (detection) items.
     */
    public static List<Appliance> ITEMS = new ArrayList<Appliance>();
    public static List<CharSequence> ITEMS_STRING = new ArrayList<CharSequence>();


    /**
     * A map of sample (detection) items, by ID.
     */
    public static Map<String, Appliance> ITEM_MAP = new HashMap<String, Appliance>();

    static {
        // Add 3 sample items.
        addItem(new Appliance("1", "Sump Pump"));
        addItem(new Appliance("2", "Fridge"));
        addItem(new Appliance("3", "Heater"));
        addItem(new Appliance("4", "Living Room"));
        addItem(new Appliance("5", "Bedroom"));
    }

    private static void addItem(Appliance item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
        ITEMS_STRING.add(item.toCharSequence());
    }


    public static CharSequence[] getCharSequence() {
        CharSequence[] appliance_names = new CharSequence[ITEMS_STRING.size()];
        for (int i = 0; i < appliance_names.length; i++){
            appliance_names[0] = ITEMS_STRING.get(i);
        }
        return appliance_names;

    }

    /**
     * A detection item representing a piece of content.
     */
    public static class Appliance {
        public String id;
        public String name;

        public Appliance(String id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        public CharSequence toCharSequence() { return name;}




    }
}