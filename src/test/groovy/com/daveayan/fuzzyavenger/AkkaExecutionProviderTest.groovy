package com.daveayan.fuzzyavenger;

import org.junit.Test

import com.daveayan.fuzzyavenger.providers.akka.AkkaExecutionProvider

public class AkkaExecutionProviderTest {
	def results
	def Lists l
	@Test public void test() {
		l = Lists.initialize(AkkaExecutionProvider.newInstance())
		
		results = l.apply(["1", "2", "3"], [], new LongRunningFunction(), 2, 60)
		println results
	}
}

class LongRunningFunction implements Function {
	def apply(Object input, List<Object> parameters) {
		return "${input}%"
	}
}
