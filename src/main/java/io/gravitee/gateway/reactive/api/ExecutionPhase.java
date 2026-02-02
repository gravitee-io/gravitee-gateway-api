/*
 * Copyright © 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.gateway.reactive.api;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * An execution phase allows knowing in which phase of the whole request processing a given action occurs.
 * The execution phase is mainly useful during the flow and policy chain executions.
 *
 *
 * <pre>
 *                                          ____________________________________________________
 *      ______________       request       |                       GATEWAY                      |      request        ______________
 *     |              |   ------------->   |                                                    |   ------------->   |              |
 *     |  DOWNSTREAM  |                    |  ----------- REQUEST / ASYNC_REQUEST  -----------> |                    |   UPSTREAM   |
 *     |   (client)   |   <-------------   |  <---------- RESPONSE / ASYNC_RESPONSE ----------- |   <-------------   |  (endpoint)  |
 *     |______________|      response      |                                                    |      response      |______________|
 *                                         |____________________________________________________|
 * </pre>
 *
 * @author Jeoffrey HAEYAERT (jeoffrey.haeyaert at graviteesource.com)
 * @author GraviteeSource Team
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ExecutionPhase {
    /**
     * This phase represents the actions occurring from the downstream to the upstream in a sync execution context.
     *
     * <pre>
     *                                          ______________________________
     *      ______________                     |            GATEWAY           |                     ______________
     *     |              |      request       |                              |      request       |              |
     *     |  DOWNSTREAM  |   ------------->   |  -------- REQUEST ---------> |   ------------->   |   UPSTREAM   |
     *     |   (client)   |                    |                              |                    |  (endpoint)  |
     *     |______________|                    |______________________________|                    |______________|
     *
     * </pre>
     *
     * For an HTTP request, it represents the policies executed <b>before</b> the endpoint is invoked by the gateway.
     */
    REQUEST("request"),

    /**
     * This phase represents the actions occurring from the downstream to the upstream in an async execution context.
     *
     * <pre>
     *                                          ______________________________
     *      ______________                     |            GATEWAY           |                     ______________
     *     |              |      request       |                              |      request       |              |
     *     |  DOWNSTREAM  |   ------------->   |  ----- MESSAGE_REQUEST ----> |   ------------->   |   UPSTREAM   |
     *     |   (client)   |                    |                              |                    |  (endpoint)  |
     *     |______________|                    |______________________________|                    |______________|
     *
     * </pre>
     */
    MESSAGE_REQUEST("message_request"),

    /**
     * This phase represents the actions occurring from the upstream to the downstream in a message execution context.
     *
     * <pre>
     *                                          ______________________________
     *      ______________                     |            GATEWAY           |                     ______________
     *     |              |      response      |                              |      response      |              |
     *     |  DOWNSTREAM  |   <-------------   |  <------- RESPONSE --------- |   <-------------   |   UPSTREAM   |
     *     |   (client)   |                    |                              |                    |  (endpoint)  |
     *     |______________|                    |______________________________|                    |______________|
     *
     * </pre>
     *
     * For an HTTP request, it represents the policies executed <b>after</b> the endpoint has been invoked by the gateway.
     */
    RESPONSE("response"),

    /**
     * This phase represents the actions occurring from the upstream to the downstream in a message execution context.
     *
     * <pre>
     *                                          ______________________________
     *      ______________                     |            GATEWAY           |                     ______________
     *     |              |      response      |                              |      response      |              |
     *     |  DOWNSTREAM  |   <-------------   |  <---- MESSAGE_RESPONSE----- |   <-------------   |   UPSTREAM   |
     *     |   (client)   |                    |                              |                    |  (endpoint)  |
     *     |______________|                    |______________________________|                    |______________|
     *
     * </pre>
     */
    MESSAGE_RESPONSE("message_response"),

    /**
     * This phase represents the actions occurring when a client connects to the entrypoint, before any authentication or message flow.
     * This phase is only applicable to Native Kafka APIs.
     *
     * <pre>
     *                                          ______________________________
     *      ______________                     |            GATEWAY           |
     *     |              |    connection      |                              |
     *     |  DOWNSTREAM  |   ------------->   |  --- ENTRYPOINT_CONNECT ---> |
     *     |   (client)   |                    |                              |
     *     |______________|                    |______________________________|
     *
     * </pre>
     */
    ENTRYPOINT_CONNECT("entrypoint_connect");

    private final String label;

    @Override
    public String toString() {
        return label;
    }
}
