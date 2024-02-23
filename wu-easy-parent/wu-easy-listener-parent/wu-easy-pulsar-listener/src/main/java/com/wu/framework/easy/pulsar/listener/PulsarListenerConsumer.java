//package com.wu.framework.easy.pulsar.listener;
//
//import com.wu.framework.easy.listener.core.ListenerConsumer;
//
//
//public class PulsarListenerConsumer implements ListenerConsumer {
//
//
//
//    /**
//     * Return whether the Runnable's operation is long-lived
//     * ({@code true}) versus short-lived ({@code false}).
//     * <p>In the former case, the task will not allocate a thread from the thread
//     * pool (if any) but rather be considered as long-running background thread.
//     * <p>This should be considered a hint. Of course TaskExecutor implementations
//     * are free to ignore this flag and the SchedulingAwareRunnable interface overall.
//     */
//    @Override
//    public boolean isLongLived() {
//        return false;
//    }
//
//    /**
//     * When an object implementing interface <code>Runnable</code> is used
//     * to create a thread, starting the thread causes the object's
//     * <code>run</code> method to be called in that separately executing
//     * thread.
//     * <p>
//     * The general contract of the method <code>run</code> is that it may
//     * take any action whatsoever.
//     *
//     * @see Thread#run()
//     */
//    @Override
//    public void run() {
//
//        // 数据拉取
//
//    }
//}
