package com.daveayan.fuzzyavenger

public interface Function<F,T> {
	T apply(F input, List<Object> parameters)
}
