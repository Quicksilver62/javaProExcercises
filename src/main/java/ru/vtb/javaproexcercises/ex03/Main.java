package ru.vtb.javaproexcercises.ex03;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        CustomThreadPool pool = new CustomThreadPool(3);

        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            pool.execute(() -> {
                System.out.printf("%s: Выполняю задачу %d%n",
                        Thread.currentThread().getName(), taskId);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.printf("%s: Завершил задачу %d%n",
                        Thread.currentThread().getName(), taskId);
            });
        }

        pool.shutdown();
        pool.awaitTermination();
        System.out.println("Все задачи выполнены");
    }
}
