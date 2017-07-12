package org.sagittarius.uitest.util.mobile.android;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

/**
 * 电话键
 *KEYCODE_CALL 拨号键 5
 *KEYCODE_ENDCALL 挂机键 6
 *KEYCODE_HOME 按键Home 3
 *KEYCODE_MENU 菜单键 82
 *KEYCODE_BACK 返回键 4
 *KEYCODE_SEARCH 搜索键 84
 *KEYCODE_CAMERA 拍照键 27
 *KEYCODE_FOCUS 拍照对焦键 80
 *KEYCODE_POWER 电源键 26
 *KEYCODE_NOTIFICATION 通知键 83
 *KEYCODE_MUTE 话筒静音键 91
 *KEYCODE_VOLUME_MUTE 扬声器静音键 164
 *KEYCODE_VOLUME_UP 音量增加键 24
 *KEYCODE_VOLUME_DOWN 音量减小键 25
 * 控制键
 *KEYCODE_ENTER 回车键 66
 *KEYCODE_ESCAPE ESC键 111
 *KEYCODE_DPAD_CENTER 导航键 确定键 23
 *KEYCODE_DPAD_UP 导航键 向上 19
 *KEYCODE_DPAD_DOWN 导航键 向下 20
 *KEYCODE_DPAD_LEFT 导航键 向左 21
 *KEYCODE_DPAD_RIGHT 导航键 向右 22
 *KEYCODE_MOVE_HOME 光标移动到开始键 122
 *KEYCODE_MOVE_END 光标移动到末尾键 123
 *KEYCODE_PAGE_UP 向上翻页键 92
 *KEYCODE_PAGE_DOWN 向下翻页键 93
 *KEYCODE_DEL 退格键 67
 *KEYCODE_FORWARD_DEL 删除键 112
 *KEYCODE_INSERT 插入键 124
 *KEYCODE_TAB Tab键 61
 *KEYCODE_NUM_LOCK 小键盘锁 143
 *KEYCODE_CAPS_LOCK 大写锁定键 115
 *KEYCODE_BREAK Break/Pause键 121
 *KEYCODE_SCROLL_LOCK 滚动锁定键 116
 *KEYCODE_ZOOM_IN 放大键 168
 *KEYCODE_ZOOM_OUT 缩小键 169
 * 组合键
 *KEYCODE_ALT_LEFT Alt+Left
 *KEYCODE_ALT_RIGHT Alt+Right
 *KEYCODE_CTRL_LEFT Control+Left
 *KEYCODE_CTRL_RIGHT Control+Right
 *KEYCODE_SHIFT_LEFT Shift+Left
 *KEYCODE_SHIFT_RIGHT Shift+Right
 */
/**
 * 用来按实体键的方法类
 * 
 * @author jasonzhang 2016年8月11日 上午11:27:30
 *
 */
public class KeyCode {

	public static final int KEYCODE_HOME = 3;// 按键Home
	public static final int KEYCODE_MENU = 82;// 菜单键
	public static final int KEYCODE_BACK = 4;// 返回键
	public static final int KEYCODE_CAMERA = 27;// 拍照键
	public static final int KEYCODE_POWER = 26;// 电源键
	public static final int KEYCODE_VOLUME_UP = 24;// 音量增加键
	public static final int KEYCODE_VOLUME_DOWN = 25;// 音量减小键

	public static void pressKeyCode(AndroidDriver<MobileElement> driver, int key) {
		switch (key) {
		case KEYCODE_HOME:
			driver.pressKeyCode(KEYCODE_HOME);
			break;
		case KEYCODE_MENU:
			driver.pressKeyCode(KEYCODE_MENU);
			break;
		case KEYCODE_BACK:
			driver.pressKeyCode(KEYCODE_BACK);
			break;
		case KEYCODE_CAMERA:
			driver.pressKeyCode(KEYCODE_CAMERA);
			break;
		case KEYCODE_POWER:
			driver.pressKeyCode(KEYCODE_POWER);
			break;
		case KEYCODE_VOLUME_UP:
			driver.pressKeyCode(KEYCODE_VOLUME_UP);
			break;
		case KEYCODE_VOLUME_DOWN:
			driver.pressKeyCode(KEYCODE_VOLUME_DOWN);
			break;

		default:
			break;
		}
	}
}
