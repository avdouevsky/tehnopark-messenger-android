package su.bnet.flowcontrol;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrey on 25.11.2016.
 */

public class Router<T extends Enum<?>, E extends Command>{
    private Map<T, E> commandMap = new HashMap<>();
    private Map<E, T> reverseMap = new HashMap<>();

    public void add(T key, E command){
        reverseMap.put(command, key);
        commandMap.put(key, command);
    }

    public E get(T state) {
        return commandMap.get(state);
    }

    public T getStateByCommand(E command) {
        return reverseMap.get(command);
    }
}
