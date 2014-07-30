package com.daveayan.fuzzyavenger;



public interface ExecutionProvider {
	public void initialize();
	public void run(List<Object> data, List<Object> parameters, Function<?, ?> function, int numberOfWorkers);
	public void shutdown();
}
