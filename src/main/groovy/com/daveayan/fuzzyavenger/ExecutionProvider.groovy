package com.daveayan.fuzzyavenger;

import java.util.List;



public interface ExecutionProvider {
	public void initialize();
	public List<Object> run(List<Object> data, List<Object> parameters, Function<?, ?> function, int numberOfWorkers, int numberOfSecondsTimeout);
	public void shutdown();
}
