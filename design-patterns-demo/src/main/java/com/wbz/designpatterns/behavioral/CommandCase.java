package com.wbz.designpatterns.behavioral;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 命令模式演示
 * 命令模式将一个请求封装为一个对象，从而使用户可以用不同的请求对客户进行参数化
 * 命令模式支持可撤销的操作、日志记录、事务等
 * 适用场景：需要将请求调用者和接收者解耦、需要支持撤销/重做操作等
 */
public final class CommandCase {

    private CommandCase() {
    }

    /**
     * 运行命令模式示例
     * 演示如何使用命令模式实现文本编辑器的操作队列
     */
    public static void run() {
        Queue<Command> commands = new ArrayDeque<>();
        TextEditor editor = new TextEditor();

        commands.offer(() -> editor.write("学习 "));
        commands.offer(() -> editor.write("设计模式"));
        commands.offer(editor::print);

        while (!commands.isEmpty()) {
            commands.poll().execute();
        }
    }

    /**
     * 命令接口，定义了执行操作的方法
     * 使用函数式接口，可以用Lambda表达式实现
     */
    @FunctionalInterface
    private interface Command {
        /**
         * 执行命令
         */
        void execute();
    }

    /**
     * 文本编辑器类，作为命令的接收者
     */
    private static final class TextEditor {
        /** 编辑器内容缓冲区 */
        private final StringBuilder content = new StringBuilder();

        /**
         * 写入文本到编辑器
         * @param text 要写入的文本
         */
        public void write(String text) {
            content.append(text);
        }

        /**
         * 打印编辑器当前内容
         */
        public void print() {
            System.out.println("编辑器内容：" + content);
        }
    }
}
