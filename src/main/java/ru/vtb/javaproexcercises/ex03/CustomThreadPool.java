package ru.vtb.javaproexcercises.ex03;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomThreadPool {

    private final Queue<Runnable> taskQueue;
    private final WorkerThread[] workers;
    private final AtomicBoolean isShutdown;
    private final Object lock;
    private int activeTasks;
    private boolean terminated;

    public CustomThreadPool(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }

        this.taskQueue = new LinkedList<>();
        this.workers = new WorkerThread[capacity];
        this.isShutdown = new AtomicBoolean(false);
        this.lock = new Object();
        this.activeTasks = 0;
        this.terminated = false;

        for (int i = 0; i < capacity; i++) {
            String workerName = "Worker-" + i;

            Runnable onTaskComplete = () -> {
                synchronized (lock) {
                    activeTasks--;
                    if (isShutdown.get() && activeTasks == 0 && taskQueue.isEmpty()) {
                        checkAndUpdateTerminated();
                        lock.notifyAll();
                    }
                }
            };

            workers[i] = new WorkerThread(workerName, taskQueue, lock, onTaskComplete);
            workers[i].start();
        }
    }

    public void execute(Runnable task) {
        if (task == null) {
            throw new NullPointerException();
        }

        synchronized (lock) {
            if (isShutdown.get()) {
                throw new IllegalStateException();
            }

            taskQueue.offer(task);
            activeTasks++;
            lock.notify();
        }
    }

    public void shutdown() {
        if (isShutdown.compareAndSet(false, true)) {
            for (WorkerThread worker : workers) {
                if (worker != null) {
                    worker.interrupt();
                }
            }

            synchronized (lock) {
                checkAndUpdateTerminated();
                lock.notifyAll();
            }
        }
    }

    public void awaitTermination() throws InterruptedException {
        synchronized (lock) {
            while (!terminated) {
                lock.wait();
            }
        }
    }

    public boolean awaitTermination(long timeoutMills) throws InterruptedException {
        long deadline = System.currentTimeMillis() + timeoutMills;

        synchronized (lock) {
            while (!terminated) {
                if (System.currentTimeMillis() >= deadline) {
                    return false;
                }
                lock.wait(timeoutMills);
            }
            return true;
        }
    }

    public boolean isShutdown() {
        return isShutdown.get();
    }

    public boolean isTerminated() {
        synchronized (lock) {
            return terminated;
        }
    }

    private void checkAndUpdateTerminated() {
        if (isShutdown.get() && activeTasks == 0 && taskQueue.isEmpty()) {
            terminated = true;
        }
    }

    public int getQueueSize() {
        synchronized (lock) {
            return taskQueue.size();
        }
    }

    public int getActiveTaskCount() {
        synchronized (lock) {
            return activeTasks;
        }
    }

    public int getWorkerCount() {
        return workers.length;
    }

    public boolean areAllWorkersAlive() {
        for (WorkerThread worker : workers) {
            if (worker == null || !worker.isAlive()) {
                return false;
            }
        }
        return true;
    }
}
