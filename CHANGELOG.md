# [](https://github.com/toolisticon/keycloak-gdpr-module/compare/v1.0.0-beta...v1.0.0) (2021-11-03)


### Bug Fixes

* upgrade org.junit.jupiter:junit-jupiter-api from 5.7.2 to 5.8.0 ([58b98bc](https://github.com/toolisticon/keycloak-gdpr-module/commit/58b98bc1593f5882f6e6cd1369dbb4ed699bd19e))
* upgrade org.junit.jupiter:junit-jupiter-api from 5.8.0 to 5.8.1 ([bcfcee0](https://github.com/toolisticon/keycloak-gdpr-module/commit/bcfcee0ec91012d195a3f5d6647b9637fc077ddb))
* upgrade org.junit.jupiter:junit-jupiter-engine from 5.7.1 to 5.7.2 ([88cb1d7](https://github.com/toolisticon/keycloak-gdpr-module/commit/88cb1d7daa01e7ed20b11612430338a18b8f1c6f))
* upgrade org.mockito:mockito-junit-jupiter from 3.11.2 to 3.12.4 ([1a15f30](https://github.com/toolisticon/keycloak-gdpr-module/commit/1a15f30fb6f60ccbb88b2345ca0e973d36a38c32))
* upgrade org.projectlombok:lombok from 1.18.20 to 1.18.22 ([1893b28](https://github.com/toolisticon/keycloak-gdpr-module/commit/1893b28eb5af706971da84d4c2ba37aad9c1c27d))
* upgrade org.slf4j:slf4j-api from 1.7.30 to 1.7.32 ([e3d18b2](https://github.com/toolisticon/keycloak-gdpr-module/commit/e3d18b2f60c1d3e8f8815053d087c2456d99c604))
* **User:** Corrected error handling for not found user ([28cba87](https://github.com/toolisticon/keycloak-gdpr-module/commit/28cba87a38fa1911e7fd66961928e41da2382df6))


### Features

* **api:** add cause to DecryptionFailedException ([de398ee](https://github.com/toolisticon/keycloak-gdpr-module/commit/de398eeb469cf5103e0d38ab340db1aaeac1bc9c))
* **API:** Adding batch endpoint and improve sensitive data handling ([6421cfb](https://github.com/toolisticon/keycloak-gdpr-module/commit/6421cfb12fc23b527b72944d975d6fbd2ce94cf1))
* **Crypto:** Adjust test case for IV ([2750174](https://github.com/toolisticon/keycloak-gdpr-module/commit/27501740678cc699bc9ded1fdd69076256b9a88a))
* **crypto:** Generate random IV for encryption ([5e5309d](https://github.com/toolisticon/keycloak-gdpr-module/commit/5e5309d8f3e02d0c7ae8b4d2cc0dc7978c4d6707))



# [1.0.0-beta](https://github.com/toolisticon/keycloak-gdpr-module/compare/7d45b68584125501dc25f646b82e8bec321a3e50...v1.0.0-beta) (2021-07-20)


### Bug Fixes

* **Build:** Adding SPI copy ([713fb21](https://github.com/toolisticon/keycloak-gdpr-module/commit/713fb219399b532a00c4194cf58e2a656cbd41fe))
* **Runtime:** Make encryption work in non SUN-JDK and verify it ([acd6616](https://github.com/toolisticon/keycloak-gdpr-module/commit/acd6616ee72cdcfba86edb7d42e3ef4095d14531))
* **Tests:** Using placeholders for crypt performance tests ([ef07f7b](https://github.com/toolisticon/keycloak-gdpr-module/commit/ef07f7b9c8d8d897618eaddc85fd0469cfae047c)), closes [#4](https://github.com/toolisticon/keycloak-gdpr-module/issues/4)


### Features

* **API:** Adding dummy method for REST call ([7d45b68](https://github.com/toolisticon/keycloak-gdpr-module/commit/7d45b68584125501dc25f646b82e8bec321a3e50))
* **api:** setup encrypt and decrypt endpoints ([06413f6](https://github.com/toolisticon/keycloak-gdpr-module/commit/06413f6e0d8d858b368a4f5af02f6e66c9fe6aa1))
* **Auth:** Securing GDPR endpoint ([edcb389](https://github.com/toolisticon/keycloak-gdpr-module/commit/edcb389460e427bf9a54fa8f9d3f6f0fbb55326d))
* **encryption:** use AES-GCM encryption ([a5b0e72](https://github.com/toolisticon/keycloak-gdpr-module/commit/a5b0e725ce734d72e4b3b6a0ee75ee3c1435db54))
* **logging:** log key creation ([64f3e93](https://github.com/toolisticon/keycloak-gdpr-module/commit/64f3e93e8d5412a6ca5f0224281921d492bec179))
* **Persistence:** Adding keys to user ([e3475e3](https://github.com/toolisticon/keycloak-gdpr-module/commit/e3475e353174298de4e7b9ab1d73c8a6384ecba1)), closes [#6](https://github.com/toolisticon/keycloak-gdpr-module/issues/6)
* **Security:** Adding token check to endpoint ([7635d2a](https://github.com/toolisticon/keycloak-gdpr-module/commit/7635d2ae53746a1fabecef249ffbf837c7b37f45))
* **SPI:** Adding JBoss Deployment structure ([0c0bfa1](https://github.com/toolisticon/keycloak-gdpr-module/commit/0c0bfa1823ab37a8e7085e294434c29527deed0a)), closes [#2](https://github.com/toolisticon/keycloak-gdpr-module/issues/2)
* **Testing:** Adding basic Gatlin performance tests ([c94c34f](https://github.com/toolisticon/keycloak-gdpr-module/commit/c94c34f5d4f06b0cb1fa3b75c2a3b7ab836e665c)), closes [#4](https://github.com/toolisticon/keycloak-gdpr-module/issues/4)
* **Tests:** Adding Keycloak performance tests ([9e87117](https://github.com/toolisticon/keycloak-gdpr-module/commit/9e8711766e574b760da2c3fbe7fb9e3f18c24eb4)), closes [#4](https://github.com/toolisticon/keycloak-gdpr-module/issues/4)
* **Tests:** Adding regular keycloak auth scenario ([3b2efc2](https://github.com/toolisticon/keycloak-gdpr-module/commit/3b2efc226cb51eb29a1f1244e6278d66886adcab)), closes [#4](https://github.com/toolisticon/keycloak-gdpr-module/issues/4)
