package com.wu.framework.easy.listener.core;

/**
 * describe : 单例消息侦听器容器
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2021/10/17 6:19 下午
 */
public interface SingletonMessageListenerContainer<K, V> extends GenericMessageListenerContainer<K, V> {

//    final class ListenerConsumer implements SchedulingAwareRunnable {
//
//        /**
//         * Return whether the Runnable's operation is long-lived
//         * ({@code true}) versus short-lived ({@code false}).
//         * <p>In the former case, the task will not allocate a thread from the thread
//         * pool (if any) but rather be considered as long-running background thread.
//         * <p>This should be considered a hint. Of course TaskExecutor implementations
//         * are free to ignore this flag and the SchedulingAwareRunnable interface overall.
//         */
//        @Override
//        public boolean isLongLived() {
//            return false;
//        }
//
//        /**
//         * When an object implementing interface <code>Runnable</code> is used
//         * to create a thread, starting the thread causes the object's
//         * <code>run</code> method to be called in that separately executing
//         * thread.
//         * <p>
//         * The general contract of the method <code>run</code> is that it may
//         * take any action whatsoever.
//         *
//         * @see Thread#run()
//         */
//        @Override
//        public void run() {
//
//        }
//    }
}
