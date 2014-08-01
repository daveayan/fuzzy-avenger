package com.daveayan.fuzzyavenger;

import java.util.List;

public interface Function<F,T> {
	T apply(F input, List<Object> parameters);
}
