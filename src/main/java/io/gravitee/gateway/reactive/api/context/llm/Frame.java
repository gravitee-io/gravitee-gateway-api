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
package io.gravitee.gateway.reactive.api.context.llm;

/**
 * A single element of an llm response flow, see {@link LlmResponse#deltas()}.
 * <p>
 * Sealed on purpose: a stream either carries generated content ({@link Turn}) or ends on a failure
 * ({@link ErrorFrame}), and a policy transforming the flow must state what it does with both. An open type, or
 * a failure signalled out of band, would let a policy pass the error through untouched without ever having
 * looked at it, which is exactly the case that matters for a guardrail.
 *
 * @author GraviteeSource Team
 */
public sealed interface Frame permits Turn, ErrorFrame {}
