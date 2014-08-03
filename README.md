fuzzy-avenger
=============

## How to use this?
The best examples can be found in this [unit test](https://github.com/daveayan/fuzzy-avenger/blob/master/src/test/groovy/com/daveayan/fuzzyavenger/AkkaExecutionProviderTest.groovy)

Examples (in groovy):

				/* My long running function here converts a appends the string with a % sign */
				def results = Lists.applyNWorkers(["1", "2", "3"], [], new LongRunningFunction(), 2)
				results == ["A%, B%, C%"]
				
				/* This one applies 2 workers to work through the list */
				
## Why was this created?
This project started off as a learning exercise. One fine weekend I wanted to learn [Akka with Java](http://doc.akka.io/docs/akka/snapshot/java.html?_ga=1.225472222.570252940.1407078201). In the process I created this github project to capture my learnings. The [folders one - five in here](https://github.com/daveayan/fuzzy-avenger/tree/master/src/test/groovy/com/daveayan/fuzzyavenger) is where that learning was captured. As I did that I realized a simple utility can be created that can be used with any traditional java code - specially unit tests or just for curious minds. This utility will process a list, apply a long running function (like making service calls) to each element in the list, capture results, aggregate them and will return the results. The results will be returned in the order - one for each element in the input list. Thus, this utility was created.

## Why would I use this?
I would recommend not to use this in production code, yet.
<li>Use this utility in your unit tests to exercise parallel processing / concurrency.
<li>For curious minds - to try out some instrumentation on your services
<li>For learning
<li>To provide me some feedback, if this is interesting, helpful and what updates can be made

