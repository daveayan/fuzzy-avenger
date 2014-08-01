package com.daveayan.fuzzyavenger.providers.akka;

import java.util.ArrayList;
import java.util.List;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;
import akka.pattern.Patterns;
import akka.util.Timeout;

import com.daveayan.fuzzyavenger.ExecutionProvider;
import com.daveayan.fuzzyavenger.Function;

@SuppressWarnings("deprecation")
public class AkkaExecutionProvider implements ExecutionProvider {
	public ActorRef shutdownCommandListener;
	public ActorSystem system;
	
	public static ExecutionProvider newInstance() {
		ExecutionProvider ep = new AkkaExecutionProvider();
		ep.initialize();
		return ep;
	}
	
	public void initialize() {
		system = ActorSystem.create("fuzzy-avenger-" + System.currentTimeMillis());
		shutdownCommandListener = system.actorOf(new Props(ActorLevel0_SystemShutdowner.class), "shutdownCommandListener");
	}
	
	@SuppressWarnings({ "unchecked" })
	public List<Object> run(final List<Object> data, List<Object> parameters, Function<?, ?> functionToApply, final int numberOfWorkers, int numberOfSecondsTimeout) {
		ActorRef actorLevel1 = system.actorOf(new Props(new UntypedActorFactory() {
			private static final long serialVersionUID = 1L;
			public UntypedActor create() {
				return new ActorLevel1(data, numberOfWorkers);
			}
		}), "ActorLevel1-" + System.currentTimeMillis());
		
		Timeout timeout = new Timeout(Duration.create(numberOfSecondsTimeout, "seconds"));
		Future<Object> future = Patterns.ask(actorLevel1, new Message_Runner_to_AL1(parameters, functionToApply), timeout);
		List<Object> results = new ArrayList<Object>();
		try {
			results = (List<Object>) Await.result(future, timeout.duration());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}
	
	public void shutdown() {
		shutdownCommandListener.tell(new Message_AL1_to_AL0(), ActorRef.noSender());
	}
}
