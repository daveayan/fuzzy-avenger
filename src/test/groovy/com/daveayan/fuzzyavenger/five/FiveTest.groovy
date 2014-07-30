package com.daveayan.fuzzyavenger.five

import org.junit.BeforeClass
import org.junit.Test

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.UntypedActor
import akka.japi.Creator
import akka.routing.RoundRobinRouter

class FiveTest {
	
	@Test public void listening_to_system_shutdown_command() {
		Runner r = new Runner()
		r.runem()
	}
}

class Runner {
	def system_name = "com-daveayan-fuzzyavenger-five-Main"
	def system
	def runem() {
		println "BEGIN - ${this}"
		system = ActorSystem.create(system_name);
		def nrOfWorkers = 4
		
		def AL0_SystemShutdowner = system.actorOf(new Props(Actor_Level0_SystemShutdowner.class), "shutdownCommandListener-1")
		def masterActor = system.actorOf(Props.create(new MasterActorCreator(AL0_SystemShutdowner, nrOfWorkers)), "masterActor")
		
		masterActor.tell(new Main_AL1_MessageCommand(), ActorRef.noSender())
		AL0_SystemShutdowner.tell(new Object(), ActorRef.noSender())
		
		println "END - ${this}"
	}
}

class MasterActorCreator implements Creator<MasterActor> {
	def AL0_SystemShutdowner, nrOfWorkers
	public MasterActorCreator(AL0_SystemShutdowner, nrOfWorkers) {
		this.AL0_SystemShutdowner = AL0_SystemShutdowner
		this.nrOfWorkers = nrOfWorkers
	}
	
	public MasterActor create(){
		return new MasterActor(AL0_SystemShutdowner, nrOfWorkers);
	}
}

class MasterActor extends UntypedActor {
	def AL0_SystemShutdowner, nrOfWorkers, workerRouter
	public MasterActor(AL0_SystemShutdowner, nrOfWorkers) {
		this.AL0_SystemShutdowner = AL0_SystemShutdowner
		this.nrOfWorkers = nrOfWorkers
		println AL0_SystemShutdowner
		def a = new Props(ActorLevel2.class).withRouter(new RoundRobinRouter(nrOfWorkers))
		println a
		this.workerRouter = this.getContext().actorOf(a, "WR");
		println workerRouter
	}
	public void onReceive(Object message) {
		println "BEGIN - ${this} - I have this message ${message}"
		println "END - ${this} - I have this message ${message}"
		getContext().stop(getSelf())
	}
}

class ActorLevel2 extends UntypedActor {
	public void onReceive(Object message) {
		println "BEGIN - ${this} - I have this message --- ${message}"
		println "END - ${this} - I have this message ${message}"
	}
}

class ActorLevel1 extends UntypedActor {
	def AL0_SystemShutdowner
	def router, nrOfWorkers, workCompleted = 0
	
	public ActorLevel1(AL0_SystemShutdowner, nrOfWorkers) {
		this.AL0_SystemShutdowner = AL0_SystemShutdowner
		this.nrOfWorkers = nrOfWorkers
		this.router = this.getContext().actorOf(new Props(Actor_Level2.class).withRouter(new RoundRobinRouter(nrOfWorkers)), "AL2-Router");
	}
	
	public void onReceive(Object message) {
		println "BEGIN - ${this} - I have this message --- ${message}"
		if (message instanceof Main_AL1_MessageCommand) {
			for (i in 0 .. nrOfWorkers * 2.2) {
				router.tell(new AL1_Al2_MessageCommand(), getSelf());
			}
		} else if (message instanceof AL2_AL1_MessageResult) {
			workCompleted ++
			if (workCompleted == nrOfWorkers * 2.2) {
				AL0_SystemShutdowner.tell(new Object(), ActorRef.noSender())
				getContext().stop(getSelf())
			}
		}
		println "END - ${this} - I have this message ${message}"
	}
	
}

class ActorLevel1Creator implements Creator<ActorLevel1> {
	def AL0_SystemShutdowner, nrOfWorkers
	public ActorLevel1 create(){
		return new ActorLevel1(AL0_SystemShutdowner, nrOfWorkers);
	}
	
	public ActorLevel1Creator(AL0_SystemShutdowner, nrOfWorkers) {
		this.AL0_SystemShutdowner = AL0_SystemShutdowner
		this.nrOfWorkers = nrOfWorkers
	}
	
	public ActorLevel1Creator() {
		this.nrOfWorkers = 3
	}
}

class Actor_Level0_SystemShutdowner extends UntypedActor {
	public void onReceive(Object message) {
		println "BEGIN - ${this} - I have this message ${message}"
		getContext().stop(getSelf())
		getContext().system().shutdown()
		println "END - ${this} - I have this message ${message}"
	}
}

class Main_AL1_MessageCommand {

}

class AL1_Al2_MessageCommand {
	
}

class AL2_AL1_MessageResult {

}