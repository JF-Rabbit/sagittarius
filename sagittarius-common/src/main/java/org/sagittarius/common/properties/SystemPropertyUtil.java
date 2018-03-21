package org.sagittarius.common.properties;

/**
 * java的System.getProperty()方法可以获取的值
 * java.version Java 运行时环境版本
 * java.vendor Java 运行时环境供应商
 * java.vendor.url Java 供应商的 URL
 * java.home Java 安装目录
 * java.vm.specification.version Java 虚拟机规范版本
 * java.vm.specification.vendor Java 虚拟机规范供应商
 * java.vm.specification.name Java 虚拟机规范名称
 * java.vm.version Java 虚拟机实现版本
 * java.vm.vendor Java 虚拟机实现供应商
 * java.vm.name Java 虚拟机实现名称
 * java.specification.version Java 运行时环境规范版本
 * java.specification.vendor Java 运行时环境规范供应商
 * java.specification.name Java 运行时环境规范名称
 * java.class.version Java 类格式版本号
 * java.class.path Java 类路径
 * java.library.path 加载库时搜索的路径列表
 * java.io.tmpdir 默认的临时文件路径
 * java.compiler 要使用的 JIT 编译器的名称
 * java.ext.dirs 一个或多个扩展目录的路径
 * os.name 操作系统的名称
 * os.arch 操作系统的架构
 * os.version 操作系统的版本
 * file.separator 文件分隔符(在 UNIX系统中是"/") path.separator 路径分隔符(在 UNIX 系统中是":")
 * line.separator 行分隔符(在 UNIX系统中是"/n")
 * user.name 用户的账户名称
 * user.home 用户的主目录
 * user.dir 用户的当前工作目录
 *
 * @author JasonZhang 2017年7月12日 下午6:05:30
 */
public class SystemPropertyUtil {

    public enum SystemPropertyEnum {
        /**
         * 用户的账户名称
         */
        USER_NAME("user.name") {
            public String getProperty() {
                return USER_NAME.getProperty(USER_NAME);
            }
        },
        /**
         * 用户的主目录
         */
        USER_HOME("user.home") {
            public String getProperty() {
                return USER_HOME.getProperty(USER_HOME);
            }
        },
        /**
         * 用户的当前工作目录
         */
        USER_DIR("user.dir") {
            public String getProperty() {
                return USER_DIR.getProperty(USER_DIR);
            }
        },
        /**
         * Java 运行时环境版本
         */
        JAVA_VERSION("java.version") {
            public String getProperty() {
                return JAVA_VERSION.getProperty(JAVA_VERSION);
            }
        },
        /**
         * Java 安装目录
         */
        JAVA_HOME("java.home") {
            public String getProperty() {
                return JAVA_HOME.getProperty(JAVA_HOME);
            }
        },
        /**
         * 操作系统的名称
         */
        OS_NAME("os.name") {
            public String getProperty() {
                return OS_NAME.getProperty(OS_NAME);
            }
        },
        /**
         * 操作系统的架构
         */
        OS_ARCH("os.arch") {
            public String getProperty() {
                return OS_ARCH.getProperty(OS_ARCH);
            }
        },
        /**
         * 操作系统的版本
         */
        OS_VERSION("os.version") {
            public String getProperty() {
                return OS_VERSION.getProperty(OS_VERSION);
            }
        },
        /**
         * 文件分隔符(在 UNIX系统中是"/") path.separator 路径分隔符(在 UNIX 系统中是":")
         */
        FILE_SEPARATOR("file.separator") {
            public String getProperty() {
                return FILE_SEPARATOR.getProperty(FILE_SEPARATOR);
            }
        },
        /**
         * 行分隔符(在 UNIX系统中是"/n")
         */
        LINE_SEPARATOR("line.separator") {
            public String getProperty() {
                return LINE_SEPARATOR.getProperty(LINE_SEPARATOR);
            }
        };

        String systemProperty;

        private String getSystemPropertyy() {
            return systemProperty;
        }

        SystemPropertyEnum(String systemProperty) {
            this.systemProperty = systemProperty;
        }

        private String getProperty(SystemPropertyEnum property) {
            return System.getProperty(property.getSystemPropertyy());
        }

        public abstract String getProperty();
    }

    public static String getAllInfo() {
        StringBuilder builder = new StringBuilder();
        for (SystemPropertyEnum spEnum : SystemPropertyEnum.values()) {
            builder.append(spEnum + "\t\t" + spEnum.getProperty() + "\n");
        }
        return builder.toString();
    }
}
