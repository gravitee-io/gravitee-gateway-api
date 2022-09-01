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
