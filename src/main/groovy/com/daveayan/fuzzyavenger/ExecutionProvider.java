package com.daveayan.fuzzyavenger;

import java.util.List;

import com.google.common.base.Function;

public interface ExecutionProvider {
	public void initialize();
	public void run(List<Object> data, Function<?, ?> function, int numberOfWorkers);
	public void shutdown();
}
