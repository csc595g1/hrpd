package edu.depaul.csc595.jarvis.detection.classes;

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
public class DetectionContent {

    /**
     * An array of sample (detection) items.
     */
    public static List<Detection> ITEMS = new ArrayList<Detection>();

    /**
     * A map of sample (detection) items, by ID.
     */
    public static Map<String, Detection> ITEM_MAP = new HashMap<String, Detection>();

    static {
        // Add 3 sample items.
        addItem(new Detection("1", "Water Leak", "JAN 3", "WATER"));
        addItem(new Detection("2", "Fire Sensed", "JAN 4", "FIRE"));
        addItem(new Detection("3", "Flood Warning", "FEB 10", "FLOOD"));
    }

    private static void addItem(Detection item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A detection item representing a piece of content.
     */
    public static class Detection {
        public String id;
        public String notification;
        public String date_occurred;
        public String category;


        public Detection(String id, String notification, String date, String category) {
            this.id = id;
            this.notification = notification;
            this.date_occurred = date;
            this.category = category;
        }

        @Override
        public String toString() {
            return notification;
        }
    }
}