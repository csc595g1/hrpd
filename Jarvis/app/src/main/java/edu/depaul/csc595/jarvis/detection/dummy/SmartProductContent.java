package edu.depaul.csc595.jarvis.detection.dummy;

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
public class SmartProductContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<SmartProduct> ITEMS = new ArrayList<SmartProduct>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, SmartProduct> ITEM_MAP = new HashMap<String, SmartProduct>();

    static {
        // Add 3 sample items.
        addItem(new SmartProduct("1", "Item 1"));
        addItem(new SmartProduct("2", "Item 2"));
        addItem(new SmartProduct("3", "Item 3"));
    }

    private static void addItem(SmartProduct item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class SmartProduct {
        public String id;
        public String serial_no;
        public String content;

        public SmartProduct(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
