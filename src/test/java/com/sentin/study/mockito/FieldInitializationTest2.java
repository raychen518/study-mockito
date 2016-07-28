package com.sentin.study.mockito;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * <pre>
 * This class demonstrates how to initialize the fields annotated with the Mockito annotations (@Mock, @Spy, @Captor and @InjectMocks),
 * using the "@RunWith(MockitoJUnitRunner.class)" annotation setting.
 * </pre>
 */
@RunWith(MockitoJUnitRunner.class)
public class FieldInitializationTest2 {

	@Mock
	private SomeObject mockedSomeObject;

	@Spy
	private SomeObject spiedSomeObject;

	@Captor
	private ArgumentCaptor<SomeObject> argumentCaptorForSomeObject;

	@InjectMocks
	private SomeObject injectedSomeObject;

	@Mock
	private SomeDependency1 someDependency1;

	@Mock
	private SomeDependency2 someDependency2;

	@Test
	public void test() {
		System.out.println("mockedSomeObject\t\t\t: " + mockedSomeObject + Commons.getHashCode(mockedSomeObject));
		System.out.println("spiedSomeObject\t\t\t\t: " + spiedSomeObject + Commons.getHashCode(spiedSomeObject));
		System.out.println("argumentCaptorForSomeObject\t\t: " + argumentCaptorForSomeObject
				+ Commons.getHashCode(argumentCaptorForSomeObject));
		System.out.println();

		System.out.println("injectedSomeObject\t\t\t: " + injectedSomeObject + Commons.getHashCode(injectedSomeObject));
		System.out.println("injectedSomeObject.getSomeDependency1()\t: " + injectedSomeObject.getSomeDependency1()
				+ Commons.getHashCode(injectedSomeObject.getSomeDependency1()));
		System.out.println("injectedSomeObject.getSomeDependency2()\t: " + injectedSomeObject.getSomeDependency2()
				+ Commons.getHashCode(injectedSomeObject.getSomeDependency2()));
		System.out.println("someDependency1\t\t\t\t: " + someDependency1 + Commons.getHashCode(someDependency1));
		System.out.println("someDependency2\t\t\t\t: " + someDependency2 + Commons.getHashCode(someDependency2));
	}

}
