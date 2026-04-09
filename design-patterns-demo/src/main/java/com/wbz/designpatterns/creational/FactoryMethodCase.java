package com.wbz.designpatterns.creational;

/**
 * 工厂方法模式演示
 * 工厂方法模式定义了一个创建对象的接口，但由子类决定要实例化的类是哪一个
 * 工厂方法让类把实例化推迟到子类
 * 适用场景：无法预知需要创建的对象类型、希望由子类决定创建哪种对象等
 */
public final class FactoryMethodCase {

    private FactoryMethodCase() {
    }

    /**
     * 运行工厂方法模式示例
     * 演示不同平台对话框如何创建各自对应的按钮
     */
    public static void run() {
        Dialog windowsDialog = new WindowsDialog();
        Dialog webDialog = new WebDialog();

        System.out.println(windowsDialog.render());
        System.out.println(webDialog.render());
    }

    /**
     * 按钮接口，定义了按钮的点击行为
     */
    private interface Button {
        /**
         * 按钮点击事件
         * @return 点击后的响应信息
         */
        String click();
    }

    /**
     * 对话框抽象类，定义了创建按钮的工厂方法
     */
    private abstract static class Dialog {
        /**
         * 渲染对话框，内部使用工厂方法创建按钮
         * @return 渲染结果信息
         */
        public String render() {
            Button button = createButton();
            return "渲染结果：" + button.click();
        }

        /**
         * 工厂方法，由子类实现以创建特定类型的按钮
         * @return Button 按钮实例
         */
        protected abstract Button createButton();
    }

    /**
     * Windows对话框实现，创建Windows风格的按钮
     */
    private static final class WindowsDialog extends Dialog {
        /**
         * 实现工厂方法，创建Windows按钮
         * @return Windows按钮实例
         */
        @Override
        protected Button createButton() {
            return () -> "Windows 按钮被点击";
        }
    }

    /**
     * Web对话框实现，创建Web风格的按钮
     */
    private static final class WebDialog extends Dialog {
        /**
         * 实现工厂方法，创建Web按钮
         * @return Web按钮实例
         */
        @Override
        protected Button createButton() {
            return () -> "Web 按钮被点击";
        }
    }
}
