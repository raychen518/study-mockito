package com.sentin.study.mockito;

import static org.junit.Assert.assertEquals;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.internal.creation.cglib.CglibMockMaker;
import org.mockito.internal.verification.api.VerificationData;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.mock.SerializableMode;
import org.mockito.plugins.MockMaker;
import org.mockito.stubbing.Answer;
import org.mockito.verification.Timeout;
import org.mockito.verification.VerificationMode;

/**
 * <pre>
 * This class introduces Mockito by demonstrating Mockito's features using test methods.
 * 
 * +++++++++++++++++
 * Contents
 * +++++++++++++++++
 * Versions
 * Overview
 * Limitations
 * Principles
 * Misc
 *     Ways to Initialize the Fields Annotated with the Mockito Annotations
 *     Avoiding Compiler Warnings of Unchecked Generic Objects
 * References
 * 
 * =================
 * Versions
 * =================
 * Library          Version
 * -------------------------------------
 * Mockito          1.10.19
 * JUnit            4.12
 * 
 * =================
 * Overview
 * =================
 * Mockito is a mocking framework for unit tests in Java.
 * 
 * =================
 * Limitations
 * =================
 * Mockito has the following limitations, which are generally technical restrictions.
 * Note: But Mockito authors believe using hacks to work around them would encourage people to write poorly testable code.
 * 
 * - The framework needs Java 1.5+.
 * - The final classes cannot be mocked.
 * - The enums cannot be mocked.
 * - The static methods cannot be mocked.
 * - The final methods cannot be mocked.
 * - The private methods cannot be mocked.
 * - The equals(...) and hashCode() methods cannot be mocked.
 * - The mocking is only possible on VMs that are supported by Objenesis.
 *   Notes:
 *   - Objenesis (http://objenesis.org/) is a Java library to instantiate a new object of a particular class, which can bypass the constructors when creating an object.
 *   - Most VMs should be OK at this limitation.
 * - The spying on real methods where real implementation references outer Class via OuterClass.this is impossible.
 *   Notes:
 *   - This is an extremely rare case.
 * 
 * Nevertheless, some of the above limitations can be mitigated using the PowerMock or JMockit libraries.
 * 
 * =================
 * Principles
 * =================
 * - Don't mock external types.
 * - Don't mock value objects.
 * - Don't mock everything.
 * - Love the tests!
 * 
 * =================
 * Misc
 * =================
 * - Ways to Initialize the Fields Annotated with the Mockito Annotations
 *   See the following 3 test classes.
 *   - FieldInitializationTest1
 *   - FieldInitializationTest2
 *   - FieldInitializationTest3
 * 
 * - Avoiding Compiler Warnings of Unchecked Generic Objects
 *   Mocking generic classes/interfaces using the Mockito.mock(...) method loses the generic information, which causes the compiler warnings of unchecked generic objects.
 *   
 *   A solution is to use the fields instead of local variables for the created mocks, and annotate those fields with the @Mock annotation,
 *   and finally invoke the MockitoAnnotations.initMocks(...) method to initialize those fields, such as follows.
 * 
 *   	<Before>
 *   	-	List<String> mockedStringList = Mockito.mock(List.class);	// The generic information is lost in the mocking.
 *   
 *   	<After>
 *   	-	@Mock
 *   	-	private List<String> mockedStringList;
 *   
 *   	-	@Before
 *   	-	public void setUp() {
 *   	-		MockitoAnnotations.initMocks(this);
 *   	-	}
 * 
 * =================
 * References
 * =================
 * - Mockito - Official Website
 *   http://mockito.org/
 * 
 * - Mockito - Latest Documentation
 *   http://mockito.github.io/mockito/docs/current/org/mockito/Mockito.html
 * 
 * - Mockito - FAQ
 *   https://github.com/mockito/mockito/wiki/FAQ
 * 
 * - Google Groups - Mockito
 *   http://groups.google.com/group/mockito
 * 
 * - DZone - Refcardz - Mockito
 *   https://dzone.com/refcardz/mockito
 * 
 * - PowerMock - Official Website
 *   https://github.com/jayway/powermock
 * 
 * - JMockit - Official Website
 *   http://jmockit.org/
 * 
 * - Wikipedia - Behavior-Driven Development
 *   https://en.wikipedia.org/wiki/Behavior-driven_development
 * </pre>
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntroductionTest {

	// +++++++++++++++++++++++++++++++++++++++++++++++++
	// List of Test Methods
	// +++++++++++++++++++++++++++++++++++++++++++++++++
	// 01. Behavior Verification
	// 02. Stubbing
	// 03. Using Argument Matchers
	// 04. Method Invocation Number Verification
	// 05. Stubbing Void Methods with Exceptions
	// 06. Verification in Order
	// 07. Verification for No Interactions
	// 08. Finding Redundant Method Invocations
	// 09. The Mock Annotation
	// 10. Stubbing Consecutive Calls
	// 11. Stubbing with Callbacks
	// 12. Using DoXxx Methods
	// 13. Spying on Real Objects
	// 14. Changing Default Return Values of Unstubbed Method Invocations
	// 15. Capturing Arguments for Assertions
	// 16. Real Partial Mocks
	// 17. Resetting Mocks
	// 18. Validating Framework Usage
	// 19. Aliases for Behavior Driven Development
	// 20. Serializable Mocks
	// 21. Annotations: @Captor, @Spy and @InjectMocks
	// 22. Verification with Timeout
	// 23. Automatic Instantiation of @Spy and @InjectMocks Annotations
	// 24. One-liner Stubs
	// 25. Verification Ignoring Stubs
	// 26. Mocking Details
	// 27. Delegating Calls to Real Instance
	// 28. MockMaker API
	// 29. BDD Style Verification
	// 30. Spying or Mocking Abstract Classes
	// 31. Serialized or Deserialized Mocks across Classloaders
	// 32. Generic Support with Deep Stubs
	// 33. Mockito JUnit Rule
	// 34. Switching on or off Plug-ins
	// 35. Custom Verification Failure Message

	private static int testMethodCounter = 0;

	@Mock
	private List<String> mockedGeneralList;

	@Captor
	private ArgumentCaptor<Collection<String>> test21CaptorArgument;

	@Spy
	private ArrayList<String> test21SpySpiedList;

	@InjectMocks
	private SomeObject test21InjectMocksSomeObject;

	@Mock
	private SomeDependency1 test21InjectMocksMockedSomeDependency1;

	@Spy
	private SomeDependency2 test21InjectMocksSpiedSomeDependency2;

	@InjectMocks
	private SomeObject1 test23InjectedSomeObject1;

	@InjectMocks
	private SomeObject2 test23InjectedSomeObject2;

	@InjectMocks
	private SomeObject3 test23InjectedSomeObject3;

	@Mock
	private SomeDependency test23MockedSomeDependency;

	@Mock
	private List<String> test51MockedList;

	@Before
	public void setUp() {
		// Initialize objects annotated with Mockito annotations - @Mock, @Spy,
		// @Captor and @InjectMocks.
		MockitoAnnotations.initMocks(this);

		Commons.printDelimiterLine(++testMethodCounter, (testMethodCounter == 1) ? false : true);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test01BehaviorVerification() {
		/**
		 * <pre>
		 * 01. Behavior Verification
		 * - Once created, a mock will remember all interactions on it, which thus can be used on the behavior verification.
		 * </pre>
		 */

		// Create a mock.
		List<Object> mockedList = Mockito.mock(List.class);

		// Use a mock.
		mockedList.add("abc");
		mockedList.clear();

		// Verify a mock ('s behavior).
		Mockito.verify(mockedList).add("abc");
		Mockito.verify(mockedList).clear();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test02Stubbing() {
		/**
		 * <pre>
		 * 02. Stubbing
		 * - By default, for all methods that return a value, a mock returns null, a primitive (wrapper) value, an empty collection, as appropriate.
		 * - Stubbing can be overridden. e.g. Common stubbing can go to fixture setup but the test methods can override it.
		 * - Once stubbed, the method will always return the stubbed value.
		 * - Last stubbing is more important, in other words, the order of stubbing matters. e.g. the 2nd stubbing for one method takes effect instead of that method's 1st stubbing.
		 * </pre>
		 */

		ArrayList<Object> mockedArrayList = Mockito.mock(ArrayList.class);

		mockedArrayList.add("001");
		mockedArrayList.add("002");
		mockedArrayList.add("003");

		// Although elements are added into the mocked array list,
		// they cannot be printed out as the normal way.
		// In this case, instead of "[001, 002, 003]", the following string is
		// printed out.
		// -------------------------------------------------
		// mockedArrayList: Mock for ArrayList, hashCode: ...
		// -------------------------------------------------
		System.out.println("mockedArrayList: " + mockedArrayList);
		System.out.println();

		// The "null" will be printed because the first 3 elements in the mocked
		// array list are not stubbed.
		System.out.println("mockedArrayList.get(0): " + mockedArrayList.get(0));
		System.out.println("mockedArrayList.get(1): " + mockedArrayList.get(1));
		System.out.println("mockedArrayList.get(2): " + mockedArrayList.get(2));
		System.out.println();

		// Do a stubbing.
		Mockito.when(mockedArrayList.get(0)).thenReturn("abc");
		Mockito.when(mockedArrayList.get(1)).thenReturn("def");
		Mockito.when(mockedArrayList.get(2)).thenReturn("ghi");

		// Show the stubbing effect.
		System.out.println("mockedArrayList.get(0): " + mockedArrayList.get(0));
		System.out.println("mockedArrayList.get(1): " + mockedArrayList.get(1));
		System.out.println("mockedArrayList.get(2): " + mockedArrayList.get(2));
		System.out.println();

		// The "null" will be printed because the 4th element in the mocked
		// array list is not stubbed.
		System.out.println("mockedArrayList.get(3): " + mockedArrayList.get(3));

		// Do a stubbing.
		String exceptionMessage = "Some exception occurred when getting the 4th element in the list.";
		// Note: The java.lang.RuntimeException class must be used here to do a
		// stubbing for an exception (instead of the java.lang.Exception class).
		Mockito.when(mockedArrayList.get(3)).thenThrow(new RuntimeException(exceptionMessage));

		// Show the stubbing effect.
		try {
			System.out.println("mockedArrayList.get(3): " + mockedArrayList.get(3));
		} catch (Exception e) {
			assertEquals(exceptionMessage, e.getMessage());
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test03UsingArgumentMatchers() {
		/**
		 * <pre>
		 * 03. Argument Matchers
		 * - The argument matchers can be used in the stubbing.
		 * - The argument matchers can be used in the behavior verification.
		 * - The custom argument matchers can be created and used.
		 * - If an argument matcher is used for a parameter in a method invocation,
		 *   all other parameters in that method invocation have to be provided by argument matchers at the same time.
		 * </pre>
		 */

		List<Object> mockedList = Mockito.mock(List.class);

		{
			System.out.println("mockedList.get(0): " + mockedList.get(0));
			System.out.println("mockedList.get(1): " + mockedList.get(1));
			System.out.println("mockedList.get(2): " + mockedList.get(2));
			System.out.println();

			// Do a stubbing using an argument matcher (anyInt()).
			Mockito.when(mockedList.get(Mockito.anyInt())).thenReturn("xxx");

			System.out.println("mockedList.get(0): " + mockedList.get(0));
			System.out.println("mockedList.get(1): " + mockedList.get(1));
			System.out.println("mockedList.get(2): " + mockedList.get(2));
			System.out.println();

			// Verify the mock using an argument matcher (anyInt()).
			Mockito.verify(mockedList, Mockito.times(6)).get(Mockito.anyInt());
		}

		{
			System.out.println("mockedList.subList(3, 6): " + mockedList.subList(3, 6));
			System.out.println("mockedList.subList(3, 9): " + mockedList.subList(3, 9));
			System.out.println();

			// Do a stubbing using argument matchers (anyInt() and eq(...)).
			Mockito.when(mockedList.subList(Mockito.anyInt(), Mockito.eq(6)))
					.thenReturn(Arrays.asList("abc", "def", "ghi"));

			System.out.println("mockedList.subList(3, 6): " + mockedList.subList(3, 6));
			System.out.println("mockedList.subList(3, 9): " + mockedList.subList(3, 9));
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test04MethodInvocationNumberVerification() {
		/**
		 * <pre>
		 * 04. Method Invocation Number Verification
		 * - The Mockito.times(...) method can be used to verify the number of the specified method invocation.
		 * - The Mockito.times(1) method is the default setting thus can be omitted in the verification statement.
		 * - The Mockito.times(0) method has an alias - Mockito.never().
		 * - The methods like Mockito.atLeast(...), Mockito.atMost(...) and Mockito.atLeastOnce() can be used for the range verification.
		 * </pre>
		 */

		List<Object> mockedList = Mockito.mock(List.class);

		mockedList.add("item001");

		mockedList.add("item002");
		mockedList.add("item002");

		mockedList.add("item003");
		mockedList.add("item003");
		mockedList.add("item003");

		Mockito.verify(mockedList).add("item001");
		Mockito.verify(mockedList, Mockito.times(1)).add("item001");

		Mockito.verify(mockedList, Mockito.times(2)).add("item002");

		Mockito.verify(mockedList, Mockito.times(3)).add("item003");

		Mockito.verify(mockedList, Mockito.times(0)).add("item004");
		Mockito.verify(mockedList, Mockito.never()).add("item004");

		Mockito.verify(mockedList, Mockito.atLeast(2)).add("item002");
		Mockito.verify(mockedList, Mockito.atMost(3)).add("item003");
		Mockito.verify(mockedList, Mockito.atLeastOnce()).add("item001");
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test05StubbingVoidMethodsWithExceptions() {
		/**
		 * <pre>
		 * 05. Stubbing Void Methods with Exceptions
		 * - The statement "Mockito.when(xxx.xxx(...)).thenThrow(new RuntimeException(...))" cannot be used to stub void methods 
		 *   because the Mockito.when(...) method needs a parameter.
		 * - Instead, the statement "Mockito.doThrow(new RuntimeException(...)).when(xxx).xxx()" should be used.
		 * - Initially, the Mockito.stubVoid(...) method was used for stubbing voids.
		 *   Currently, the Mockito.stubVoid(...) method is deprecated in favor of the Mockito.doThrow(...) method.
		 * </pre>
		 */

		List<Object> mockedList = Mockito.mock(List.class);

		mockedList.clear();

		String exceptionMessage = "Some exception occurred when clearing the list.";
		Mockito.doThrow(new RuntimeException(exceptionMessage)).when(mockedList).clear();

		try {
			mockedList.clear();
		} catch (Exception e) {
			assertEquals(exceptionMessage, e.getMessage());
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test06VerificationInOrder() {
		/**
		 * <pre>
		 * 06. Verification in Order
		 * - The InOrder class can be used to perform the verification in order.
		 * - Verification in order is flexible here.
		 *   - There is no need to verify all interactions, only interested interactions can be verified.
		 *   - There is no need to pass all objects to create the InOrder object. Only verification relevant objects should be passed.
		 * </pre>
		 */

		// =================================================
		// Verification without Order
		// =================================================
		{
			List<Object> mockedList = Mockito.mock(List.class);

			mockedList.add("value1");
			mockedList.add("value2");

			Mockito.verify(mockedList).add("value2");
			Mockito.verify(mockedList).add("value1");
		}

		// =================================================
		// Verification in Order
		// =================================================
		{
			List<Object> mockedList = Mockito.mock(List.class);

			mockedList.add("value1");
			mockedList.add("value2");

			InOrder inOrder = Mockito.inOrder(mockedList);

			inOrder.verify(mockedList).add("value1");
			inOrder.verify(mockedList).add("value2");
		}

		// =================================================
		// Verification in Order on Multiple Objects
		// =================================================
		{
			List<Object> mockedList1 = Mockito.mock(List.class);
			List<Object> mockedList2 = Mockito.mock(List.class);

			mockedList1.add("value1");
			mockedList2.add("value2");

			InOrder inOrder = Mockito.inOrder(mockedList1, mockedList2);

			inOrder.verify(mockedList1).add("value1");
			inOrder.verify(mockedList2).add("value2");
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test07VerificationForNoInteractions() {
		/**
		 * <pre>
		 * 07. Verification for No Interactions
		 * - The Mockito.verifyZeroInteractions(...) can be used to verify there are no interactions happened on the specified mocks.
		 * </pre>
		 */

		List<Object> mockedList1 = Mockito.mock(List.class);
		List<Object> mockedList2 = Mockito.mock(List.class);
		List<Object> mockedList3 = Mockito.mock(List.class);
		List<Object> mockedList4 = Mockito.mock(List.class);

		mockedList1.add("value1");

		// Verify there is no interactions happened on the specified mocks.
		Mockito.verifyZeroInteractions(mockedList2, mockedList3, mockedList4);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test08FindingRedundantMethodInvocations() {
		/**
		 * <pre>
		 * 08. Finding Redundant Method Invocations
		 * - The Mockito.verifyNoMoreInteractions(...) can be used to verify there are no more interactions happened on the specified mocks from now on.
		 * - This method is not recommended to use unless the testing scenario is about the interaction testing toolkit.
		 * </pre>
		 */

		List<Object> mockedList = Mockito.mock(List.class);

		mockedList.add("value1");

		Mockito.verify(mockedList).add("value1");

		// ...

		Mockito.verifyNoMoreInteractions(mockedList);
	}

	@Test
	public void test09TheMockAnnotation() {
		/**
		 * <pre>
		 * 09. The @Mock Annotation
		 * - The @Mock annotation is a shorthand for the mock creation.
		 *   It marks a field as a mock. 
		 * - It has the following advantages.
		 *   - It minimizes the repetitive mock creation code.
		 *   - It makes the code more readable.
		 *   - It makes the verification error easier to read because the field name is used to identify the mock.
		 *   - The generic information will not get lost when creating the mocks (while the Mockito.mock(...) method loses that generic information).
		 * - The following statement should be executed to do the corresponding initialization
		 *   before using those fields annotated using the @Mock annotation.
		 *      MockitoAnnotations.initMocks(this);
		 * </pre>
		 */

		// The generic information will not get lost.
		List<String> mockedList1 = mockedGeneralList;

		// The generic information will get lost.
		@SuppressWarnings("unchecked")
		List<String> mockedList2 = Mockito.mock(List.class);

		System.out.println("mockedList1: " + mockedList1);
		System.out.println("mockedList2: " + mockedList2);
	}

	@Test
	public void test10StubbingConsecutiveCalls() {
		/**
		 * <pre>
		 * 10. Stubbing Consecutive Calls
		 * - Sometimes it may be needed to stub with different return values or exceptions for the same method invocation.
		 *   A typical use case could be mocking the iterators, though it can be achieved by using the java.lang.Iterable<T> interface or real collections, which offer natural ways of stubbing.
		 *   In rare scenarios, stubbing consecutive calls could be useful, though.
		 *   A possible syntax is as follows.
		 *       Mockito.when(xxx.xxx(...)).thenThrow(new RuntimeException(...)).thenReturn(...);
		 * - A possible shorthand for stubbing consecutive calls is as follows.
		 *       Mockito.when(xxx.xxx(...)).thenReturn(..., ..., ...);
		 * </pre>
		 */

		{
			SomeObject mockedSomeObject = Mockito.mock(SomeObject.class);

			String exceptionMessage = "Some exception occurred when doing something with the value \"someValue\".";
			String result = "someSpecialValue";

			// Make the mock throw a specified exception at the 1st time of the
			// specified method invocation, and then always return a specified
			// value for that method invocation from then on.
			Mockito.when(mockedSomeObject.doSomething("someValue")).thenThrow(new RuntimeException(exceptionMessage))
					.thenReturn(result);

			// The specified exception is thrown at the 1st time of the
			// specified method invocation.
			try {
				mockedSomeObject.doSomething("someValue");
			} catch (Exception e) {
				assertEquals(exceptionMessage, e.getMessage());
				e.printStackTrace();
			}

			// The specified value is always returned for that method invocation
			// from then on.
			assertEquals(result, mockedSomeObject.doSomething("someValue"));
			assertEquals(result, mockedSomeObject.doSomething("someValue"));
			assertEquals(result, mockedSomeObject.doSomething("someValue"));
		}

		{
			SomeObject mockedSomeObject = Mockito.mock(SomeObject.class);

			Mockito.when(mockedSomeObject.doSomething("someValue")).thenReturn("someValue1", "someValue2",
					"someValue3");

			assertEquals("someValue1", mockedSomeObject.doSomething("someValue"));
			assertEquals("someValue2", mockedSomeObject.doSomething("someValue"));
			assertEquals("someValue3", mockedSomeObject.doSomething("someValue"));
			assertEquals("someValue3", mockedSomeObject.doSomething("someValue"));
		}

		{
			@SuppressWarnings("unchecked")
			List<Object> mockedList = Mockito.mock(List.class);

			Mockito.when(mockedList.get(Mockito.anyInt())).thenReturn("someValue1", "someValue2", "someValue3");

			assertEquals("someValue1", mockedList.get(0));
			assertEquals("someValue2", mockedList.get(1));
			assertEquals("someValue3", mockedList.get(2));
		}
	}

	@Test
	public void test11StubbingWithCallbacks() {
		/**
		 * <pre>
		 * 11. Stubbing with Callbacks
		 * - The Answer interface is used for configuring the mock's answer.
		 *   It specifies an action to be executed and a return value to be returned when interacting with the mock.
		 * - Syntax
		 *   Mockito.when(xxx.xxx(...)).thenAnswer(...);
		 * </pre>
		 */

		SomeObject mockedSomeObject = Mockito.mock(SomeObject.class);

		System.out.println("mockedSomeObject: " + mockedSomeObject);
		System.out.println();

		Mockito.when(mockedSomeObject.doSomething(Mockito.anyString())).thenAnswer(new Answer<String>() {

			@Override
			public String answer(InvocationOnMock invocation) throws Throwable {
				Object[] arguments = invocation.getArguments();
				Object mock = invocation.getMock();

				System.out.println("Arrays.toString(arguments): " + Arrays.toString(arguments));
				System.out.println("mock: " + mock);
				System.out.println();

				// Execute some action on the mock.
				// ...

				// Return some value for the mock.
				return "Content: " + arguments[0];
			}

		});

		System.out.println("mockedSomeObject.doSomething(\"abc\"): " + mockedSomeObject.doSomething("abc"));
		System.out.println();

		System.out.println("mockedSomeObject.doSomething(\"def\"): " + mockedSomeObject.doSomething("def"));
	}

	@Test
	public void test12UsingDoXxxMethods() {
		/**
		 * <pre>
		 * 12. Using DoXxx Methods
		 * - Stubbing void methods requires a different approach from the Mockito.when(...) method,
		 *   because the Mockito.when(...) method must accept a parameter.
		 * - The following methods can be used in place of the corresponding .thenXxx(...) methods when using the Mockito.when(...) method.
		 *   - Mockito.doReturn(...).when(...)			corresponding to the method Mockito.when(...).thenReturn(...).
		 *   - Mockito.doThrow(...).when(...)			corresponding to the method Mockito.when(...).thenThrow(...).
		 *   - Mockito.doAnswer(...).when(...)			corresponding to the method Mockito.when(...).thenAnswer(...).
		 *   - Mockito.doNothing().when(...)			corresponding to no method.
		 *   - Mockito.doCallRealMethod().when(...)		corresponding to the method Mockito.when(...).thenCallRealMethod(...).
		 * - The above methods can be used in following scenarios.
		 *   - Stubbing void methods.
		 *   - Stubbing methods on spied objects.
		 *   - Stubbing the same method more than once, to change the behavior of a mock in the middle of a test.
		 *   - Alternating the Mockito.when(...) method in some other cases.
		 * </pre>
		 */

		// =================================================
		// The Mockito.when(...).thenXxx(...) Methods
		// =================================================
		{
			// =================================================================
			// The Mockito.when(...).thenReturn(...) Method
			// =================================================================
			{
				SomeObject mockedSomeObject = Mockito.mock(SomeObject.class);

				Mockito.when(mockedSomeObject.doSomethingWithReturn()).thenReturn("abc");

				System.out.println(
						"mockedSomeObject.doSomethingWithReturn(): " + mockedSomeObject.doSomethingWithReturn());
				System.out.println();
			}

			// =================================================================
			// The Mockito.when(...).thenThrow(...) Method
			// =================================================================
			{
				SomeObject mockedSomeObject = Mockito.mock(SomeObject.class);

				String exceptionMessage = "Some exception occurred.";
				Mockito.when(mockedSomeObject.doSomethingWithReturn())
						.thenThrow(new RuntimeException(exceptionMessage));

				try {
					System.out.println(
							"mockedSomeObject.doSomethingWithReturn(): " + mockedSomeObject.doSomethingWithReturn());
				} catch (Exception e) {
					assertEquals(exceptionMessage, e.getMessage());
					e.printStackTrace();
				}
				System.out.println();
			}

			// =================================================================
			// The Mockito.when(...).thenAnswer(...) Method
			// =================================================================
			{
				SomeObject mockedSomeObject = Mockito.mock(SomeObject.class);

				Mockito.when(mockedSomeObject.doSomethingWithReturn()).thenAnswer(new Answer<Object>() {

					@Override
					public Object answer(InvocationOnMock invocation) throws Throwable {
						// Do something special...
						System.out.println("Do something special...");

						// Return something special.
						return "def";
					}

				});

				System.out.println(
						"mockedSomeObject.doSomethingWithReturn(): " + mockedSomeObject.doSomethingWithReturn());
				System.out.println();
			}

			// =================================================================
			// The Mockito.when(...).thenCallRealMethod(...) Method
			// =================================================================
			{
				SomeObject mockedSomeObject = Mockito.mock(SomeObject.class);

				System.out.println(
						"mockedSomeObject.doSomethingWithReturn(): " + mockedSomeObject.doSomethingWithReturn());

				Mockito.when(mockedSomeObject.doSomethingWithReturn()).thenCallRealMethod();

				System.out.println(
						"mockedSomeObject.doSomethingWithReturn(): " + mockedSomeObject.doSomethingWithReturn());
				System.out.println();
			}
		}

		// =================================================
		// The Mockito.doXxx(...).when(...) Methods
		// =================================================
		{
			// =================================================================
			// The Mockito.doReturn(...).when(...) Method
			// =================================================================
			{
				SomeObject mockedSomeObject = Mockito.mock(SomeObject.class);

				String exceptionMessage = "Some exception occurred.";
				Mockito.when(mockedSomeObject.doSomethingWithReturn())
						.thenThrow(new RuntimeException(exceptionMessage));

				try {
					System.out.println(
							"mockedSomeObject.doSomethingWithReturn(): " + mockedSomeObject.doSomethingWithReturn());
				} catch (Exception e) {
					assertEquals(exceptionMessage, e.getMessage());
					e.printStackTrace();
				}
				System.out.println();

				// The following statements fails because the previously stubbed
				// exception takes precedence over this stubbing.
				// ---------------------------------------------------------------------------------
				// Mockito.when(mockedSomeObject.doSomethingWithReturn()).thenReturn("abc");
				// System.out.println("mockedSomeObject.doSomethingWithReturn():
				// " + mockedSomeObject.doSomethingWithReturn());
				// ---------------------------------------------------------------------------------

				// *********************************************************************************
				// #1: The Mockito.doReturn(...).when(...) method can be used to
				// override a previous exception-stubbing.
				// *********************************************************************************
				Mockito.doReturn("abc").when(mockedSomeObject).doSomethingWithReturn();

				System.out.println(
						"mockedSomeObject.doSomethingWithReturn(): " + mockedSomeObject.doSomethingWithReturn());
				System.out.println();

				List<String> spiedList = Mockito.spy(new ArrayList<>());

				// The exception java.lang.IndexOutOfBoundsException will be
				// thrown when executing the following mocking statement,
				// because the real method java.util.List.get(int) on the spied
				// list is invoked while there is actually no element in the
				// spied list then.
				// ---------------------------------------------------------------------------------
				// Mockito.when(spiedList.get(0)).thenReturn("xxx");
				// ---------------------------------------------------------------------------------

				// *********************************************************************************
				// #2: The Mockito.doReturn(...).when(...) method can be used to
				// remove the side effect when a spied object calls its some
				// real method.
				// *********************************************************************************
				Mockito.doReturn("def").when(spiedList).get(0);

				System.out.println("spiedList.get(0): " + spiedList.get(0));
				System.out.println();
			}

			// =================================================================
			// The Mockito.doThrow(...).when(...) Method
			// =================================================================
			{
				SomeObject mockedSomeObject = Mockito.mock(SomeObject.class);

				// *********************************************************************************
				// The Mockito.doThrow(...).when(...) method can be used to stub
				// the void methods with exceptions.
				// Note: This method can still stub non-void methods.
				// *********************************************************************************
				String exceptionMessage = "Some exception occurred.";
				Mockito.doThrow(new RuntimeException(exceptionMessage)).when(mockedSomeObject)
						.doSomethingWithoutReturn();

				try {
					mockedSomeObject.doSomethingWithoutReturn();
				} catch (Exception e) {
					assertEquals(exceptionMessage, e.getMessage());
					e.printStackTrace();
				}
				System.out.println();
			}

			// =================================================================
			// The Mockito.doAnswer(...).when(...) Method
			// =================================================================
			{
				SomeObject mockedSomeObject = Mockito.mock(SomeObject.class);

				// *********************************************************************************
				// The Mockito.doAnswer(...).when(...) method can be used to
				// stub the void methods with custom answers.
				// Note: This method can still stub non-void methods.
				// *********************************************************************************
				Mockito.doAnswer(new Answer<Void>() {

					@Override
					public Void answer(InvocationOnMock invocation) throws Throwable {
						// Do something special...
						System.out.println("Do something special...");

						return null;
					}

				}).when(mockedSomeObject).doSomethingWithoutReturn();

				mockedSomeObject.doSomethingWithoutReturn();
				System.out.println();
			}

			// =================================================================
			// The Mockito.doNothing().when(...) Method
			// =================================================================
			{

				SomeObject spiedSomeObject = Mockito.spy(new SomeObject());

				System.out.println("----------------------------------------------------------------");
				spiedSomeObject.doSomethingWithoutReturn();
				System.out.println("----------------------------------------------------------------");
				// *********************************************************************************
				// The Mockito.doNothing().when(...) method can be used to make
				// a call to a void method on a spied real object do nothing
				// instead of the real process.
				// *********************************************************************************
				Mockito.doNothing().when(spiedSomeObject).doSomethingWithoutReturn();
				spiedSomeObject.doSomethingWithoutReturn();
				System.out.println("----------------------------------------------------------------");
				System.out.println();

				SomeObject mockedSomeObject = Mockito.mock(SomeObject.class);

				// *********************************************************************************
				// Note: Void methods on mocks do nothing by default.
				// *********************************************************************************
				mockedSomeObject.doSomethingWithoutReturn();

				// *********************************************************************************
				// The Mockito.doNothing().when(...) method can be used to stub
				// consecutive calls on a void method.
				// *********************************************************************************
				String exceptionMessage = "Some exception occurred.";
				Mockito.doNothing().doThrow(new RuntimeException(exceptionMessage)).when(mockedSomeObject)
						.doSomethingWithoutReturn();

				// Do nothing at the 1st time.
				mockedSomeObject.doSomethingWithoutReturn();

				// Throw an exception at the 2nd time.
				try

				{
					mockedSomeObject.doSomethingWithoutReturn();
				} catch (

				Exception e)

				{
					assertEquals(exceptionMessage, e.getMessage());
					e.printStackTrace();
				}
				System.out.println();
			}

			// =================================================================
			// The Mockito.doCallRealMethod().when(...) Method
			// =================================================================
			{

				SomeObject mockedSomeObject = Mockito.mock(SomeObject.class);

				System.out.println("----------------------------------------------------------------");
				mockedSomeObject.doSomethingWithoutReturn();
				System.out.println("----------------------------------------------------------------");
				// *********************************************************************************
				// The Mockito.doCallRealMethod().when(...) method can be used
				// to make the mock call a real void method.
				// *********************************************************************************
				Mockito.doCallRealMethod().when(mockedSomeObject).doSomethingWithoutReturn();
				mockedSomeObject.doSomethingWithoutReturn();
				System.out.println("----------------------------------------------------------------");

			}
		}
	}

	@Test
	public void test13SpyingOnRealObjects() {
		/**
		 * <pre>
		 * 13. Spying on Real Objects
		 * - Spies can be created for real objects.
		 *   When using the spy, the real methods are invoked (unless a method is stubbed, using the Mockito.doXxx(...) methods).
		 * - Spies should be used carefully and occasionally, for example when dealing with legacy code.
		 * - Spying on real objects can be associated with the "partial mocking" concept.
		 * - Sometimes, it may be impossible/impractical to use the method Mockito.when(...) for stubbing spies.
		 *   In that case, use the method Mockito.doReturn(...)/Mockito.doThrow(...)/Mockito.doAnswer(...) instead.
		 * - Mockito DOES NOT delegate calls to the real object, instead it actually creates a copy of it as the spy.
		 *   The spy WILL NOT get aware of interactions and effects happened on the real object.
		 * </pre>
		 */

		{
			SomeObject realSomeObject = new SomeObject();
			SomeObject mockedSomeObject = Mockito.mock(SomeObject.class);
			SomeObject spiedSomeObject = Mockito.spy(new SomeObject());

			// The real method is invoked, on the real object.
			System.out.println("----------------------------------------");
			System.out.println("realSomeObject.doSomething(\"abc\"): " + realSomeObject.doSomething("abc"));
			System.out.println("----------------------------------------");
			System.out.println();

			// The real method is not invoked, on the mocked object.
			System.out.println("----------------------------------------");
			System.out.println("mockedSomeObject.doSomething(\"def\"): " + mockedSomeObject.doSomething("def"));
			System.out.println("----------------------------------------");
			System.out.println();

			// The real method is invoked, on the spied object.
			System.out.println("----------------------------------------");
			System.out.println("spiedSomeObject.doSomething(\"ghi\"): " + spiedSomeObject.doSomething("ghi"));
			System.out.println("----------------------------------------");
			System.out.println();

			// ---------------------------------------------------------------------

			// The real method is not invoked, on the mocked object, and the
			// method return is mocked, because the real method is stubbed.
			System.out.println("----------------------------------------");
			Mockito.when(mockedSomeObject.doSomething("def")).thenReturn("mocked [def]");
			System.out.println("mockedSomeObject.doSomething(\"def\"): " + mockedSomeObject.doSomething("def"));
			System.out.println("----------------------------------------");
			System.out.println();

			// The real method is invoked, on the spied object, but the method
			// return is mocked, because the real method is stubbed.
			System.out.println("----------------------------------------");
			Mockito.when(spiedSomeObject.doSomething("ghi")).thenReturn("mocked [ghi]");
			System.out.println("spiedSomeObject.doSomething(\"ghi\"): " + spiedSomeObject.doSomething("ghi"));
			System.out.println("----------------------------------------");
			System.out.println();

			// ---------------------------------------------------------------------

			// The real method is not invoked, on the spied object, and the
			// method return is also mocked, because the real method is stubbed
			// using the method Mockito.doReturn(...)/Mockito.doAnswer(...).
			System.out.println("----------------------------------------");
			Mockito.doReturn("mocked [ghi] 2").when(spiedSomeObject).doSomething("ghi");
			System.out.println("spiedSomeObject.doSomething(\"ghi\"): " + spiedSomeObject.doSomething("ghi"));
			System.out.println("----------------------------------------");
			System.out.println();
		}

		{
			@SuppressWarnings("unchecked")
			List<String> mockedList = Mockito.mock(List.class);

			Mockito.when(mockedList.get(0)).thenReturn("abc");
			System.out.println("mockedList.get(0): " + mockedList.get(0));

			// ---------------------------------------------------------------------

			List<String> spiedList = Mockito.spy(new ArrayList<String>());

			// The exception java.lang.IndexOutOfBoundsException will be thrown
			// if the following mocking statement is used, because the real
			// method java.util.List.get(int) on the spied list is invoked while
			// there is no element in the spied list now.
			// =================================================================
			// Mockito.when(spiedList.get(0)).thenReturn("def");
			// =================================================================

			// Using the Mockito.doXxx(...) methods to avoid the above issue.
			// =================================================================
			Mockito.doReturn("def").when(spiedList).get(0);
			// =================================================================

			System.out.println("spiedList.get(0): " + spiedList.get(0));
		}
	}

	@Test
	public void test14ChangingDefaultReturnValuesOfUnstubbedMethodInvocations() {
		/**
		 * <pre>
		 * 14. Changing Default Return Values of Unstubbed Method Invocations
		 * - When a mock is created, a strategy can be specified for that mock's return values.
		 * - This feature is a quite advanced one and typically it is not needed to write decent tests.
		 *   It can be helpful when working with legacy systems.
		 * - This feature should ONLY be used when not stubbing the method invocations.
		 * </pre>
		 */

		SomeObject mockedSomeObject1 = Mockito.mock(SomeObject.class);

		System.out.println("mockedSomeObject1.doSomething(\"abc\")\t: " + mockedSomeObject1.doSomething("abc"));
		System.out.println("mockedSomeObject1.doSomething2(\"def\")\t: " + mockedSomeObject1.doSomething2("def"));
		System.out.println();

		SomeObject mockedSomeObject2 = Mockito.mock(SomeObject.class, new Answer<String>() {

			@Override
			public String answer(InvocationOnMock invocation) throws Throwable {
				return "[Default Value] " + invocation.getArguments()[0];
			}

		});

		System.out.println("mockedSomeObject2.doSomething(\"abc\")\t: " + mockedSomeObject2.doSomething("abc"));
		System.out.println("mockedSomeObject2.doSomething2(\"def\")\t: " + mockedSomeObject2.doSomething2("def"));
		System.out.println();

		SomeObject mockedSomeObject3 = Mockito.mock(SomeObject.class, Mockito.RETURNS_SMART_NULLS);

		System.out.println("mockedSomeObject3.doSomething(\"abc\")\t: " + mockedSomeObject3.doSomething("abc"));
		System.out.println("mockedSomeObject3.doSomething2(\"def\")\t: " + mockedSomeObject3.doSomething2("def"));
	}

	@Test
	public void test15CapturingArgumentsForAssertions() {
		/**
		 * <pre>
		 * 15. Capturing Arguments for Assertions
		 * - In some situations, it is helpful to assert on certain arguments after the actual verification.
		 * - Warning: It is recommended to use the ArgumentCaptor class with verification but not with stubbing.
		 *   Using the ArgumentCaptor class with stubbing may decrease test readability.
		 * - ArgumentCaptor vs. Custom Argument Matcher
		 *   - Both techniques can be used for making sure that certain arguments have been passed to mocks.
		 *   - ArgumentCaptor may be better if
		 *     - the custom argument matcher is not likely to be reused or
		 *     - the arguments need to be asserted for verification.
		 *   - The custom argument matcher is usually better for stubbing.
		 * </pre>
		 */

		{
			SomeObject mockedSomeObject = Mockito.mock(SomeObject.class);

			mockedSomeObject.doSomething("abc");

			Mockito.verify(mockedSomeObject).doSomething("abc");
		}

		{
			SomeObject mockedSomeObject = Mockito.mock(SomeObject.class);

			mockedSomeObject.doSomething("abc");

			ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
			Mockito.verify(mockedSomeObject).doSomething(argumentCaptor.capture());
			assertEquals("abc", argumentCaptor.getValue());
		}

		{
			SomeObject mockedSomeObject = Mockito.mock(SomeObject.class);

			mockedSomeObject.doSomething3("abc", 123);

			ArgumentCaptor<String> argumentCaptor1 = ArgumentCaptor.forClass(String.class);
			ArgumentCaptor<Integer> argumentCaptor2 = ArgumentCaptor.forClass(Integer.class);
			Mockito.verify(mockedSomeObject).doSomething3(argumentCaptor1.capture(), argumentCaptor2.capture());
			assertEquals("abc", argumentCaptor1.getValue());
			assertEquals(Integer.valueOf(123), argumentCaptor2.getValue());
		}
	}

	@Test
	public void test16RealPartialMocks() {
		/**
		 * <pre>
		 * 16. Real Partial Mocks
		 * - Previously, partial mocks were considered as code smells.
		 *   But after legitimate use cases were found for partial mocks,
		 *   the support for partial mocks was added to Mockito.
		 * - Partial mocks should not be used for new, test-driven and well-designed code.
		 *   Instead, they can be used for the code that cannot be changed easily (3rd party interfaces, some legacy code, etc.).
		 * </pre>
		 */

		{
			SomeObject mockedSomeObject = Mockito.mock(SomeObject.class);

			mockedSomeObject.doSomething("abc");

			// Call the real method on a mocked object.
			Mockito.when(mockedSomeObject.doSomething("abc")).thenCallRealMethod();

			mockedSomeObject.doSomething("abc");
		}

		{
			SomeObject spiedSomeObject = Mockito.spy(new SomeObject());

			// Call the real method on a spied object.
			spiedSomeObject.doSomething("abc");
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test17ResettingMocks() {
		/**
		 * <pre>
		 * 17. Resetting Mocks
		 * - The Mockito.reset(...) method is used to reset mocks.
		 *   A reset mock forgets all its interactions and stubbing.
		 * - Normally, mocks SHOULD NOT be reset, because it could be a sign of poor tests.
		 *   Just create new mocks for subsequent uses.
		 * - If this method is used, it also probably means current testing is big.
		 *   Consider writing simple, small and focused tests over lengthy, over-specified tests.
		 * - The only reason that the Mockito.reset(...) method is added, is to make it possible to work with container-injected mocks.
		 * </pre>
		 */

		List<String> mockedList = Mockito.mock(List.class);

		Mockito.when(mockedList.get(1)).thenReturn("def");

		System.out.println("mockedList.get(1): " + mockedList.get(1));
		System.out.println("mockedList.get(1): " + mockedList.get(1));
		System.out.println();

		Mockito.verify(mockedList, Mockito.atLeastOnce()).get(1);
		Mockito.verify(mockedList, Mockito.atLeastOnce()).get(1);

		Mockito.reset(mockedList);

		Mockito.verify(mockedList, Mockito.never()).get(1);

		System.out.println("mockedList.get(1): " + mockedList.get(1));
	}

	@Test
	public void test18ValidatingFrameworkUsage() {
		/**
		 * <pre>
		 * 18. Validating Framework Usage
		 * - The Mockito.validateMockitoUsage() method explicitly validates the framework state to detect invalid use of Mockito.
		 *   Usually there is no need to call this method because Mockito validates its usage all the time on next-time basis, and it should be enough.
		 * - There are still use cases for this method.
		 *   For example, putting this method in a @After method makes know immediately when Mockito is misused instead of knowing that the next time Mockito is used.
		 * </pre>
		 */

		@SuppressWarnings({ "unchecked", "unused" })
		List<String> mockedList = Mockito.mock(List.class);

		// =====================================================================
		// Uncomment the following statement block to see the effect without
		// calling the Mockito.validateMockitoUsage() method.
		// It can be noticed from the console that those println() methods
		// should have be executed.
		// =====================================================================
		// {
		// // Invalid Use: Missing the thenXxx Part
		// Mockito.when(mockedList.get(0));
		//
		// System.out.println("...");
		// System.out.println("...");
		// System.out.println("...");
		// System.out.println();
		//
		// System.out.println("mockedList.get(0): " + mockedList.get(0));
		// }

		// =====================================================================
		// Uncomment the following statement block to see the effect with
		// calling the Mockito.validateMockitoUsage() method.
		// It can be noticed from the console that those println() methods
		// should have not be executed.
		// =====================================================================
		// {
		// // Invalid Use: Missing the thenXxx Part
		// Mockito.when(mockedList.get(0));
		//
		// Mockito.validateMockitoUsage();
		//
		// System.out.println("...");
		// System.out.println("...");
		// System.out.println("...");
		// System.out.println();
		//
		// System.out.println("mockedList.get(0): " + mockedList.get(0));
		// }
	}

	@Test
	public void test19AliasesForBehaviorDrivenDevelopment() {
		/**
		 * <pre>
		 * 19. Aliases for Behavior Driven Development
		 * - Behavior Driven Development is BDD.
		 * - BDD style of writing tests uses "//given", "//when" and "//then" comments as fundamental parts of the test methods, which is encouraged by Mockito.
		 * - The problem is that current stubbing API of the Mockito class with the canonical role of the "when" word does not integrate nicely with the "//given", "//when" and "//then" comments,
		 *   because the stubbing should belong to the "given" component but current stubbing belongs to the "when" component (Mockito.when(...).thenXxx(...)).
		 * - The BDDMockito class is hence introduced to solve this problem, as an Mockito alias for BDD style testing.
		 * </pre>
		 */

		// General Style Testing
		{
			@SuppressWarnings("unchecked")
			List<String> mockedList = Mockito.mock(List.class);

			// given
			Mockito.when(mockedList.get(0)).thenReturn("abc");

			// when
			String element = mockedList.get(0);

			// then
			assertEquals("abc", element);
		}

		// BDD Style Testing
		{
			@SuppressWarnings("unchecked")
			List<String> mockedList = Mockito.mock(List.class);

			// given
			BDDMockito.given(mockedList.get(0)).willReturn("abc");

			// when
			String element = mockedList.get(0);

			// then
			assertEquals("abc", element);
		}
	}

	@Test
	public void test20SerializableMocks() {
		/**
		 * <pre>
		 * 20. Serializable Mocks
		 * - Mocks can be made serializable using the MockSettings.serializable() method.
		 *   With this feature a mock can be used in a place that requires dependencies to be serializable.
		 * - WARNING: This should be rarely used in unit testing.
		 * - This feature is added for a specific use case of a BDD spec that had an unreliable external dependency.
		 *   This was in a web environment and the objects from the external dependency were being serialized to pass between layers.
		 * </pre>
		 */

		SomeObject mockedSomeObject = Mockito.mock(SomeObject.class);
		SomeObject mockedListAsSerializable = Mockito.mock(SomeObject.class, Mockito.withSettings().serializable());

		System.out.println("mockedList instanceof Serializable\t\t\t: " + (mockedSomeObject instanceof Serializable));
		System.out.println("mockedListAsSerializable instanceof Serializable\t: "
				+ (mockedListAsSerializable instanceof Serializable));

		List<String> spiedList = Mockito.spy(new ArrayList<>());
		@SuppressWarnings("unchecked")
		List<String> spiedListAsSerializable = Mockito.mock(ArrayList.class, Mockito.withSettings()
				.spiedInstance(new ArrayList<>()).defaultAnswer(Mockito.CALLS_REAL_METHODS).serializable());

		System.out.println("spiedList instanceof Serializable\t\t\t: " + (spiedList instanceof Serializable));
		System.out.println("spiedListAsSerializable instanceof Serializable\t\t: "
				+ (spiedListAsSerializable instanceof Serializable));
	}

	@Test
	public void test21AnnotationsCaptorAndSpyAndInjectMocks() {
		/**
		 * <pre>
		 * 21. Annotations: @Captor, @Spy and @InjectMocks
		 * - The @Captor annotation allows shorthand org.mockito.ArgumentCaptor creation on fields.
		 *   It simplifies the creation of the ArgumentCaptor objects
		 *   and it is useful when the argument to capture is a generic class and you want to avoid compiler warnings.
		 * 
		 * - The @Spy annotation allows shorthand spy objects' creation on fields.
		 *   e.g.
		 *   -	// Create a spy object by calling a constructor explicitly.
		 *   -	// This approach is same as the following one.
		 *   -	// ---------------------------------------------------------------------------------
		 *   -	// SomeObject spiedSomeObject = Mockito.spy(new SomeObject("someParameter"));
		 *   -	// ---------------------------------------------------------------------------------
		 *   -	@Spy SomeObject spiedSomeObject = new SomeObject("someParameter");
		 *   
		 *   -	// Create a spy object by calling the default constructor implicitly.
		 *   -	// This approach is same as the following one.
		 *   -	// ---------------------------------------------------------------------------------
		 *   -	// SomeObject spiedSomeObject = Mockito.spy(new SomeObject());
		 *   -	// ---------------------------------------------------------------------------------
		 *   -	@Spy SomeObject spiedSomeObject;
		 * 
		 * - The @InjectMocks annotation allows shorthand mock and spy injection on fields.
		 * </pre>
		 */

		// @Captor
		{
			// Not Using @Captor
			// There is "rawtypes" warning when defining argument for a generic
			// class.
			{
				@SuppressWarnings("unchecked")
				List<String> mockedList = Mockito.mock(List.class);

				Collection<String> collection = Arrays.asList("abc", "def", "ghi");
				mockedList.containsAll(collection);

				// The "rawtypes" warning appears if not suppressed.
				@SuppressWarnings("rawtypes")
				ArgumentCaptor<Collection> argument = ArgumentCaptor.forClass(Collection.class);
				Mockito.verify(mockedList).containsAll(argument.capture());
				assertEquals(collection, argument.getValue());
			}

			// Using @Captor
			// There is no "rawtypes" warning when defining argument for a
			// generic class.
			{
				@SuppressWarnings("unchecked")
				List<String> mockedList = Mockito.mock(List.class);

				Collection<String> collection = Arrays.asList("abc", "def", "ghi");
				mockedList.containsAll(collection);

				Mockito.verify(mockedList).containsAll(test21CaptorArgument.capture());
				assertEquals(collection, test21CaptorArgument.getValue());
			}
		}

		// @Spy
		{
			// Not Using @Spy
			{
				List<String> spiedList = Mockito.spy(new ArrayList<String>());

				try {
					System.out.println("spiedList.get(0): " + spiedList.get(0));
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
					System.out.println();
				}

				spiedList.add("someElement");
				System.out.println("spiedList.get(0): " + spiedList.get(0));
			}

			System.out.println();

			// Using @Spy
			{
				try {
					System.out.println("test21SpySpiedList.get(0): " + test21SpySpiedList.get(0));
				} catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
					System.out.println();
				}

				test21SpySpiedList.add("someElement");
				System.out.println("test21SpySpiedList.get(0): " + test21SpySpiedList.get(0));
			}
		}

		System.out.println();

		// @InjectMocks
		{
			// Not Using @InjectMocks
			{
				SomeObject someObject = new SomeObject();

				System.out.println("someObject.getSomeDependency1(): " + someObject.getSomeDependency1());
				System.out.println("someObject.getSomeDependency2(): " + someObject.getSomeDependency2());
				System.out.println();

				someObject.setSomeDependency1(Mockito.mock(SomeDependency1.class));
				someObject.setSomeDependency2(Mockito.spy(new SomeDependency2()));

				System.out.println("someObject.getSomeDependency1(): " + someObject.getSomeDependency1());
				System.out.println("someObject.getSomeDependency2(): " + someObject.getSomeDependency2());
			}

			System.out.println();

			// Using @InjectMocks
			{
				System.out.println("test21InjectMocksSomeObject.getSomeDependency1(): "
						+ test21InjectMocksSomeObject.getSomeDependency1());
				System.out.println("test21InjectMocksSomeObject.getSomeDependency2(): "
						+ test21InjectMocksSomeObject.getSomeDependency2());

				System.out.println(
						"test21InjectMocksSomeObject.getSomeDependency1() == test21InjectMocksMockedSomeDependency1: "
								+ (test21InjectMocksSomeObject
										.getSomeDependency1() == test21InjectMocksMockedSomeDependency1));
				System.out.println(
						"test21InjectMocksSomeObject.getSomeDependency2() == test21InjectMocksSpiedSomeDependency2: "
								+ (test21InjectMocksSomeObject
										.getSomeDependency2() == test21InjectMocksSpiedSomeDependency2));
			}
		}
	}

	@Test
	public void test22VerificationWithTimeout() {
		/**
		 * <pre>
		 * 22. Verification with Timeout
		 * - The Mockito.timeout(...) method allows the verification with timeout.
		 *   It causes the verification to wait for a specified period of time for a desired interaction, rather than fail immediately if that interaction has not already happened.
		 *   It may be useful for testing in concurrent conditions.
		 *   Notes:
		 *   - This method should be used rarely, try to find a better way to test the multi-threaded cases.
		 *   - Work with the InOrder verification has not been implemented.
		 * - The Timeout class can also be used mainly for custom verification.
		 * </pre>
		 */

		@SuppressWarnings("unchecked")
		List<String> mockedList = Mockito.mock(List.class);

		Mockito.when(mockedList.get(0)).thenReturn("abc");

		System.out.println("mockedList.get(0): " + mockedList.get(0));

		Mockito.verify(mockedList).get(0);

		// Cause the verification to wait for 1000 milliseconds before it fails.
		Mockito.verify(mockedList, Mockito.timeout(1000)).get(0);

		// Same as above.
		Mockito.verify(mockedList, Mockito.timeout(1000).times(1)).get(0);

		Mockito.verify(mockedList, Mockito.timeout(1000).atLeastOnce()).get(0);

		Mockito.verify(mockedList, new Timeout(1000, new VerificationMode() {

			@Override
			public void verify(VerificationData data) {
				// ... Perform some custom verification...
			}

		})).get(0);
	}

	@Test
	public void test23AutomaticInstantiationOfSpyAndInjectMocksAnnotations() {
		/**
		 * <pre>
		 * 23. Automatic Instantiation of @Spy and @InjectMocks Annotations
		 * - Mockito will now try to instantiate @Spy fields, and @InjectMocks fields using Constructor Injection, Setter Injection, or Field Injection.
		 * </pre>
		 */

		// Automatic instantiation of a @InjectMocks field using the Constructor
		// Injection approach.
		System.out.println("test23InjectedSomeObject1: " + test23InjectedSomeObject1);
		System.out.println(
				"test23InjectedSomeObject1.getSomeDependency(): " + test23InjectedSomeObject1.getSomeDependency());
		System.out.println();

		// Automatic instantiation of a @InjectMocks field using the Setter
		// Injection approach.
		System.out.println("test23InjectedSomeObject2: " + test23InjectedSomeObject2);
		System.out.println(
				"test23InjectedSomeObject2.getSomeDependency(): " + test23InjectedSomeObject2.getSomeDependency());
		System.out.println();

		// Automatic instantiation of a @InjectMocks field using the Field
		// Injection approach.
		System.out.println("test23InjectedSomeObject3: " + test23InjectedSomeObject3);
		System.out.println(
				"test23InjectedSomeObject3.getSomeDependency(): " + test23InjectedSomeObject3.getSomeDependency());
	}

	@Test
	public void test24OneLinerStubs() {
		/**
		 * <pre>
		 * 24. One-liner Stubs
		 * - Mockito now allows to create mocks when stubbing, even in one line of code, which helps to keep the test code clean.
		 * </pre>
		 */

		// Using the normal approach for the stubbing.
		{
			@SuppressWarnings("unchecked")
			List<String> mockedList = Mockito.mock(List.class);

			Mockito.when(mockedList.get(0)).thenReturn("abc");

			System.out.println("mockedList.get(0): " + mockedList.get(0));
		}

		System.out.println();

		// Using the One-liner Stubs approach for the stubbing.
		{
			List<String> mockedList = Mockito.when(Mockito.mock(List.class).get(0)).thenReturn("abc").getMock();

			System.out.println("mockedList.get(0): " + mockedList.get(0));
		}

	}

	@Test
	public void test25VerificationIgnoringStubs() {
		/**
		 * <pre>
		 * 25. Verification Ignoring Stubs
		 * - Mockito now allows to ignore stubbing for the sake of verification.
		 *   To do that, use the Mockito.ignoreStubs(...) method.
		 * 
		 *   This method is sometimes useful when coupled with the Mockito.verifyNoMoreInteractions(...) method or verification Mockito.inOrder(...) method,
		 *   helps avoid redundant verification of stubbed calls - typically we're not interested in verifying stubs.
		 * 
		 *   Warning: the Mockito.ignoreStubs(...) method might lead to overuse of the Mockito.verifyNoMoreInteractions(...) method, which is not recommended.
		 * </pre>
		 */

		{
			@SuppressWarnings("unchecked")
			List<String> mockedList1 = Mockito.mock(List.class);

			@SuppressWarnings("unchecked")
			List<String> mockedList2 = Mockito.mock(List.class);

			Mockito.when(mockedList1.get(0)).thenReturn("abc");
			Mockito.when(mockedList2.get(1)).thenReturn("def");

			System.out.println("mockedList1.get(0): " + mockedList1.get(0));
			System.out.println("mockedList2.get(1): " + mockedList2.get(1));

			Mockito.verify(mockedList1).get(0);
			Mockito.verify(mockedList2).get(1);

			Mockito.when(mockedList1.get(2)).thenReturn("ghi");

			System.out.println("mockedList1.get(2): " + mockedList1.get(2));

			// =================================================================
			// The following statement fails because the interaction
			// "mockedList1.get(2)" is not verified.
			// =================================================================
			// Mockito.verifyNoMoreInteractions(mockedList1, mockedList2);

			// =================================================================
			// The following statement succeeds because the unverified
			// interaction "mockedList1.get(2)" is ignored.
			// =================================================================
			Mockito.verifyNoMoreInteractions(Mockito.ignoreStubs(mockedList1, mockedList2));
		}

		System.out.println();

		{
			@SuppressWarnings("unchecked")
			List<String> mockedList1 = Mockito.mock(List.class);

			@SuppressWarnings("unchecked")
			List<String> mockedList2 = Mockito.mock(List.class);

			Mockito.when(mockedList1.get(0)).thenReturn("abc");
			Mockito.when(mockedList2.get(1)).thenReturn("def");
			Mockito.when(mockedList1.get(2)).thenReturn("ghi");

			System.out.println("mockedList1.get(0): " + mockedList1.get(0));
			System.out.println("mockedList2.get(1): " + mockedList2.get(1));
			System.out.println("mockedList1.get(2): " + mockedList1.get(2));

			InOrder inOrder = Mockito.inOrder(mockedList1, mockedList2);

			inOrder.verify(mockedList1).get(0);
			inOrder.verify(mockedList2).get(1);

			// =================================================================
			// Without the following InOrder re-declaration, a failure will
			// occur in the testing reporting the interaction
			// "mockedList1.get(2)" is not expected.
			// =================================================================
			inOrder = Mockito.inOrder(Mockito.ignoreStubs(mockedList1, mockedList2));

			inOrder.verifyNoMoreInteractions();
		}
	}

	@Test
	public void test26MockingDetails() {
		/**
		 * <pre>
		 * 26. Mocking Details
		 * - The Mockito.mockingDetails(...).xxx() methods return details about the mock, as follows.
		 *   .isMock()			: Return whether the specified object is a mock.
		 *   					  This method returns true for a spy because a spy is just a different kind of mocks.
		 *   .isSpy()			: Return whether the specified object is a spy.
		 *   .getInvocations()	: Return a collection of methods indicating the invocations occurred on the specified mock.
		 * </pre>
		 */

		List<String> list = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<String> mockedList = Mockito.mock(List.class);
		List<String> spiedList = Mockito.spy(new ArrayList<>());

		System.out.println("Mockito.mockingDetails(list).isMock()\t\t: " + Mockito.mockingDetails(list).isMock());
		System.out.println(
				"Mockito.mockingDetails(mockedList).isMock()\t: " + Mockito.mockingDetails(mockedList).isMock());
		System.out
				.println("Mockito.mockingDetails(spiedList).isMock()\t: " + Mockito.mockingDetails(spiedList).isMock());
		System.out.println();

		System.out.println("Mockito.mockingDetails(list).isSpy()\t\t: " + Mockito.mockingDetails(list).isSpy());
		System.out
				.println("Mockito.mockingDetails(mockedList).isSpy()\t: " + Mockito.mockingDetails(mockedList).isSpy());
		System.out.println("Mockito.mockingDetails(spiedList).isSpy()\t: " + Mockito.mockingDetails(spiedList).isSpy());
		System.out.println();

		System.out.println("Mockito.mockingDetails(mockedList).getInvocations(): "
				+ Mockito.mockingDetails(mockedList).getInvocations());
		Mockito.when(mockedList.get(Mockito.anyInt())).thenReturn("Some Value");
		System.out.println("mockedList.get(1): " + mockedList.get(1));
		System.out.println("mockedList.get(3): " + mockedList.get(3));
		System.out.println("mockedList.get(5): " + mockedList.get(5));
		System.out.println("Mockito.mockingDetails(mockedList).getInvocations(): "
				+ Mockito.mockingDetails(mockedList).getInvocations());
		System.out.println();

		System.out.println("Mockito.mockingDetails(spiedList).getInvocations(): "
				+ Mockito.mockingDetails(spiedList).getInvocations());
		spiedList.add("abc");
		System.out.println("spiedList.get(0): " + spiedList.get(0));
		System.out.println("Mockito.mockingDetails(spiedList).getInvocations(): "
				+ Mockito.mockingDetails(spiedList).getInvocations());
	}

	@Test
	public void test27DelegatingCallsToRealInstance() {
		/**
		 * <pre>
		 * 27. Delegating Calls to Real Instance
		 * - The AdditionalAnswers.delegatesTo(...) method returns an answer that directly forwards the call to the specified delegate. 
		 * - This method is useful for spies or partial mocks of objects that are difficult to mock or spy using the usual spy API.
		 * - The delegate may or may not be of the same type as the mock.
		 * - Possible use cases for this feature are as follows.
		 *   - Final classes but with an interface.
		 *   - Already custom proxied objects.
		 *   - Special objects with a finalize method (i.e. to avoid executing it 2 times).
		 * - Differences from the Regular Spy
		 *   - The regular spy contains all states from the spied instance and the methods are invoked on the spy.
		 *     The spied instance is only used at mock creation to copy the states from.
		 *     Calling a method on a regular spy internally calls other method on this spy.
		 *     Method calls are remembered for verifications and can be effectively stubbed.
		 *   - The mock that delegates simply delegates all methods to the delegate.
		 *     Method calls are not remembered and stubbing does not have effect on them.
		 *     The mock that delegates is less powerful than the regular spy but it is useful when the regular spy cannot be created.
		 * </pre>
		 */

		// Mock a final class.
		// The following statement causes a Mockito exception because a final
		// class cannot be mocked.
		// {
		// SomeObject4 mockedSomeObject4 = Mockito.mock(SomeObject4.class);
		//
		// // ...
		// }

		// Mock an interface.
		{
			SomeInterface mockedSomeInterface = Mockito.mock(SomeInterface.class);

			System.out.println("mockedSomeInterface: " + mockedSomeInterface);
			System.out.println("mockedSomeInterface.getSomeValue(): " + mockedSomeInterface.getSomeValue());
		}

		System.out.println();

		// Mock a final class by delegating.
		{
			SomeInterface mockedSomeObject4 = Mockito.mock(SomeInterface.class,
					AdditionalAnswers.delegatesTo(new SomeObject4()));

			System.out.println("mockedSomeObject4: " + mockedSomeObject4);
			System.out.println("mockedSomeObject4.getSomeValue(): " + mockedSomeObject4.getSomeValue());
		}
	}

	@Test
	public void test28MockMakerApi() {
		/**
		 * <pre>
		 * 28. MockMaker API
		 * - The MockMaker API (the MockMaker interface) is an extension point that makes it possible to use custom dynamic proxies and avoid using the default byte-buddy/asm/objenesis implementation.
		 *   For example, the Android developers can use a MockMaker that can work with Dalvik VM and hence bring Mockito to Android app development.
		 *   Xxx xxx xxx xxx.
		 * </pre>
		 */

		MockMaker mockMaker = new CglibMockMaker();
		System.out.println("mockMaker.getHandler(null)\t\t: " + mockMaker.getHandler(null));

		List<String> list = new ArrayList<>();
		System.out.println("mockMaker.getHandler(list)\t\t: " + mockMaker.getHandler(list));

		@SuppressWarnings("unchecked")
		List<String> mockedList = Mockito.mock(List.class);
		System.out.println("mockMaker.getHandler(mockedList)\t: " + mockMaker.getHandler(mockedList));

		List<String> spiedList = Mockito.spy(new ArrayList<>());
		System.out.println("mockMaker.getHandler(spiedList)\t\t: " + mockMaker.getHandler(spiedList));
	}

	@Test
	public void test29BddStyleVerification() {
		/**
		 * <pre>
		 * 29. BDD Style Verification
		 * - The BDD style verification can be enabled by starting verification with the BDD "then" keyword.
		 *       BDDMockito.then(...).should(BDDMockito.times(...)).xxxXxx(...);
		 * </pre>
		 */

		@SuppressWarnings("unchecked")
		List<String> mockedList = Mockito.mock(List.class);

		// given
		BDDMockito.given(mockedList.get(0)).willReturn("abc");
		BDDMockito.given(mockedList.size()).willReturn(3);

		// when
		String element = mockedList.get(0);

		int size = -1;
		if (mockedList.size() > 0) {
			size = mockedList.size();
		}

		// then
		assertEquals("abc", element);
		assertEquals(3, size);

		BDDMockito.then(mockedList).should(BDDMockito.times(1)).get(0);
		BDDMockito.then(mockedList).should(BDDMockito.times(2)).size();
	}

	@Test
	public void test30SpyingOrMockingAbstractClasses() {
		/**
		 * <pre>
		 * 30. Spying or Mocking Abstract Classes
		 * - Xxx xxx xxx xxx.
		 *   Xxx xxx xxx xxx.
		 * </pre>
		 */

		List<String> spiedList1 = Mockito.spy(new ArrayList<>());

		// Approach #1: Mockito.spy({ABSTRACT_CLASS_NAME}.class);
		// This approach is convenient.
		@SuppressWarnings("unchecked")
		List<String> spiedList2 = Mockito.spy(AbstractList.class);

		// Approach #2: Mockito.mock({ABSTRACT_CLASS_NAME}.class,
		// Mockito.withSettings().useConstructor().defaultAnswer(Mockito.CALLS_REAL_METHODS));
		// This approach is robust.
		@SuppressWarnings("unchecked")
		List<String> spiedList3 = Mockito.mock(AbstractList.class,
				Mockito.withSettings().useConstructor().defaultAnswer(Mockito.CALLS_REAL_METHODS));

		// Approach #3: Mockito.mock({ABSTRACT_CLASS_NAME}.class,
		// Mockito.withSettings().useConstructor().outerInstance({OUTER_INSTANCE}).defaultAnswer(Mockito.CALLS_REAL_METHODS));
		// This approach is for a non-static inner abstract class.
		// @SuppressWarnings("unchecked")
		// List<String> spiedList4 = Mockito.mock(AbstractList.class,
		// Mockito.withSettings().useConstructor()
		// .outerInstance(outerInstance).defaultAnswer(Mockito.CALLS_REAL_METHODS));

		System.out.println("Commons.getObjectInfo(spiedList1): " + Commons.getObjectInfo(spiedList1));
		System.out.println("Commons.getObjectInfo(spiedList2): " + Commons.getObjectInfo(spiedList2));
		System.out.println("Commons.getObjectInfo(spiedList3): " + Commons.getObjectInfo(spiedList3));
	}

	@Test
	public void test31SerializedOrDeserializedMocksAcrossClassloaders() {
		/**
		 * <pre>
		 * 31. Serialized or Deserialized Mocks across Classloaders
		 * - Mockito introduces serialization across classloader.
		 *   Like with any other form of serialization, all types in the mock hierarchy have to be serializable, including the answers.
		 * </pre>
		 */

		SomeObject mockedSomeObject1 = Mockito.mock(SomeObject.class);
		SomeObject mockedSomeObject2 = Mockito.mock(SomeObject.class, Mockito.withSettings().serializable());
		SomeObject mockedSomeObject3 = Mockito.mock(SomeObject.class,
				Mockito.withSettings().serializable(SerializableMode.ACROSS_CLASSLOADERS));

		System.out.println("mockedSomeObject1 instanceof Serializable: " + (mockedSomeObject1 instanceof Serializable));
		System.out.println("mockedSomeObject2 instanceof Serializable: " + (mockedSomeObject2 instanceof Serializable));
		System.out.println("mockedSomeObject3 instanceof Serializable: " + (mockedSomeObject3 instanceof Serializable));
	}

	@Test
	public void test32GenericSupportWithDeepStubs() {
		/**
		 * <pre>
		 * 32. Generic Support with Deep Stubs
		 * - Xxx xxx xxx xxx.
		 * Note: In most scenarios, a mock returning a mock is wrong.
		 * </pre>
		 */

		// Normal Mocking
		// An exception (java.lang.NullPointerException) occurs.
		{
			// SomeObject5s mockedSomeObject5s =
			// Mockito.mock(SomeObject5s.class);
			//
			// // The "mockedSomeObject5s.iterator()" statement returns null.
			// SomeObject5 someObject5 = mockedSomeObject5s.iterator().next();
			//
			// System.out.println("someObject5: " + someObject5);
		}

		// Mocking Using Deep Stubs
		// No exception occurs.
		{
			SomeObject5s mockedSomeObject5s = Mockito.mock(SomeObject5s.class, Mockito.RETURNS_DEEP_STUBS);

			SomeObject5 someObject5 = mockedSomeObject5s.iterator().next();

			System.out.println("someObject5: " + someObject5);
		}
	}

	@Test
	public void test33MockitoJUnitRule() {
		/**
		 * <pre>
		 * 33. Mockito JUnit Rule
		 * - The MockitoJUnit.rule() method creates a rule instance that initiates mocks.
		 * </pre>
		 */

		// See the test class FieldInitializationTest3 for an example.
	}

	@Test
	public void test34SwitchingOnOrOffPlugins() {
		/**
		 * <pre>
		 * 34. Switching on or off Plug-ins
		 * - See the PluginSwitch interface for details.
		 *   Note: This feature is an incubating one.
		 * </pre>
		 */

		// ...
	}

	@Test
	public void test35CustomVerificationFailureMessage() {
		/**
		 * <pre>
		 * 35. Custom Verification Failure Message
		 * - The Mockito.description("...") method allows to specify a custom verification failure message.
		 *   Note: This feature is only available since the version 2.0.0 of Mockito.
		 * </pre>
		 */

		@SuppressWarnings("unchecked")
		List<String> mockedList = Mockito.mock(List.class);

		Mockito.when(mockedList.get(0)).thenReturn("abc");

		System.out.println("mockedList.get(0): " + mockedList.get(0));

		Mockito.verify(mockedList).get(0);

		// =================================================
		// This feature is only available since the version 2.0.0 of Mockito.
		// =================================================
		// Mockito.verify(mockedList, Mockito.description("...")).get(1);
	}

	@Test
	public void test51AvoidingCompilerWarningsOfUncheckedGenericObjects() {
		@SuppressWarnings("unchecked")
		List<String> mockedList = Mockito.mock(List.class);

		System.out.println("mockedList: " + mockedList);
		System.out.println("test51MockedList: " + test51MockedList);
	}

}
