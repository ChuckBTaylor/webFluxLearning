package mono;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Mono;

public class MonoTest {

	@Test
	public void firstMono() {
		Mono.just("A").log()
		.doOnSubscribe(subscribe -> System.out.println(subscribe))
		.doOnRequest(request -> System.out.println(request))
		.doOnSuccess(complete -> System.out.println("Complete: " + complete))
		.subscribe(System.out::println);
	}
	
	@Test
	public void emptyMono() {
		Mono.empty().log().subscribe(System.out::println);
	}
	
	@Test
	public void emptyCompleteConsumerMono() {
		Mono.empty().log()
		.subscribe(System.out::println, null, () -> System.out.println("Done!"));
	}
	
	@Test
	public void simulateOnError() {
		Mono.error(new RuntimeException()).log().doOnError(re -> System.out.println("doneOnError: " + re)).subscribe();
	}
	
	@Test
	public void simulateCheckedError() {
		Mono.error(new Exception()).log().subscribe(System.out::println, e -> System.out.println("Error: " + e));
	}
	
	@Test
	public void errorOnErrorResume() {
		Mono.error(new Exception()).log().onErrorResume(e -> {
			System.out.println("Caught: "  + e);
			return Mono.just("B");
		}).subscribe();
	}

}
