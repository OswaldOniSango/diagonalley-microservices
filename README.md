# Store Microservices (Spring Boot + PostgreSQL)

Servicios:
- gateway (8080)
- users (8081)
- products (8082)
- orders (8083)
- payments (8084)
- offers (8085)
- cart (8086)

## Ejecutar con Docker Compose

```bash
docker compose up --build
```

## Flujo rápido

1) Registrar usuario:
```bash
curl -X POST http://localhost:8080/auth/register   -H 'Content-Type: application/json'   -d '{"name":"Oswaldo","email":"oswaldo@mail.com","password":"123456"}'
```

2) Login (si no quieres re-registrar):
```bash
curl -X POST http://localhost:8080/auth/login   -H 'Content-Type: application/json'   -d '{"email":"oswaldo@mail.com","password":"123456"}'
```

3) Usa el token para llamadas protegidas:
```bash
curl http://localhost:8080/products   -H 'Authorization: Bearer <TOKEN>'
```

## Notas
- JWT es HS256 con clave simétrica (`JWT_SECRET`).
- Cada microservicio tiene su propia base de datos en un mismo PostgreSQL.
- Gateway es sólo enrutador (la validación JWT ocurre en los servicios).
