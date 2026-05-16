# EcoRepair
API enfocada a la reutilizacion de dispositivos.

## Tecnologías utilizadas
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/spring%20boot-%236DB33F.svg?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/spring%20security-%236DB33F.svg?style=for-the-badge&logo=springsecurity&logoColor=white)
![Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![AWS](https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white)
![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)

## Requisitos previos
Antes de ejecutar el proyecto, asegúrate de tener instalado:
- **Java 17** (JDK)
- **Docker Desktop**
- **Maven 3.8+**
- **Git** 
- **Cliente de base de datos**

## Estructura del proyecto
- **/config**: Configuración (ModelMapper)
- **/controller**: Controladores REST y rutas HTTP
- **/domain**: Entidades JPA
- **/dto**: Entrada/salida de datos
- **/exception**: Excepciones personalizadas
- **/repository**: Repositorios JPA
- **/security**: Configuración JWT y autenticación
- **/service**: Lógica de negocio 

### Endpoints
- **Device** (`/device`)
- **Repair** (`/repairs`)

## Instalación y arranque

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/EcoRepair-DAM/EcoRepiar-API
   cd EcoReoair_API
   ```

2. **Crear archivo `.env`** en la raíz del proyecto:
   ```env
   MARIADB_USER=EcoRepair
   MARIADB_PASSWORD=tu_password
   MARIADB_DATABASE=ecorepair
   MARIADB_ROOT_PASSWORD=root_password

   JWT_SECRET=tu_clave_secreta
   JWT_EXPIRATION=86400000

   CLOUDINARY_CLOUD_NAME=tu_cloud_name
   CLOUDINARY_API_KEY=tu_api_key
   CLOUDINARY_API_SECRET=tu_api_secret
   CLOUDINARY_FOLDER=ecorepair
   ```

3. **Levantar la base de datos**
   ```bash
   docker-compose up -d
   ```
 _Nota: No necesitas crear la base de datos manualmente. Docker Compose la crea automáticamente al levantar el contenedor_

4. **Ejecutar la aplicación**
   ```bash
   mvn spring-boot:run
   ```

La API estará disponible en `http://localhost:8080`

## Detener el proyecto

```bash
# Detener la aplicación: Ctrl+C

# Detener Docker:
docker-compose down
```

## Tests de integración (Postman / Newman)

El proyecto incluye una colección Postman en `/docs` para probar todos los endpoints.

```bash
# Instalar Newman globalmente
npm install -g newman

# Ejecutar la colección de tests
newman run docs/EcoRepiar-Test.postman_collection.json
```

## CI/CD

El proyecto incluye dos pipelines de GitHub Actions:

- **`deploy-back.yaml`** — Al hacer push a `main`: construye la imagen Docker, la publica en Docker Hub y despliega en **Amazon EKS** con `kubectl`.
- **`newman.yml`** — En cada pull request a `main` o `develop`: levanta la API y la base de datos con Docker Compose y ejecuta la colección de tests de integración con Newman.


---
Proyecto escolar Curso 2025–2026
