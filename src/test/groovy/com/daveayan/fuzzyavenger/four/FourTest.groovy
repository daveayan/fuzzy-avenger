package com.daveayan.fuzzyavenger.four

import org.junit.BeforeClass
import org.junit.Test

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.UntypedActor
import akka.japi.Creator
import akka.routing.RoundRobinRouter

class FourTest {
	static def system_name = "com-daveayan-fuzzyavenger-four-Main"
	static def system
	
	@Test public void listening_to_system_shutdown_command() {
		println "BEGIN - ${this}"
		def nrOfWorkers = 4
		
		def shutdownCommandListener = system.actorOf(new Props(ActorShutdownCommandListener.class), "shutdownCommandListener")
		def workerRouter = system.actorOf(new Props(MasterActor.class) .withRouter(new RoundRobinRouter(nrOfWorkers)), "workerRouter");
		
		for(i in 0..10) {
			workerRouter.tell(i, ActorRef.noSender())
		}
		
		shutdownCommandListener.tell(new Object(), ActorRef.noSender())
		
		println "END - ${this}"
	}
	
	@BeforeClass public static void before_class() {
		system = ActorSystem.create(system_name);
	}
}

class ActorShutdownCommandListener extends UntypedActor {
	public void onReceive(Object message) {
		println "BEGIN - ${this} - I have this message ${message}"
		getContext().stop(getSelf())
		getContext().system().shutdown()
		println "END - ${this} - I have this message ${message}"
	}
}

class MasterActor extends UntypedActor {
	public void onReceive(Object message) {
		println "BEGIN - ${this} - I have this message ${message}"
		println "END - ${this} - I have this message ${message}"
	}
}
