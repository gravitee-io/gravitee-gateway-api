package io.gravitee.gateway.reactive.api.connector.entrypoint.agent;

import io.gravitee.gateway.reactive.api.context.agent.AgentExecutionContext;
import io.reactivex.rxjava3.core.Completable;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface AuthenticationListener {

    default Completable onAuthenticationSuccess(AgentExecutionContext ctx, String state) {
        return null;
    }

    default Completable onAuthenticationFailed(AgentExecutionContext ctx, String state) {
        return null;
    }
}
