package operator;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class OperatorTest {
	
	@Test
	public void operatorTestMap() {
		Flux.range(1, 10).log().map(i -> i * 10).subscribe(System.out::println);
	}
	
	@Test
	public void operatorTestFlatMap() {
		Flux.range(1, 10).log().flatMap(i -> Flux.range(i*10, 3).log()).subscribe(System.out::println);
	}
	
	@Test
	public void flatMapMany() {
		Mono.just(6).flatMapMany(it -> Flux.range(1, it)).subscribe(System.out::println);
	}
	
	@Test
	public void concatenateFlux() throws InterruptedException {
		Flux<Integer> oneToFive = Flux.range(1, 5).log().delayElements(Duration.ofMillis(400));
		Flux<Integer> sixToTen = Flux.range(6, 5).delayElements(Duration.ofMillis(200)); 
		
		Flux.concat(oneToFive, sixToTen).subscribe(System.out::println);
		
		Thread.sleep(4000);
	}
	
	@Test
	public void mergeFlux() throws InterruptedException {
		Flux<Integer> oneToFive = Flux.range(1, 5).log().delayElements(Duration.ofMillis(400));
		Flux<Integer> sixToTen = Flux.range(6, 5).delayElements(Duration.ofMillis(200)); 
		
		Flux.merge(oneToFive, sixToTen).subscribe(System.out::println);
		
		Thread.sleep(4000);
	}
	
	@Test
	public void zipFlux() throws InterruptedException {
		Flux<Integer> oneToFive = Flux.range(1, 5).log().delayElements(Duration.ofMillis(400));
		Flux<Integer> sixToTen = Flux.range(6, 10).delayElements(Duration.ofMillis(200)); 
		
		Flux.zip(oneToFive, sixToTen, (item1, item2) -> item1 + ", " + item2).subscribe(System.out::println);
		
		Thread.sleep(4000);
	}

}
