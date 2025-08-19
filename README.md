# ParameterizedTypeReference + WebClient con FakeStore API
Microservicio reactivo que demuestra cómo deserializar colecciones genéricas usando *ParameterizedTypeReference&lt;T&gt;* con WebClient. Consume https://fakestoreapi.com/products y expone un endpoint propio.

## Stack
- Java 17+
- Maven (o Gradle)
- Spring Boot 3+

## ¿Qué resuelve?
Por el **type erasure** de Java, List<Product> pierde su tipo en runtime. Con **new ParameterizedTypeReference&lt;List&lt;Product&gt;&gt;() {}** WebClient sabe convertir la respuesta HTTP a **List<Product>** sin casting inseguros.

## Estructura
- *model/Product.java*: POJO de producto.
- *client/FakeStoreClient.java*: WebClient + ParameterizedTypeReference&lt;List&lt;Product&gt;&gt;.
- *controller/DemoController.java*: expone el endpoint reactivo.

## Configuración
*src/main/resource/application.yml*
```
spring:
  application:
    name: ParameterizedTypeReference
fakestore:
  base-url: https://fakestoreapi.com
server:
  port: 8081
```

## Endpoint
**GET /api/product**

## Código Clave
```
webClient.get()
  .uri("/products")
  .retrieve()
  .bodyToMono(new ParameterizedTypeReference<List<Product>>() {});
```

## Notas
- Si FakeStore agrega campos extras, Jackson los ignorará a menos que los modeles.
- Puedes extender con */products/{id}*, */products/categories* o paginación repitiendo el mismo patrón.
