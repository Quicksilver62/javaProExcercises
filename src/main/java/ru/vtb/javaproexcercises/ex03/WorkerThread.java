package ru.vtb.javaproexcercises.ex03;

import java.util.Queue;

public class WorkerThread extends Thread {

    private final Queue<Runnable> taskQueue;
    private final Object lock;
    private final Runnable onTaskComplete;
    private volatile boolean isRunning;

    public WorkerThread(String name, Queue<Runnable> taskQueue, Object lock, Runnable onTaskComplete) {
        super(name);
        this.taskQueue = taskQueue;
        this.lock = lock;
        this.onTaskComplete = onTaskComplete;
        this.isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning) {
            Runnable task = null;

            synchronized (lock) {
                while (taskQueue.isEmpty() && isRunning) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        if (!isRunning && taskQueue.isEmpty()) {
                            return;
                        }
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                if (!isRunning && taskQueue.isEmpty()) {
                    return;
                }

                if (!taskQueue.isEmpty()) {
                    task = taskQueue.poll();
                }
            }

            if (task != null) {
                try {
                    task.run();
                } catch (Throwable t) {
                    System.err.println("Exception in task " + task);
                } finally {
                    if (onTaskComplete != null) {
                        onTaskComplete.run();
                    }
                }
            }
        }
    }

    public void stopThread() {
        isRunning = false;
        interrupt();
    }

    public boolean isRunning() {
        return isRunning;
    }
}
