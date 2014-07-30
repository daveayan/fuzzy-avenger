package com.daveayan

import com.daveayan.fuzzyavenger.Function
import com.daveayan.fuzzyavenger.Lists
import com.daveayan.fuzzyavenger.providers.akka.AkkaExecutionProvider

class Trial {
	public static void main (String[] args) {
		AkkaExecutionProvider aep = new AkkaExecutionProvider()
		aep.initialize()
		Lists l = Lists.initialize(aep)
		
//		l.apply(["1", "2", "3"], [], new LongRunningFunction(), 4)
		l.apply(["A", "B", "C", "D", "E", "F", "G"], [], new LongRunningFunction(), 3)
		Thread.sleep(30*1000)
		l.shutdown()
		
		
//		Lists.apply(AkkaExecutionProvider.newInstance(), ["1", "2", "3"], null, 4)
//		Lists.apply(AkkaExecutionProvider.newInstance(), ["A", "B", "C", "D"], null, 3)
	}
}

class LongRunningFunction implements Function<String, String> {
	@Override
	public String apply(String arg0, List<Object> parameters) {
		println "${this} - Making Service Call, will run for 5 seconds - ${arg0}"
		Thread.sleep(5*1000)
		println "${this} - Done with Service Call - ${arg0}"
		return "${arg0}%";
	}
}