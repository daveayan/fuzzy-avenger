package com.daveayan.fuzzyavenger.three

import org.junit.BeforeClass
import org.junit.Test

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.UntypedActor
import akka.japi.Creator

class ThreeTest {
	static def system_name = "com-daveayan-fuzzyavenger-three-Main"
	static def system
	
	@Test public void listening_to_system_shutdown_command() {
		println "BEGIN - ${this}"
		def shutdownCommandListener = system.actorOf(new Props(ActorShutdownCommandListener.class), "shutdownCommandListener")
		def masterActor = system.actorOf(Props.create(new MasterActorCreator()), "masterActor-1")
		
		masterActor.tell(new Object(), ActorRef.noSender())
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

class MasterActorCreator implements Creator<MasterActor> {
	public MasterActor create(){
		return new MasterActor();
	}
}

class MasterActor extends UntypedActor {
	public void onReceive(Object message) {
		println "BEGIN - ${this} - I have this message ${message}"
		println "END - ${this} - I have this message ${message}"
		getContext().stop(getSelf())
	}
}
