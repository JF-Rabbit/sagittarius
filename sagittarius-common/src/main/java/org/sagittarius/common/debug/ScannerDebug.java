package org.sagittarius.common.debug;

import java.util.Scanner;

/**
 * 采用Scanner做Debug模式的操作
 *
 * @author JasonZhang 2017-11-09
 * <p>
 * IDEA-64081 TestNG tests does not allow user input (regression from IDEA 9)
 * https://youtrack.jetbrains.com/issue/IDEA-64081#tab=Comments
 */
public abstract class ScannerDebug {
    /**
     * 操作枚举
     */
    enum DebugAction {
        /**
         * 继续
         */
        C,
        /**
         * 停止
         */
        S,
    }

    /**
     * 根据操作枚举类型执行操作
     */
    protected void debugWaiting() {
        Scanner scanner = new Scanner(System.in);
        doAction(scanner);
        scanner.close();
    }

    private void doAction(Scanner scanner) {
        try {
            switch (DebugAction.valueOf(scanner.next().toUpperCase())) {
                case S:
                    beforeExit();
                    System.exit(0);
                    break;
                case C:
                    System.out.println("Continue test");
                    break;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Only support args: S(stop) or C(continue)");
            doAction(scanner);
        }
    }

    protected abstract void beforeExit();

}
