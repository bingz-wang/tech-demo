package com.wbz.designpatterns.behavioral;

/**
 * 模板方法模式演示
 * 模板方法模式定义了一个算法的骨架，而将一些步骤延迟到子类中
 * 模板方法使得子类可以不改变一个算法的结构即可重定义该算法的某些特定步骤
 * 适用场景：多个子类有公共行为、控制子类扩展等
 */
public final class TemplateMethodCase {

    private TemplateMethodCase() {
    }

    /**
     * 运行模板方法模式示例
     * 演示如何使用模板方法定义数据导入的通用流程
     */
    public static void run() {
        DataImporter importer = new CsvImporter();
        importer.importData();
    }

    /**
     * 数据导入抽象类，定义了导入数据的模板方法
     */
    private abstract static class DataImporter {
        /**
         * 模板方法，定义了数据导入的固定流程
         * 使用final防止子类重写，保证算法结构不被改变
         */
        public final void importData() {
            readData();
            validate();
            save();
        }

        /**
         * 抽象方法：读取数据，由子类实现具体的读取逻辑
         */
        protected abstract void readData();

        /**
         * 可选方法：数据校验，子类可以重写提供自己的校验逻辑
         * 这里提供通用的默认实现
         */
        protected void validate() {
            System.out.println("执行通用校验逻辑");
        }

        /**
         * 可选方法：保存数据，子类可以重写提供自己的保存逻辑
         * 这里提供通用的默认实现
         */
        protected void save() {
            System.out.println("保存到数据库");
        }
    }

    /**
     * CSV数据导入器，实现了特定的数据读取方式
     */
    private static final class CsvImporter extends DataImporter {
        /**
         * 实现读取CSV文件的具体逻辑
         */
        @Override
        protected void readData() {
            System.out.println("读取 CSV 文件内容");
        }
    }
}
