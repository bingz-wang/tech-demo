package com.wbz.designpatterns.creational;

/**
 * 建造者模式演示
 * 建造者模式将复杂对象的构建与其表示分离，使得同样的构建过程可以创建不同的表示
 * 适用场景：对象有多个属性且部分是可选的、需要更流畅的API等
 */
public final class BuilderCase {

    private BuilderCase() {
    }

    /**
     * 运行建造者模式示例
     * 演示如何使用Builder模式构建复杂的Course对象
     */
    public static void run() {
        Course course = new Course.Builder()
                .title("Java 并发编程")
                .author("wbz")
                .chapterCount(12)
                .freePreview(true)
                .build();

        System.out.println(course);
    }

    /**
     * 课程类，使用建造者模式进行构造
     * 具有多个属性，使用Builder使构造更清晰
     */
    private static final class Course {
        /** 课程标题 */
        private final String title;
        /** 作者 */
        private final String author;
        /** 章节数量 */
        private final int chapterCount;
        /** 是否支持免费预览 */
        private final boolean freePreview;

        /**
         * 私有构造函数，只能通过Builder构建
         * @param builder 构建器实例
         */
        private Course(Builder builder) {
            this.title = builder.title;
            this.author = builder.author;
            this.chapterCount = builder.chapterCount;
            this.freePreview = builder.freePreview;
        }

        /**
         * 返回对象的字符串表示
         * @return 课程信息的格式化字符串
         */
        @Override
        public String toString() {
            return "Course{title='%s', author='%s', chapterCount=%d, freePreview=%s}"
                    .formatted(title, author, chapterCount, freePreview);
        }

        /**
         * 建造者类，用于逐步构建Course对象
         * 提供流式API，使构建过程更直观
         */
        private static final class Builder {
            private String title;
            private String author;
            private int chapterCount;
            private boolean freePreview;

            /** 设置课程标题
             * @param title 课程标题
             * @return Builder实例，支持链式调用
             */
            public Builder title(String title) {
                this.title = title;
                return this;
            }

            /**
             * 设置作者
             * @param author 作者名
             * @return Builder实例，支持链式调用
             */
            public Builder author(String author) {
                this.author = author;
                return this;
            }

            /**
             * 设置章节数量
             * @param chapterCount 章节数量
             * @return Builder实例，支持链式调用
             */
            public Builder chapterCount(int chapterCount) {
                this.chapterCount = chapterCount;
                return this;
            }

            /**
             * 设置是否支持免费预览
             * @param freePreview 是否免费预览
             * @return Builder实例，支持链式调用
             */
            public Builder freePreview(boolean freePreview) {
                this.freePreview = freePreview;
                return this;
            }

            /**
             * 构建最终的Course对象
             * @return 构建完成的Course实例
             */
            public Course build() {
                return new Course(this);
            }
        }
    }
}
