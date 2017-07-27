package org.sagittarius.jmeter.demo;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

public class JemterDemo extends AbstractJavaSamplerClient{

	@Override
	public Arguments getDefaultParameters() {
		// TODO Auto-generated method stub
		return super.getDefaultParameters();
	}

	@Override
	public void setupTest(JavaSamplerContext context) {
		// TODO Auto-generated method stub
		super.setupTest(context);
	}

	@Override
	public void teardownTest(JavaSamplerContext context) {
		// TODO Auto-generated method stub
		super.teardownTest(context);
	}

	@Override
	public SampleResult runTest(JavaSamplerContext arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
