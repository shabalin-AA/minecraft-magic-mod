package com.minecraftmod.util;

import net.minecraft.server.MinecraftServer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Scheduler {
    private static final Map<UUID, Task> TASKS = new HashMap<>();

    public static void schedule(int delayTicks, Runnable action, String uuidProvider) {
        UUID id = UUID.nameUUIDFromBytes(uuidProvider.getBytes());
        TASKS.put(id, new Task(delayTicks, action));
    }

    public static void serverTick(MinecraftServer server) {
        TASKS.entrySet().removeIf(entry -> {
            Task task = entry.getValue();
            task.remaining--;
            if (task.remaining <= 0) {
                task.action.run();
                return true;
            }
            return false;
        });
    }

    public static class Task {
        public int remaining;
        public Runnable action;

        public Task(int delayTicks, Runnable action) {
            this.remaining = delayTicks;
            this.action = action;
        }
    }
}
