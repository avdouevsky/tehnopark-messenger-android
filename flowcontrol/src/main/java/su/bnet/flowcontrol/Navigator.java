package su.bnet.flowcontrol;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by andrey on 25.11.2016.
 *
 * Класс, управляющий переходами
 *
 * Для созддания нужно 2 типа
 * 1 тип Перечисление экранов/состояний
 * 2 тип Объект, отвечающий за событие
 */
public class Navigator<T extends Enum<?>, E extends Command>{
    private static final String TAG = Navigator.class.toString();
    protected Router<T, E> router;
    private Deque<E> commands = new ArrayDeque<>();

    public Navigator(Router<T, E> router) {
        this.router = router;
    }

    public void forwardTo(T state, Bundle bundle){
        Log.v(TAG, "forwardTo " + state);
        E command = router.get(state);
        if(command == null) return;

        if(commands.size() != 0){
            commands.getLast().rollback();
        }
        commands.add(command);
        commands.getLast().forward(null);
        Log.v(TAG, "stack: " + commands.size());
    }

    public void forwardTo(T state){
        forwardTo(state, null);
    }

    public boolean back(){
        Log.v(TAG, "back");
        if(commands.size() == 0)
            return false;
        commands.removeLast().rollback();
        if(commands.size() == 0)
            return false;
        commands.getLast().forward(null);
        Log.v(TAG, "stack: " + commands.size());
        return true;
    }

    public void replaceTo(T state, Bundle bundle){
        Log.v(TAG, "replaceTo " + state);
        back();
        forwardTo(state, bundle);
    }

    public void replaceTo(T state){
        replaceTo(state, null);
    }

    public void backTo(T state){
        Log.v(TAG, "backTo " + state);
        E command = router.get(state);
        if(command == null) return;

        boolean have = false;
        for(Command c : commands){
            have = command.hashCode() == c.hashCode();
            if(have) break;
        }
        if(!have) return;
        for (int i = commands.size() - 1; i >=0 ; i--) {
            if(commands.getLast().hashCode() == command.hashCode()){
                E c = commands.getLast();
                back();
                c.forward(null);
                commands.add(c);
            }else{
                commands.removeLast().rollback();
            }
        }
        Log.v(TAG, "stack: " + commands.size());
    }

    @Nullable
    public T getCurrentState(){
        if(commands.isEmpty()) return null;
        return router.getStateByCommand(commands.getLast());
    }
}
