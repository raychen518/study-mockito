package com.sentin.study.mockito;

public class SomeObject {

	private SomeDependency1 someDependency1;

	private SomeDependency2 someDependency2;

	public SomeObject() {
	}

	public SomeObject(SomeDependency1 someDependency1, SomeDependency2 someDependency2) {
		this.someDependency1 = someDependency1;
		this.someDependency2 = someDependency2;
	}

	public Object doSomething(String someParameter) {
		System.out.println(this + ": Doing Something... (" + someParameter + ")");
		return "[" + someParameter + "]";
	}

	public Object doSomething2(String someParameter) {
		System.out.println(this + ": Doing Something 2... (" + someParameter + ")");
		return "[" + someParameter + "]";
	}

	public Object doSomething3(String someParameter1, Integer someParameter2) {
		System.out.println(this + ": Doing Something 3... (" + someParameter1 + ", " + someParameter2 + ")");
		return "[" + someParameter1 + ", " + someParameter2 + "]";
	}

	public Object doSomethingWithReturn() {
		System.out.println(this + ": Doing Something with Return... ()");
		return new Object();
	}

	public void doSomethingWithoutReturn() {
		System.out.println(this + ": Doing Something without Return... ()");
	}

	public SomeDependency1 getSomeDependency1() {
		return someDependency1;
	}

	public void setSomeDependency1(SomeDependency1 someDependency1) {
		this.someDependency1 = someDependency1;
	}

	public SomeDependency2 getSomeDependency2() {
		return someDependency2;
	}

	public void setSomeDependency2(SomeDependency2 someDependency2) {
		this.someDependency2 = someDependency2;
	}

}
