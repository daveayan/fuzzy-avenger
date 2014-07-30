package com.daveayan.fuzzyavenger

import com.daveayan.fuzzyavenger.providers.akka.AkkaExecutionProvider;



class Lists {
	ExecutionProvider ep;
	public static Lists initialize(ExecutionProvider ep) {
		Lists l = new Lists();
		l.ep = ep;
		return l
	}
	
	public <F,T> List<T> apply(List<F> data, List<Object> parameters, Function<? super F,? extends T> function, int numberOfWorkers) {
		ep.run(data, parameters, function, numberOfWorkers)
		return []
	}
	
	public static <F,T> List<T> apply(ExecutionProvider ep, List<F> data, List<Object> parameters, Function<? super F,? extends T> function, int numberOfWorkers) {
		ep.initialize()
		Lists l = Lists.initialize(ep)
		l.apply(data, parameters, function, numberOfWorkers)
		Thread.sleep(20*1000)
		l.shutdown()
		return []
	}
	
	public static <F,T> List<T> applyNWorkers(List<F> data, List<Object> parameters, Function<? super F,? extends T> function, int numberOfWorkers) {
		return Lists.apply(AkkaExecutionProvider.newInstance(), data, parameters, function, numberOfWorkers)
	}
	
	public static <F,T> List<T> applySizeOfDataSetWorkers(List<F> data, List<Object> parameters, Function<? super F,? extends T> function) {
		return Lists.apply(AkkaExecutionProvider.newInstance(), data, parameters, function, data.size())
	}
	
	public static <F,T> List<T> applyOneWorker(List<F> data, List<Object> parameters, Function<? super F,? extends T> function) {
		return Lists.apply(AkkaExecutionProvider.newInstance(), data, parameters, function, 1)
	}
	
	public void shutdown() {
		ep.shutdown()
	}
}
