package com.daveayan.fuzzyavenger.two

import org.junit.BeforeClass
import org.junit.Test

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.UntypedActor
import akka.japi.Creator

class TwoTest {
	static def system_name = "com-daveayan-fuzzyavenger-two-Main"
	static def system
	
	@Test public void actor_created_message_receieved() {
		println "${this} - BEGIN"
		def masterActor = system.actorOf(Props.create(new MasterActorCreator()), "masterActor-1")
		masterActor.tell(new Object(), ActorRef.noSender())
		system.shutdown()
		println "${this} - END"
	}
	
	@BeforeClass public static void before_class() {
		system = ActorSystem.create(system_name);
	}
}

class MasterActorCreator implements Creator<MasterActor> {
	public MasterActor create(){
		return new MasterActor();
	}
}

class MasterActor extends UntypedActor {
	public void onReceive(Object message) {
		println "${this} - I have this message ${message}"
		getContext().stop(getSelf())
	}
}