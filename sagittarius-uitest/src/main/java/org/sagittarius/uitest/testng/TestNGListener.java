package org.sagittarius.uitest.testng;

import org.testng.IClassListener;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestClass;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestNGListener implements ITestListener, IClassListener, IInvokedMethodListener {

	@Override
	public void onStart(ITestContext context) {

	}

	@Override
	public void onBeforeClass(ITestClass testClass) {
	}

	@Override
	public void onTestStart(ITestResult result) {
	}

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
	}

	@Override
	public void onTestSuccess(ITestResult result) {
	}

	@Override
	public void onTestFailure(ITestResult result) {

	}

	@Override
	public void onTestSkipped(ITestResult result) {

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	@Override
	public void onAfterClass(ITestClass testClass) {
	}

	@Override
	public void onFinish(ITestContext context) {
	}

}
