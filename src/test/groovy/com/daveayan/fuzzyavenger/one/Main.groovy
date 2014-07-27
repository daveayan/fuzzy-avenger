package com.daveayan.fuzzyavenger.one

import org.junit.BeforeClass
import org.junit.Test

import akka.actor.ActorSystem

class Main {
	static def system_name = "com-daveayan-fuzzyavenger-one-Main"
	static def system
	
	@Test public void system_created_and_shutdown() {
		println "${this} - BEGIN"
		system.shutdown()
		println "${this} - END"
	}
	
	@BeforeClass public static void before_class() {
		system = ActorSystem.create(system_name);
	}
}
