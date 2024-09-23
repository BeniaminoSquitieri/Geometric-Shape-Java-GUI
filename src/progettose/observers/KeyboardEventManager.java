package progettose.observers;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Manager of the keyboard pressed changing events
 *
 */
public class KeyboardEventManager {

    private static KeyboardEventManager instance;
    HashMap<String, ArrayList<KeyboardSubscriberInterface>> listerners = new HashMap<>();

    /**
    * This is a static method used to create a single instance of the KeyboardEventManager.
    * It takes Strings as arguments and if an instance of the KeyboardEventManager has not been created yet, it will create one and return it.
     * @param operations This is the first parameter that represents the events managed by this class
     * @return KeyboardEventManager it returns the instance of this class
    */
    public static KeyboardEventManager getInstance(String... operations) {
        if (instance == null) {
            instance = new KeyboardEventManager(operations);
        }
        return instance;
    }

    /**
     * This method is the constructor of the KeyboardEventManager.
     * This constructor initialize the hashmap of listeners adding the event and the list of subscriber at that event
     * 
     * @param operations This is the first parameter that represents the events managed by this class
     */
    private KeyboardEventManager(String... operations) {
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
    public void subscribe(String eventType, KeyboardSubscriberInterface listener) {
        ArrayList<KeyboardSubscriberInterface> users = this.listerners.get(eventType);
        users.add(listener);
    }

    /**
     * This method is user to update the list of listener of a particular event removing a subscriber
     * 
     * @param eventType This is the first parameter representing the event at which the listener want to unsubscribe
     * @param listener This is the second parameter representing the listener that wants to be no more notified on the update of a particular event
     */
    public void unsubscribe(String eventType, KeyboardSubscriberInterface listener) {
        ArrayList<KeyboardSubscriberInterface> users = this.listerners.get(eventType);
        users.remove(listener);
    }

         
    /**
     * This method is user to notify the list of listener of an update of an event specified.
     * 
     * @param eventType This is the first parameter representing the event updated
     * @param color This is the second parameter representing the real update
     */
    
    public void notify(String eventType) {
        ArrayList<KeyboardSubscriberInterface> users = this.listerners.get(eventType);
        for (KeyboardSubscriberInterface listener : users) {
            listener.update(eventType);
        }
    }
}
