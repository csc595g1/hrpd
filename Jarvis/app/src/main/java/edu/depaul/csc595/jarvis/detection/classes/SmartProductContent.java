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
public class SmartProductContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<SmartProduct> ITEMS = new ArrayList<SmartProduct>();
    public static List<String> ITEMS_STRING = new ArrayList<String>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, SmartProduct> ITEM_MAP = new HashMap<String, SmartProduct>();

    static {
        // Add 3 sample items.
        addItem(new SmartProduct("1", "d00001", "Water", "Sump Pump"));
        addItem(new SmartProduct("2", "d00002", "Fire", "Living Room"));
    }

    private static void addItem(SmartProduct item) {
        ITEMS.add(item);
        ITEMS_STRING.add(item.toString());
        ITEM_MAP.put(item.id, item);

    }

    public static List<String> getSmartProductAsString() {
        return ITEMS_STRING;
    }

    /**
     * A dummy item representing a piece of content.
     */

    public static class SmartProductInfo {
        public int total_smart_products;

        public void setTotalSmartProducts(int total_smart_products){
            this.total_smart_products = total_smart_products;
        }

        public int getTotalSmartProducts(){
            return total_smart_products;
        }
    }

    public static class SmartProduct {
        public String id;
        public String serial_no;
        public String type_of_smart_product;
        public String appliance_name;

        public SmartProduct(String serial_no, String type_of_smart_product, String appliance_name) {
            this.serial_no = serial_no;
            this.type_of_smart_product = type_of_smart_product;
            this.appliance_name = appliance_name;
        }

        public SmartProduct(String id, String serial_no, String type_of_smart_product, String appliance_name) {
            this.id = id;
            this.serial_no = serial_no;
            this.type_of_smart_product = type_of_smart_product;
            this.appliance_name = appliance_name;
        }

        @Override
        public String toString() {
            return type_of_smart_product + " sensor";
        }
    }
}
