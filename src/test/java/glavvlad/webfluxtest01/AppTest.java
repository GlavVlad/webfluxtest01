package glavvlad.webfluxtest01;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * @author Vlad on 12.08.17.
 */
public class AppTest {

	private final WebTestClient webTestClient =
			WebTestClient
					.bindToRouterFunction(App.getRouter())
					.build();

	@Test
	public void indexPageWhenRequestedSayHello() {
		webTestClient.get().uri("/").exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(String.class)
				.isEqualTo("Hello");
	}

	@Test
	public void jsonPageWhenRequestedReturnJson() {
		webTestClient.get().uri("/json").exchange()
				.expectStatus().is2xxSuccessful()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody(Data.class)
				.isEqualTo(new Data("some data"));
	}
}