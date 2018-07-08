package de.paluno.game;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

public class EventManager {
    public enum Type {
        WormTookDamage, WormEquipWeapon, WormInfected, WormJumped,
        WormMovement, WormDied, WeaponShoot, ProjectileExploded, GameOver,
        PlayerDefeated, Replay, ReplayEnded
    }

    public interface Listener {
        void handleEvent(Type eventType, Object data);
    }

    private class Data {
        private Type type;
        private Object data;
    }

    private ArrayDeque<Data>[] queues;
    private HashMap<Type, ArrayList<Listener>> listenerMap;
    private int activeQueue;

    private EventManager() {
        queues = new ArrayDeque[2];

        for (int i = 0; i < 2; i++)
            queues[i] = new ArrayDeque<>();

        listenerMap = new HashMap<>();

        activeQueue = 0;
    }

    public void addListener(Listener l, Type... eventTypes) {
        if (eventTypes.length <= 0)
            throw new IllegalArgumentException("At least one event type must be given");

        for (Type eventType : eventTypes) {
            listenerMap.computeIfAbsent(eventType, (e) -> new ArrayList<>(1)).add(l);
        }
    }

    public void removeListener(Listener l, Type... eventTypes) {
        if (eventTypes.length <= 0)
            throw new IllegalArgumentException("At least one event type must be given");

        for (Type eventType : eventTypes) {
            ArrayList<Listener> listeners = listenerMap.get(eventType);

            if (listeners != null)
                listeners.remove(l);
        }
    }

    public void processEvents() {
        int processingQueue = activeQueue;
        activeQueue = (activeQueue + 1) % 2;

        Data eventData;
        while ((eventData = queues[processingQueue].pollFirst()) != null) {
            triggerEvent(eventData.type, eventData.data);
        }
    }

    public void triggerEvent(Type type, Object data) {
        ArrayList<Listener> listeners = listenerMap.get(type);

        if (listeners != null) {
            for (Listener l : listeners) {
                l.handleEvent(type, data);
            }
        }
    }

    public void queueEvent(Type type, Object data) {
        System.out.println("Queueing event with type: " + type.name());

        Data eventData = new Data();
        eventData.type = type;
        eventData.data = data;
        queues[activeQueue].addLast(eventData);
    }

    private static final EventManager singleton = new EventManager();

    public static EventManager getInstance() {
        return singleton;
    }
}
