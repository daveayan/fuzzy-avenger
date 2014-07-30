package com.daveayan.fuzzyavenger

import com.google.common.base.Function

class Lists {
	ExecutionProvider ep;
	public static Lists initialize(ExecutionProvider ep) {
		Lists l = new Lists();
		l.ep = ep;
		return l
	}
	
	public <F,T> List<T> apply(List<F> list, Function<? super F,? extends T> function, int numberOfWorkers) {
		ep.run(list, function, numberOfWorkers)
		return []
	}
	
	public static <F,T> List<T> apply(ExecutionProvider ep, List<F> list, Function<? super F,? extends T> function, int numberOfWorkers) {
		ep.initialize()
		Lists l = Lists.initialize(ep)
		l.apply(list, null, numberOfWorkers)
		l.shutdown()
		return []
	}
	
	public void shutdown() {
		ep.shutdown()
	}
}
