package com.daveayan.fuzzyavenger.three

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
		println "BEGIN - ${this}"
		
		def system = ActorSystem.create("com-daveayan-fuzzyavenger-three-Main");
		def shutdownCommandListener = system.actorOf(new Props(ActorShutdownCommandListener.class), "shutdownCommandListener")
		def masterActor = system.actorOf(Props.create(new MasterActorCreator()), "masterActor-1")
		
		masterActor.tell(new Object(), ActorRef.noSender())
		shutdownCommandListener.tell(new Object(), ActorRef.noSender())
		
		println "END - ${this}"
	}
}

class ActorShutdownCommandListener extends UntypedActor {
	public void onReceive(Object message) {
		println "BEGIN - ${this} - I have this message ${message}"
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
	}
}
