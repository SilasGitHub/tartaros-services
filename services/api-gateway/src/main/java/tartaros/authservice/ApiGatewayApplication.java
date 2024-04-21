package tartaros.authservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.function.*;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.setRequestHostHeader;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.stripPrefix;
import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.web.servlet.function.RequestPredicates.path;

@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Value("${auth.port}")
    private String PORT_AUTH;

    @Value("${auth.host}")
    private String HOST_AUTH;

    @Bean
    public RouterFunction<ServerResponse> routeConfig() {
        RouterFunctions.Builder routeBuilder = route("routes");

        return  routeBuilder
                .route(path("/auth/**"), http())
                .before(stripPrefix(1))
                .before(setRequestHostHeader(HOST_AUTH + ":" + PORT_AUTH))
                .filter(lb("auth-service"))
                .build()
                .andRoute(path("/activity/**"), http())
                .filter(lb("activity-service"))
                .andRoute(path("/financial/**"), http())
                .filter(lb("financial-service"))
                .andRoute(path("/**"), http())
                .filter(lb("ui-service"));
    }
}


