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
