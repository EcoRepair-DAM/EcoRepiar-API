# EcoRepiar API — Colección Postman

## Importar

1. Abrir Postman → **Import**
2. Seleccionar `EcoRepiar_API_postman_collection.json`

## Ejecutar

Lanzar en este orden con el **Collection Runner**:

1. `POST /auth/register` — crea el usuario de prueba
2. `POST /auth/login` — guarda el token automáticamente
3. El resto de peticiones ya usan el token solas

## Variables

| Variable | Valor por defecto |
|----------|------------------|
| `base_url` | `http://localhost:8080` |
| `testEmail` | `test@ecorepair.com` |
| `testPassword` | `password123` |

> Asegúrate de tener la API corriendo antes de ejecutar la colección.