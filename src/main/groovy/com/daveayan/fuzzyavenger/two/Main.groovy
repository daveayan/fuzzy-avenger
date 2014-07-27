package com.daveayan.fuzzyavenger.two

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.UntypedActor
import akka.japi.Creator

class Main {
	public static void main (String[] args) {
		Main m = new Main()
		m.runit()
	}
	
	def runit() {
		println "${this} - BEGIN"
		
		def system = ActorSystem.create("com-daveayan-fuzzyavenger-two-Main");
		def masterActor = system.actorOf(Props.create(new MasterActorCreator()), "masterActor-1")
		
		masterActor.tell(new Object(), ActorRef.noSender())
		
		system.shutdown()
		println "${this} - END"
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
	}
}