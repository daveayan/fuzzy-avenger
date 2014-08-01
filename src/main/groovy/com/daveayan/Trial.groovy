package com.daveayan

import com.daveayan.fuzzyavenger.Function
import com.daveayan.fuzzyavenger.Lists
import com.daveayan.fuzzyavenger.providers.akka.AkkaExecutionProvider

class Trial {
	public static void main (String[] args) {
		def endtime = System.currentTimeMillis()
		def starttime = System.currentTimeMillis()
		AkkaExecutionProvider aep = new AkkaExecutionProvider()
		aep.initialize()
		Lists l = Lists.initialize(aep)
		def results = []
//		results = l.apply(["1", "2", "3"], [], new LongRunningFunction(), 4, 15)
//		println "HEHE - ${results}"
//		results = l.apply(["A", "B", "C", "D", "E", "F", "G"], [], new LongRunningFunction(), 3, 60)
//		println "HEHE - ${results}"
		results = l.apply(["A", "B", "C", "D", "E", "F", "G"], [], new LongRunningFunction(), 7, 60)
//		println "HEHE - ${results}"
//		results = l.apply(["A"], [], new LongRunningFunction(), 3, 15)
		println "HEHE - ${results}"
		l.shutdown()
		
		endtime = System.currentTimeMillis()
		println "\nTOOK ${endtime - starttime}ms = ${(endtime - starttime)/1000} s"
		
		starttime = System.currentTimeMillis()
		
//		results = process(["1", "2", "3"])
//		println "HAHA - ${results}"
//		results = process(["A", "B", "C", "D", "E", "F", "G"])
//		println "HAHA - ${results}"
		results = process(["A", "B", "C", "D", "E", "F", "G"])
		println "HAHA - ${results}"
//		results = process(["A"])
//		println "HAHA - ${results}"
		
		endtime = System.currentTimeMillis()
		println "\nTOOK ${endtime - starttime}ms = ${(endtime - starttime)/1000} s"
		
		
		
//		results = Lists.apply(AkkaExecutionProvider.newInstance(), ["1", "2", "3"], [], new LongRunningFunction(), 4, 15)
//		println "HOHO - ${results}"
//		results = Lists.apply(AkkaExecutionProvider.newInstance(), ["A", "B", "C", "D", "E", "F", "G"], [], new LongRunningFunction(), 3, 60)
//		println "HOHO - ${results}"
//		results = Lists.apply(AkkaExecutionProvider.newInstance(), ["A", "B", "C", "D", "E", "F", "G"], [], new LongRunningFunction(), 3, 60)
//		println "HOHO - ${results}"
//		results = Lists.apply(AkkaExecutionProvider.newInstance(), ["A"], [], new LongRunningFunction(), 3, 15)
//		println "HOHO - ${results}"
	}
	
	public static List process(input) {
		def output = []
		input.each {
			output << "${it}%"
//			Thread.sleep(40)
		}
		return output
	}
}

class LongRunningFunction implements Function<String, String> {
	@Override
	public String apply(String arg0, List<Object> parameters) {
		println "${this} - Making Service Call, will run for 1 seconds - ${arg0}"
//		Thread.sleep(1*1000)
		println "${this} - Done with Service Call - ${arg0}"
		return "${arg0}%";
	}
}