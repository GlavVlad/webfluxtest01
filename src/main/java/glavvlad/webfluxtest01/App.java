package glavvlad.webfluxtest01;

import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import reactor.core.publisher.Mono;
import reactor.ipc.netty.http.server.HttpServer;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @author Vlad on 12.08.17.
 */
public class App {

	static RouterFunction getRouter() {
		HandlerFunction hello = request -> ok().body(Mono.just("Hello"), String.class);

		return route(GET("/"), hello)
				.andRoute(GET("/json"), req -> ok()
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.body(Mono.just(new Data("some data")), Data.class));
	}

	public static void main(String[] args) throws InterruptedException {
//		HandlerFunction hello = request -> ok().body(Mono.just("Hello"), String.class);
//		RouterFunction router = route(GET("/"), hello);

		RouterFunction router = getRouter();
		HttpHandler httpHandler = RouterFunctions.toHttpHandler(router);

		HttpServer
				.create("localhost", 8080)
				.newHandler(new ReactorHttpHandlerAdapter(httpHandler))
				.block();

		Thread.currentThread().join();
	}

}
