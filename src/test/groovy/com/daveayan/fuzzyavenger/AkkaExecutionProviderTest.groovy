package com.daveayan.fuzzyavenger;

import org.junit.Test

import com.daveayan.fuzzyavenger.providers.akka.AkkaExecutionProvider

public class AkkaExecutionProviderTest {
	def results
	def Lists l
	@Test public void one_class_by_itself() {
		l = Lists.initialize(AkkaExecutionProvider.newInstance())		
		results = l.apply(["1", "2", "3"], [], new LongRunningFunction(), 2, 60)
		assert results == ["1%", "2%", "3%"]
		l.shutdown()
	}
	
	@Test public void can_reuse_the_execution_provider() {
		def ep = AkkaExecutionProvider.newInstance()
		def l1 = Lists.initialize(ep)
		def l2 = Lists.initialize(ep)
		def l3 = Lists.initialize(ep)
		
		results = l1.apply(["1", "2", "3"], [], new LongRunningFunction(), 2, 60)
		assert results == ["1%", "2%", "3%"]
		
		results = l2.apply(["A", "B", "C"], [], new LongRunningFunction(), 2, 60)
		assert results == ["A%", "B%", "C%"]
		
		results = l3.apply(["A1", "B2", "C3"], [], new LongRunningFunction(), 2, 60)
		assert results == ["A1%", "B2%", "C3%"]
		
		l1.shutdown()
		l2.shutdown()
		l3.shutdown()
	}
	
	@Test public void can_reuse_list() {
		l = Lists.initialize(AkkaExecutionProvider.newInstance())
		
		results = l.apply(["1", "2", "3"], [], new LongRunningFunction(), 2, 60)
		assert results == ["1%", "2%", "3%"]
		
		results = l.apply(["A", "B", "C"], [], new LongRunningFunction(), 2, 60)
		assert results == ["A%", "B%", "C%"]
		
		results = l.apply(["A1", "B2", "C3"], [], new LongRunningFunction(), 2, 60)
		assert results == ["A1%", "B2%", "C3%"]
		
		l.shutdown()
	}
	
	@Test public void issue_1_fix() {
		l = Lists.initialize(AkkaExecutionProvider.newInstance())
		def original_list = ["1", "2", "3"]
		
		results = l.apply(original_list, [], new LongRunningFunction(), 2, 60)
		assert results == ["1%", "2%", "3%"]
		assert original_list == ["1", "2", "3"]
		
		l.shutdown()
	}
}

class LongRunningFunction implements Function {
	def apply(Object input, List<Object> parameters) {
		return "${input}%"
	}
}
