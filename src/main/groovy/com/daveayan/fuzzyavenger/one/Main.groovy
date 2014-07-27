package com.daveayan.fuzzyavenger.one

import akka.actor.ActorSystem
import akka.actor.UntypedActor

class Main {
	public static void main (String[] args) {
		Main m = new Main()
		m.runit()
	}
	
	def runit() {
		println "${this} - BEGIN"
		def system = ActorSystem.create("com-daveayan-fuzzyavenger-one-Main");
		system.shutdown()
		println "${this} - END"
	}
}
