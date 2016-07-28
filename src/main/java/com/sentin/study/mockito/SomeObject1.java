package com.sentin.study.mockito;

public class SomeObject1 {

	private SomeDependency someDependency;

	public SomeObject1(SomeDependency someDependency) {
		this.someDependency = someDependency;
	}

	public SomeDependency getSomeDependency() {
		return someDependency;
	}

}
