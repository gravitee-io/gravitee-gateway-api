# [2.1.0-alpha.19](https://github.com/gravitee-io/gravitee-gateway-api/compare/2.1.0-alpha.18...2.1.0-alpha.19) (2023-03-13)


### Features

* add subscription configuration ([878ceeb](https://github.com/gravitee-io/gravitee-gateway-api/commit/878ceeba461ffb80e69af077219719402c415189))

# [2.1.0-alpha.18](https://github.com/gravitee-io/gravitee-gateway-api/compare/2.1.0-alpha.17...2.1.0-alpha.18) (2023-03-13)


### Bug Fixes

* rename ApiType values ([59e3f50](https://github.com/gravitee-io/gravitee-gateway-api/commit/59e3f50815cca27c2608f3419a04f2a567fe767a))

# [2.1.0-alpha.17](https://github.com/gravitee-io/gravitee-gateway-api/compare/2.1.0-alpha.16...2.1.0-alpha.17) (2023-03-09)


### Bug Fixes

* don't emit stop message if not required ([d3d3ed6](https://github.com/gravitee-io/gravitee-gateway-api/commit/d3d3ed672d6252dd6cc2b9fbc9449e338c8eb720))

# [2.1.0-alpha.16](https://github.com/gravitee-io/gravitee-gateway-api/compare/2.1.0-alpha.15...2.1.0-alpha.16) (2023-03-09)


### Reverts

* Revert "chore: rename 'jupiter' package in 'reactive'" ([6096a74](https://github.com/gravitee-io/gravitee-gateway-api/commit/6096a74aef473f8124804d78d696af05cf1d3bdc))

# [2.1.0-alpha.15](https://github.com/gravitee-io/gravitee-gateway-api/compare/2.1.0-alpha.14...2.1.0-alpha.15) (2023-03-03)


### Bug Fixes

* default header implementation ([dc59f8f](https://github.com/gravitee-io/gravitee-gateway-api/commit/dc59f8f944b66670704870c487bf8029defd951f))
* rename reject to close on websocket ([3d71ca8](https://github.com/gravitee-io/gravitee-gateway-api/commit/3d71ca8ff1544ba459f3029a42ce15fe87c1dd5c))

# [2.1.0-alpha.14](https://github.com/gravitee-io/gravitee-gateway-api/compare/2.1.0-alpha.13...2.1.0-alpha.14) (2023-03-01)


### Features

* introduce api service concept ([2b3a3c0](https://github.com/gravitee-io/gravitee-gateway-api/commit/2b3a3c089bd78dde34d7080f3a5ddef6a8a2f4dc))

# [2.1.0-alpha.13](https://github.com/gravitee-io/gravitee-gateway-api/compare/2.1.0-alpha.12...2.1.0-alpha.13) (2023-02-23)


### Features

* enhance DefaultMessage builder to manage sourceTimestamp ([45831bb](https://github.com/gravitee-io/gravitee-gateway-api/commit/45831bb0aa07d0da6d2c7eb6baf8ea9b14663d5c))

# [2.1.0-alpha.12](https://github.com/gravitee-io/gravitee-gateway-api/compare/2.1.0-alpha.11...2.1.0-alpha.12) (2023-02-15)


### Bug Fixes

* add `active` field ([12799a3](https://github.com/gravitee-io/gravitee-gateway-api/commit/12799a3e06af073f3d9fbba0f92110eeaf00a09e))

# [2.1.0-alpha.11](https://github.com/gravitee-io/gravitee-gateway-api/compare/2.1.0-alpha.10...2.1.0-alpha.11) (2023-02-02)


### Bug Fixes

* bump reporter api ([77ef43b](https://github.com/gravitee-io/gravitee-gateway-api/commit/77ef43b487afb6bb6536fb95ad49fc7b380b58fe))

# [2.1.0-alpha.10](https://github.com/gravitee-io/gravitee-gateway-api/compare/2.1.0-alpha.9...2.1.0-alpha.10) (2023-02-01)


### Features

* add new internal attribute to store apireactor ([1768c64](https://github.com/gravitee-io/gravitee-gateway-api/commit/1768c643ed2b3bca09e5fd260419841d8fcd0323))

# [2.1.0-alpha.9](https://github.com/gravitee-io/gravitee-gateway-api/compare/2.1.0-alpha.8...2.1.0-alpha.9) (2023-01-30)


### Bug Fixes

* revert change on api type ([e80f04e](https://github.com/gravitee-io/gravitee-gateway-api/commit/e80f04e2f0757cd491214748928ab4e0f969c374))

# [2.1.0-alpha.8](https://github.com/gravitee-io/gravitee-gateway-api/compare/2.1.0-alpha.7...2.1.0-alpha.8) (2023-01-24)


### Features

* add supportedListenerType to EntrypointConnectorFactory interface ([c094315](https://github.com/gravitee-io/gravitee-gateway-api/commit/c094315d0eaad6bc9c70d7e43715d72472cecd6c))

# [2.1.0-alpha.7](https://github.com/gravitee-io/gravitee-gateway-api/compare/2.1.0-alpha.6...2.1.0-alpha.7) (2023-01-16)


### Features

* add correlation id and timestamp on message ([765b625](https://github.com/gravitee-io/gravitee-gateway-api/commit/765b625a1be80e6fdf3f09e486d2161f8b933675))

# [2.1.0-alpha.6](https://github.com/gravitee-io/gravitee-gateway-api/compare/2.1.0-alpha.5...2.1.0-alpha.6) (2023-01-13)


### Features

* add http method setter in HttpRequest ([d815bbb](https://github.com/gravitee-io/gravitee-gateway-api/commit/d815bbbadc4626c1f71638a5c5d232bcb8a8d3c3))

# [2.1.0-alpha.5](https://github.com/gravitee-io/gravitee-gateway-api/compare/2.1.0-alpha.4...2.1.0-alpha.5) (2023-01-12)


### Features

* add FAILURE consumer status on Subscription ([f0458f2](https://github.com/gravitee-io/gravitee-gateway-api/commit/f0458f2cc644c1a37132937085140cd54447733b))
* add saving and dispatching capabilities to SubscriptionService ([1f484c2](https://github.com/gravitee-io/gravitee-gateway-api/commit/1f484c2f79c6c4b6bfec0d40c29f90b78177ff47))
* provide isStatusXXX methods on GenericResponse ([8180324](https://github.com/gravitee-io/gravitee-gateway-api/commit/81803245aa5d5f16513d83f72a1af8962574457f))

# [2.1.0-alpha.5](https://github.com/gravitee-io/gravitee-gateway-api/compare/2.1.0-alpha.4...2.1.0-alpha.5) (2023-01-12)


### Features

* add FAILURE consumer status on Subscription ([f0458f2](https://github.com/gravitee-io/gravitee-gateway-api/commit/f0458f2cc644c1a37132937085140cd54447733b))
* add saving and dispatching capabilities to SubscriptionService ([1f484c2](https://github.com/gravitee-io/gravitee-gateway-api/commit/1f484c2f79c6c4b6bfec0d40c29f90b78177ff47))
* provide isStatusXXX methods on GenericResponse ([8180324](https://github.com/gravitee-io/gravitee-gateway-api/commit/81803245aa5d5f16513d83f72a1af8962574457f))

# [2.1.0-alpha.4](https://github.com/gravitee-io/gravitee-gateway-api/compare/2.1.0-alpha.3...2.1.0-alpha.4) (2022-12-21)


### Features

* add exception dedicated to message processing failures ([a891e3f](https://github.com/gravitee-io/gravitee-gateway-api/commit/a891e3f4c9796e25095b10ef05b14c8e0b608834))

# [2.1.0-alpha.3](https://github.com/gravitee-io/gravitee-gateway-api/compare/2.1.0-alpha.2...2.1.0-alpha.3) (2022-12-16)


### Features

* method to set the content-length header on http request ([1281eb2](https://github.com/gravitee-io/gravitee-gateway-api/commit/1281eb26ba0811e5a0fc875592c806484f3d88cf))

# [2.1.0-alpha.2](https://github.com/gravitee-io/gravitee-gateway-api/compare/2.1.0-alpha.1...2.1.0-alpha.2) (2022-12-14)


### Features

* method to set the content-length header on http response ([3a0f153](https://github.com/gravitee-io/gravitee-gateway-api/commit/3a0f153045b7ed3111129d0cc00a8bd0be37e2f6))

# [2.1.0-alpha.1](https://github.com/gravitee-io/gravitee-gateway-api/compare/2.0.0...2.1.0-alpha.1) (2022-12-12)


### Features

* introduce dlq service concept ([6a6d5ce](https://github.com/gravitee-io/gravitee-gateway-api/commit/6a6d5ce682296c32741db42d1ce3dfeeb4ba618a))

## [2.0.1](https://github.com/gravitee-io/gravitee-gateway-api/compare/2.0.0...2.0.1) (2023-02-07)


### Bug Fixes

* add `active` field ([12799a3](https://github.com/gravitee-io/gravitee-gateway-api/commit/12799a3e06af073f3d9fbba0f92110eeaf00a09e))

# [2.0.0](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.47.1...2.0.0) (2022-12-09)


### Bug Fixes

* allow interrupting body or messages flow ([856caf0](https://github.com/gravitee-io/gravitee-gateway-api/commit/856caf09ea884999924b07924ae7b207221f3f8b))
* check if api requires a deployment before redeploying every objects related ([d11bc38](https://github.com/gravitee-io/gravitee-gateway-api/commit/d11bc38e49a3abb4ec83f6bd3e3287e4f4e18c65))
* distinguish host from  original host on request ([9e87dfb](https://github.com/gravitee-io/gravitee-gateway-api/commit/9e87dfbeb50ce95c120ce0a9cf481abe0db1a972))
* missing release ([ec490a2](https://github.com/gravitee-io/gravitee-gateway-api/commit/ec490a2b9d824c10316223a573a2e8b700153d52))


### chore

* bump to rxJava3 ([8b03864](https://github.com/gravitee-io/gravitee-gateway-api/commit/8b038648cf8588060a818ba2649e1e3df481c8d4))


### Features

* add client identifier on request ([ee97c7e](https://github.com/gravitee-io/gravitee-gateway-api/commit/ee97c7ee216bccc03a9190718ce5054b8abd6056))
* add deployment context to connector factory ([f0c9bbb](https://github.com/gravitee-io/gravitee-gateway-api/commit/f0c9bbbd04c7a5d2cbf3a0bd0965a33c9d7bc899))
* add deployment context to connector factory ([1cfe4dd](https://github.com/gravitee-io/gravitee-gateway-api/commit/1cfe4ddd10e52a3af2809e86f1272482696794c4))
* add entrypoint subscription configuration capabilities ([7625c6a](https://github.com/gravitee-io/gravitee-gateway-api/commit/7625c6a9856eea50df8e188deed37647baeb4ec5))
* add message processor chain ([cefcdfa](https://github.com/gravitee-io/gravitee-gateway-api/commit/cefcdfa8b9d893915fccd583afbf3948023bd8d2))
* add processor and error on async api ([4323afc](https://github.com/gravitee-io/gravitee-gateway-api/commit/4323afc339ac3d933b06801b2324dfba165de373))
* add subscription.equals using lombok ([aefbc82](https://github.com/gravitee-io/gravitee-gateway-api/commit/aefbc8276f8928fbcd16c00bf4b9a19a1b70952c))
* add support for condition at message level ([cc59aeb](https://github.com/gravitee-io/gravitee-gateway-api/commit/cc59aeb21355f765764318a1830e05dc03d8a826))
* allow access resolved subscription from a EL ([6767655](https://github.com/gravitee-io/gravitee-gateway-api/commit/6767655df9d36a01e28dfa97d82ba52ddcf710b4))
* ConnectorHelper handles reading entrypoint configuration ([b7781f2](https://github.com/gravitee-io/gravitee-gateway-api/commit/b7781f2ebcba1b89f0930b31e1fee8def68bcad8))
* handle stop on entrypoint connector ([de89dde](https://github.com/gravitee-io/gravitee-gateway-api/commit/de89dde265810a88d6cfb7f0a7968058c45e5d72))
* implement QoS for event-native ([f031839](https://github.com/gravitee-io/gravitee-gateway-api/commit/f0318396d7f94524b65d4d1924425c6c3326db04))
* manage subscription consumer status ([b17b498](https://github.com/gravitee-io/gravitee-gateway-api/commit/b17b498f09d637b56f7c0876541c9756a68ab49f))
* **v4:** add identifier on Connector ([9d9f5ba](https://github.com/gravitee-io/gravitee-gateway-api/commit/9d9f5ba2ee67b497ea5bf0673b665a641330735c))


### BREAKING CHANGES

* rxJava3 required

# [2.0.0-alpha.10](https://github.com/gravitee-io/gravitee-gateway-api/compare/2.0.0-alpha.9...2.0.0-alpha.10) (2022-12-01)


### Bug Fixes

* check if api requires a deployment before redeploying every objects related ([d11bc38](https://github.com/gravitee-io/gravitee-gateway-api/commit/d11bc38e49a3abb4ec83f6bd3e3287e4f4e18c65))

# [2.0.0-alpha.9](https://github.com/gravitee-io/gravitee-gateway-api/compare/2.0.0-alpha.8...2.0.0-alpha.9) (2022-11-25)


### Features

* add subscription.equals using lombok ([aefbc82](https://github.com/gravitee-io/gravitee-gateway-api/commit/aefbc8276f8928fbcd16c00bf4b9a19a1b70952c))
* manage subscription consumer status ([b17b498](https://github.com/gravitee-io/gravitee-gateway-api/commit/b17b498f09d637b56f7c0876541c9756a68ab49f))

# [2.0.0-alpha.8](https://github.com/gravitee-io/gravitee-gateway-api/compare/2.0.0-alpha.7...2.0.0-alpha.8) (2022-11-24)


### Bug Fixes

* missing release ([ec490a2](https://github.com/gravitee-io/gravitee-gateway-api/commit/ec490a2b9d824c10316223a573a2e8b700153d52))

# [2.0.0-alpha.7](https://github.com/gravitee-io/gravitee-gateway-api/compare/2.0.0-alpha.6...2.0.0-alpha.7) (2022-11-24)


### Features

* add client identifier on request ([ee97c7e](https://github.com/gravitee-io/gravitee-gateway-api/commit/ee97c7ee216bccc03a9190718ce5054b8abd6056))

# [2.0.0-alpha.6](https://github.com/gravitee-io/gravitee-gateway-api/compare/2.0.0-alpha.5...2.0.0-alpha.6) (2022-11-23)


### Bug Fixes

* distinguish host from  original host on request ([9e87dfb](https://github.com/gravitee-io/gravitee-gateway-api/commit/9e87dfbeb50ce95c120ce0a9cf481abe0db1a972))

# [2.0.0-alpha.5](https://github.com/gravitee-io/gravitee-gateway-api/compare/2.0.0-alpha.4...2.0.0-alpha.5) (2022-11-21)


### Features

* add deployment context to connector factory ([f0c9bbb](https://github.com/gravitee-io/gravitee-gateway-api/commit/f0c9bbbd04c7a5d2cbf3a0bd0965a33c9d7bc899))
* allow access resolved subscription from a EL ([6767655](https://github.com/gravitee-io/gravitee-gateway-api/commit/6767655df9d36a01e28dfa97d82ba52ddcf710b4))

# [2.0.0-alpha.4](https://github.com/gravitee-io/gravitee-gateway-api/compare/2.0.0-alpha.3...2.0.0-alpha.4) (2022-11-18)


### Features

* add deployment context to connector factory ([1cfe4dd](https://github.com/gravitee-io/gravitee-gateway-api/commit/1cfe4ddd10e52a3af2809e86f1272482696794c4))

# [2.0.0-alpha.3](https://github.com/gravitee-io/gravitee-gateway-api/compare/2.0.0-alpha.2...2.0.0-alpha.3) (2022-10-31)


### Features

* ConnectorHelper handles reading entrypoint configuration ([b7781f2](https://github.com/gravitee-io/gravitee-gateway-api/commit/b7781f2ebcba1b89f0930b31e1fee8def68bcad8))

# [2.0.0-alpha.2](https://github.com/gravitee-io/gravitee-gateway-api/compare/2.0.0-alpha.1...2.0.0-alpha.2) (2022-10-26)


### Features

* add entrypoint subscription configuration capabilities ([7625c6a](https://github.com/gravitee-io/gravitee-gateway-api/commit/7625c6a9856eea50df8e188deed37647baeb4ec5))

# [2.0.0-alpha.1](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.48.0-alpha.4...2.0.0-alpha.1) (2022-10-18)


### chore

* bump to rxJava3 ([8b03864](https://github.com/gravitee-io/gravitee-gateway-api/commit/8b038648cf8588060a818ba2649e1e3df481c8d4))


### BREAKING CHANGES

* rxJava3 required

# [1.48.0-alpha.4](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.48.0-alpha.3...1.48.0-alpha.4) (2022-10-18)


### Features

* implement QoS for event-native ([f031839](https://github.com/gravitee-io/gravitee-gateway-api/commit/f0318396d7f94524b65d4d1924425c6c3326db04))

# [1.48.0-alpha.3](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.48.0-alpha.2...1.48.0-alpha.3) (2022-10-17)


### Features

* add support for condition at message level ([cc59aeb](https://github.com/gravitee-io/gravitee-gateway-api/commit/cc59aeb21355f765764318a1830e05dc03d8a826))

# [1.48.0-alpha.2](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.48.0-alpha.1...1.48.0-alpha.2) (2022-10-13)


### Bug Fixes

* allow interrupting body or messages flow ([856caf0](https://github.com/gravitee-io/gravitee-gateway-api/commit/856caf09ea884999924b07924ae7b207221f3f8b))

# [1.48.0-alpha.1](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.47.1...1.48.0-alpha.1) (2022-10-10)


### Features

* add message processor chain ([cefcdfa](https://github.com/gravitee-io/gravitee-gateway-api/commit/cefcdfa8b9d893915fccd583afbf3948023bd8d2))
* add processor and error on async api ([4323afc](https://github.com/gravitee-io/gravitee-gateway-api/commit/4323afc339ac3d933b06801b2324dfba165de373))
* handle stop on entrypoint connector ([de89dde](https://github.com/gravitee-io/gravitee-gateway-api/commit/de89dde265810a88d6cfb7f0a7968058c45e5d72))
* **v4:** add identifier on Connector ([9d9f5ba](https://github.com/gravitee-io/gravitee-gateway-api/commit/9d9f5ba2ee67b497ea5bf0673b665a641330735c))

## [1.47.1](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.47.0...1.47.1) (2022-09-28)


### Bug Fixes

* add ATTR_INTERNAL_SECURITY_TOKEN constant to handle apikey vs keyless plan selection ([7415451](https://github.com/gravitee-io/gravitee-gateway-api/commit/74154511e5ede072f4d21b83870a307014878e06))

## [1.44.2](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.44.1...1.44.2) (2023-01-30)


### Bug Fixes

* add `active` field ([12799a3](https://github.com/gravitee-io/gravitee-gateway-api/commit/12799a3e06af073f3d9fbba0f92110eeaf00a09e))

## [1.44.1](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.44.0...1.44.1) (2022-09-28)


### Bug Fixes

* add ATTR_INTERNAL_SECURITY_TOKEN constant to handle apikey vs keyless plan selection ([7415451](https://github.com/gravitee-io/gravitee-gateway-api/commit/74154511e5ede072f4d21b83870a307014878e06))

# [1.47.0](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.46.2...1.47.0) (2022-09-20)


### Features

* add dedicated WebSocket interface for jupiter ([d92420d](https://github.com/gravitee-io/gravitee-gateway-api/commit/d92420d16190f94a2eb485ef49ef257f3e0ba341)), closes [gravitee-io/issues#8253](https://github.com/gravitee-io/issues/issues/8253)

## [1.46.2](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.46.1...1.46.2) (2022-09-12)


### Bug Fixes

* add missing attributes to handle properly SME ([d34a27a](https://github.com/gravitee-io/gravitee-gateway-api/commit/d34a27a4c52de5c7c076b75e601c407e62ed1c47)), closes [gravitee-io/issues#8037](https://github.com/gravitee-io/issues/issues/8037)

## [1.46.1](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.46.0...1.46.1) (2022-09-09)


### Bug Fixes

* message level policy ([7aeada5](https://github.com/gravitee-io/gravitee-gateway-api/commit/7aeada5e95eae49375459cdbd16006a6ef614d7b)), closes [graviteeio/issues#8403](https://github.com/graviteeio/issues/issues/8403)

# [1.46.0](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.45.0...1.46.0) (2022-09-09)


### Features

* **message:** add identifier on message ([9445ced](https://github.com/gravitee-io/gravitee-gateway-api/commit/9445ced71da75eeb31e3c89a8b37630f3480c35d))

# [1.45.0](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.44.0...1.45.0) (2022-09-06)


### Bug Fixes

* add FactoryHelper to hold common behaviour across connector factory ([16021a3](https://github.com/gravitee-io/gravitee-gateway-api/commit/16021a3337aeb38a282de12ffc391ca74557ec3f))
* fix after review ([562eb0e](https://github.com/gravitee-io/gravitee-gateway-api/commit/562eb0eaab0ce22e2b469261574d9cb6356c5829))


### Features

* add attributes on message ([042cf3c](https://github.com/gravitee-io/gravitee-gateway-api/commit/042cf3c7fc9ed70404fd8f2765667985291e7227))

# [1.44.0](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.43.0...1.44.0) (2022-09-02)


### Features

* improve execution context structure ([9589294](https://github.com/gravitee-io/gravitee-gateway-api/commit/95892941ffd6964ab5ba096a44e9fa8856c5d404)), closes [gravitee-io/issues#8386](https://github.com/gravitee-io/issues/issues/8386)
* improve Message structure ([32baa21](https://github.com/gravitee-io/gravitee-gateway-api/commit/32baa2184048c9f98029437705fcd14909ff1d18))

# [1.43.0](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.42.0...1.43.0) (2022-09-01)


### Features

* Initial support for webhook subscription ([bcd8064](https://github.com/gravitee-io/gravitee-gateway-api/commit/bcd8064a0fa86b84f354f9c8b36652dc5cca7cda))

# [1.42.0](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.41.1...1.42.0) (2022-08-30)


### Features

* **kafka:** add kafka endpoint ([e3bd01e](https://github.com/gravitee-io/gravitee-gateway-api/commit/e3bd01ed889f8d8b458514871ecb039582d6d25c))

## [1.41.1](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.41.0...1.41.1) (2022-08-30)


### Bug Fixes

* **request:** move body() from Message to Http ([87ed3f7](https://github.com/gravitee-io/gravitee-gateway-api/commit/87ed3f7ec3afc17bc64c594870fb511cf24bdeac))

# [1.41.0](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.40.0...1.41.0) (2022-08-30)


### Features

* **message:** access body from MessageRequest ([e70f903](https://github.com/gravitee-io/gravitee-gateway-api/commit/e70f90368fb20b080fde577ffe9170c211f0ff39))

# [1.40.0](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.39.0...1.40.0) (2022-08-17)


### Features

* EndpointConnectorFactory deserializes endpoint plugin configuration ([b6f28de](https://github.com/gravitee-io/gravitee-gateway-api/commit/b6f28de9cc9e9c366a5d0c2c16d37c06a51748d9))

# [1.39.0](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.38.0...1.39.0) (2022-08-16)


### Features

* introduce generic SecurityToken to handle ApiKey and clientId tokens ([1ff6a9b](https://github.com/gravitee-io/gravitee-gateway-api/commit/1ff6a9b3e6d7bd9ea4242ccb3886d4d8195701b4))

# [1.38.0](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.37.3...1.38.0) (2022-08-08)


### Features

* **entrypoint:** add plugin framework ([278c374](https://github.com/gravitee-io/gravitee-gateway-api/commit/278c37489d7b77738590c2d7798ec0f7f7e11b9c))
* **v4:** add resource to deal with plan, and state ([3ff26e9](https://github.com/gravitee-io/gravitee-gateway-api/commit/3ff26e917b21b63f0fad79d74651c0bd5ecfcd11))

## [1.37.3](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.37.2...1.37.3) (2022-07-29)


### Bug Fixes

* SubscriptionService searches a subscription from a given API, client_id, and plan ([a3e82d6](https://github.com/gravitee-io/gravitee-gateway-api/commit/a3e82d630fa2d4d3aac38f62c64765c47fec2f9d))

## [1.37.2](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.37.1...1.37.2) (2022-07-26)


### Bug Fixes

* properly name onChunks method ([acf340f](https://github.com/gravitee-io/gravitee-gateway-api/commit/acf340f679050ef7cf31e5e71bd52db88604d524)), closes [gravitee-io/issues#8030](https://github.com/gravitee-io/issues/issues/8030)

## [1.37.1](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.37.0...1.37.1) (2022-07-26)


### Bug Fixes

* return null instead of empty list when getting an undefined header ([91e2fb4](https://github.com/gravitee-io/gravitee-gateway-api/commit/91e2fb4753d1eefca6306934d0b8573786be9695))

## [1.32.4](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.32.3...1.32.4) (2022-07-26)


### Bug Fixes

* return null instead of empty list when getting an undefined header ([91e2fb4](https://github.com/gravitee-io/gravitee-gateway-api/commit/91e2fb4753d1eefca6306934d0b8573786be9695))

# [1.37.0](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.36.1...1.37.0) (2022-07-07)


### Features

* **jupiter:** add websocket support ([233f8ad](https://github.com/gravitee-io/gravitee-gateway-api/commit/233f8ad5159fc6d8cd7cb6a77c3270e9cd30222c))

## [1.36.1](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.36.0...1.36.1) (2022-07-04)


### Bug Fixes

* restore HTTP headers backward compatibility ([100c58f](https://github.com/gravitee-io/gravitee-gateway-api/commit/100c58f333126f8f0c412f8862c109b92bf85c38))

## [1.32.3](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.32.2...1.32.3) (2022-06-24)


### Bug Fixes

* restore HTTP headers backward compatibility ([100c58f](https://github.com/gravitee-io/gravitee-gateway-api/commit/100c58f333126f8f0c412f8862c109b92bf85c38))

# [1.36.0](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.35.0...1.36.0) (2022-06-28)


### Features

* add services to manage api keys and subscriptions ([c6ff0e3](https://github.com/gravitee-io/gravitee-gateway-api/commit/c6ff0e31699345632f1a476ba246b664faeb43ed))

# [1.35.0](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.34.0...1.35.0) (2022-06-24)


### Features

* **jupiter:** implementation of debug mode ([6029b40](https://github.com/gravitee-io/gravitee-gateway-api/commit/6029b40990931f379e930d6630f9319c19ed00e8))

# [1.34.0](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.33.0...1.34.0) (2022-06-20)


### Features

* **jupiter:** request & response content support in EL ([6e7821b](https://github.com/gravitee-io/gravitee-gateway-api/commit/6e7821ba02c842f1419f969fea2d057a1d0fabd5))

# [1.33.0](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.32.2...1.33.0) (2022-06-10)


### Features

* **jupiter:** add hook mechanism to deal with tracing ([44fb045](https://github.com/gravitee-io/gravitee-gateway-api/commit/44fb045643577bfc9c6c9a2889d66d4c8d878b24))
* **jupiter:** add jupiter entities ([e118db4](https://github.com/gravitee-io/gravitee-gateway-api/commit/e118db4f4bc313e512b833334b02207ff7b393fd))
* **jupiter:** add SecurityPolicy ([528495d](https://github.com/gravitee-io/gravitee-gateway-api/commit/528495d592df50b84c452f037f0bf84b10b6ab60))
* **jupiter:** better handling of request and response body ([dcbc602](https://github.com/gravitee-io/gravitee-gateway-api/commit/dcbc602d4dc76b27ea7824301de0fb5b9cec5a2f))
* **jupiter:** handle interruption mechanism ([180dbc1](https://github.com/gravitee-io/gravitee-gateway-api/commit/180dbc1e0ec8a606ea07bcfc36af1cb28fdfb0b7))

## [1.32.2](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.32.1...1.32.2) (2022-04-27)


### Bug Fixes

* add a close handler on the request interface ([c2d962d](https://github.com/gravitee-io/gravitee-gateway-api/commit/c2d962d8a758a3d9d660cf18ec9f7768e5616b27))

## [1.31.2](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.31.1...1.31.2) (2022-04-26)


### Bug Fixes

* add a close handler on the request interface ([c2d962d](https://github.com/gravitee-io/gravitee-gateway-api/commit/c2d962d8a758a3d9d660cf18ec9f7768e5616b27))

## [1.27.4](https://github.com/gravitee-io/gravitee-gateway-api/compare/1.27.3...1.27.4) (2022-04-26)


### Bug Fixes

* add a close handler on the request interface ([8e6cdbb](https://github.com/gravitee-io/gravitee-gateway-api/commit/8e6cdbb1b25cecd50f92ffe096cda210172a293b))
