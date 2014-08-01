package com.daveayan.fuzzyavenger;

import java.util.List;

import com.daveayan.fuzzyavenger.providers.akka.AkkaExecutionProvider;



public class Lists<F> {
	ExecutionProvider ep;
	public static Lists initialize(ExecutionProvider ep) {
		Lists l = new Lists();
		l.ep = ep;
		return l;
	};
	
	public <F, T> List<Object> apply(List<Object> data, List<Object> parameters, Function<? super F,? extends T> function, int numberOfWorkers, int numberOfSecondsTimeout) {
		List<Object> results = ep.run(data, parameters, function, numberOfWorkers, numberOfSecondsTimeout);
		return results;
	}
	
	public static <F, T> List<Object> apply(ExecutionProvider ep, List<F> data, List<Object> parameters, Function<? super F,? extends T> function, int numberOfWorkers, int numberOfSecondsTimeout) {
		ep.initialize();
		Lists l = Lists.initialize(ep);
		List<Object> results = l.apply(data, parameters, function, numberOfWorkers, numberOfSecondsTimeout);
		l.shutdown();
		return results;
	}
	
	public static <F,T> List<Object> applyNWorkers(List<F> data, List<Object> parameters, Function<? super F,? extends T> function, int numberOfWorkers) {
		return Lists.apply(AkkaExecutionProvider.newInstance(), data, parameters, function, numberOfWorkers, 60);
	}
	
	public static <F,T> List<Object> applySizeOfDataSetWorkers(List<F> data, List<Object> parameters, Function<? super F,? extends T> function) {
		return Lists.apply(AkkaExecutionProvider.newInstance(), data, parameters, function, data.size(), 60);
	}
	
	public static <F,T> List<Object> applyOneWorker(List<F> data, List<Object> parameters, Function<? super F,? extends T> function) {
		return Lists.apply(AkkaExecutionProvider.newInstance(), data, parameters, function, 1, 60);
	}
	
	public static <F,T> List<Object> applyNWorkers(List<F> data, List<Object> parameters, Function<? super F,? extends T> function, int numberOfWorkers, int numberOfSecondsTimeout) {
		return Lists.apply(AkkaExecutionProvider.newInstance(), data, parameters, function, numberOfWorkers, numberOfSecondsTimeout);
	}
	
	public static <F,T> List<Object> applySizeOfDataSetWorkers(List<F> data, List<Object> parameters, Function<? super F,? extends T> function, int numberOfSecondsTimeout) {
		return Lists.apply(AkkaExecutionProvider.newInstance(), data, parameters, function, data.size(), numberOfSecondsTimeout);
	}
	
	public static <F,T> List<Object> applyOneWorker(List<F> data, List<Object> parameters, Function<? super F,? extends T> function, int numberOfSecondsTimeout) {
		return Lists.apply(AkkaExecutionProvider.newInstance(), data, parameters, function, 1, numberOfSecondsTimeout);
	}
	
	public void shutdown() {
		ep.shutdown();
	}
}
