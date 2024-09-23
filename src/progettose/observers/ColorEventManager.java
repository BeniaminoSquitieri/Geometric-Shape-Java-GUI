package progettose.observers;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.paint.Color;

/**
 * Manager of the colour changing events
 *
 */
public class ColorEventManager {

    private static ColorEventManager instance;
    HashMap<String, ArrayList<ColorSubscriberInterface>> listerners = new HashMap<>();

    
    /**
    * This is a static method used to create a single instance of the ColorEventManager.
    * It takes Strings as arguments and if an instance of the ColorEventManager has not been created yet, it will create one and return it.
     * @param operations This is the first parameter that represents the events managed by this class
     * @return ColorEventManager it returns the instance of this class
    */
    public static ColorEventManager getInstance(String... operations) {
        if (instance == null) {
            instance = new ColorEventManager(operations);
        }
        return instance;
    }

     /**
     * This method is the constructor of the ColorEventManager.
     * This constructor initialize the hashmap of listeners adding the event and the list of subscriber at that event
     * 
     * @param operations This is the first parameter that represents the events managed by this class
     */
    private ColorEventManager(String... operations) {
        for (String op : operations) {
            this.listerners.put(op, new ArrayList<>());
        }
    }

     /**
     * This method is user to update the list of listener of a particular event adding a new subscriber
     * 
     * @param eventType This is the first parameter representing the event at which the listener want to subscribe
     * @param listener This is the second parameter representing the listener that wants to be notified on the update of a particular event
     */
    public void subscribe(String eventType, ColorSubscriberInterface listener) {
        ArrayList<ColorSubscriberInterface> users = this.listerners.get(eventType);
        users.add(listener);
    }

     /**
     * This method is user to update the list of listener of a particular event removing a subscriber
     * 
     * @param eventType This is the first parameter representing the event at which the listener want to unsubscribe
     * @param listener This is the second parameter representing the listener that wants to be no more notified on the update of a particular event
     */
    public void unsubscribe(String eventType, ColorSubscriberInterface listener) {
        ArrayList<ColorSubscriberInterface> users = this.listerners.get(eventType);
        users.remove(listener);
    }

     /**
     * This method is user to notify the list of listener of an update of an event specified.
     * 
     * @param eventType This is the first parameter representing the event updated
     * @param color This is the second parameter representing the real update
     */
    public void notify(String eventType, Color color) {
        ArrayList<ColorSubscriberInterface> users = this.listerners.get(eventType);
        for (ColorSubscriberInterface listener : users) {
            listener.update(eventType, color);
        }
    }
}
