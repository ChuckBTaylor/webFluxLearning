package flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

public class FluxTest {

	@Test
	public void firstFlux() {
		Flux.fromIterable(Arrays.asList("A", "B", "C")).log().subscribe();
	}
	
	@Test
	public void fluxFromRange() {
		Flux.range(1, 10).log().subscribe();
	}
	
	@Test
	public void fluxFromInterval() throws InterruptedException {
		Flux.interval(Duration.ofSeconds(1)).log().take(2).subscribe();
//		Thread.sleep(5001);
	}
	
	@Test
	public void fluxFourthArgumentInSubscribe() {
		Flux.range(1, 5).log().subscribe(null, null, null, s -> s.request(3));
	}
	
	@Test
	public void fluxCustomSubscriber() {
		Flux.range(1, 10).log().subscribe(new BaseSubscriber<Integer>() {
			int elementsToProcess=3;
			int counter= 0;
			
			@Override
			public void hookOnSubscribe(Subscription subscription) {
				System.out.println("Subscribed!");
				request(elementsToProcess);
			}
			
			@Override
			public void hookOnNext(Integer value) {
				counter++;
				if(counter==elementsToProcess) {
					counter=0;
					Random r = new Random();
					elementsToProcess = r.ints(1,4).findFirst().getAsInt();
					request(elementsToProcess);
				}
			}
		});
	}
	
	@Test
	public void fluxLimitRate() {
		Flux.range(1, 10).log().limitRate(3).subscribe();
	}

}
