package org.sagittarius.junit;

import org.junit.runner.JUnitCore;

public class Executer {

//	public static void main(String[] args) {
//		run(JunitDemo.class, JunitDemo2.class);
//	}

	public static void run(Class<?>... classes) {
		for (Class<?> clazz : classes) {
			JUnitCore runner = new JUnitCore();
//			ExecutionListener listener = new ExecutionListener();
//			runner.addListener(listener);
			runner.run(clazz);
//			MyResultRecorder recorder = listener.recorder;
//			System.out.println(recorder);
		}
	}
}
