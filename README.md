# MesaLista

Sistema de reservaciones de restaurantes desarrollado en Java con interfaz gráfica Swing y base de datos MongoDB. Proyecto para la materia de Diseño de Software.

---

## Requisitos previos

- **Java JDK 11 o superior**
- **Apache NetBeans** (recomendado, el proyecto es multi-módulo Maven)
- **MongoDB Community 7.0** corriendo en `localhost:27017`
- **MongoDB Compass** (opcional, para visualizar la base de datos)

### Instalar MongoDB en Mac
```bash
brew tap mongodb/brew
brew install mongodb-community@7.0
brew services start mongodb/brew/mongodb-community@7.0
```

### Instalar MongoDB en Windows
Descargar el instalador desde [mongodb.com/try/download/community](https://www.mongodb.com/try/download/community) y seguir el asistente. Asegurarse de que el servicio quede corriendo.

---

## Cómo correr el proyecto

1. Clonar el repositorio
2. Abrir **NetBeans** y cargar el proyecto `MesaListaPresentacion`
3. Asegurarse de que MongoDB esté corriendo
4. Hacer **Build** en este orden (cada uno depende del anterior):
   - `MesaListaDTO`
   - `MesaListaPersistencia`
   - `MesaListaDAOs`
   - `MesaListaMappers`
   - `SubSistemaReservaciones`
   - `SubSistemaUsuario`
   - `SubSistemaPagos`
   - `SubSistemaRestaurantes`
   - `MesaListaPresentacion`
5. Hacer **Run** en `MesaListaPresentacion`

> Al arrancar por primera vez, el sistema crea automáticamente los 3 restaurantes y los usuarios de administrador en la base de datos.

---

## Usuarios del sistema

### Actor: Cliente
Registrarse directamente desde la app con cualquier correo electrónico. No requiere contraseña.

### Actor: Dueño de Restaurante
Acceder desde el botón **"¿Dueño de restaurante? Administra aquí"** en la pantalla de inicio.

| Usuario | Contraseña | Restaurante |
|---|---|---|
| `rincon` | `123` | Rincón el Asador |
| `deshuesadero` | `456` | El Deshuesadero |
| `mariscos` | `789` | Mariscos el Rey |

---

## Funcionalidades implementadas

### Flujo Cliente
- Registrar cuenta con correo y datos personales
- Iniciar sesión con correo
- Ver lista de restaurantes disponibles
- Seleccionar platillo del menú
- Configurar preferencias de reservación (fecha, personas, área)
- Confirmar reservación (integración con PayPal Sandbox)
- Ver historial de reservaciones

### Flujo Dueño de Restaurante
- Acceso protegido con usuario y contraseña
- Cada dueño accede únicamente a su propio restaurante
- **Administrar Áreas:** agregar y eliminar áreas con sus mesas
- **Administrar Menú:** agregar y eliminar platillos con precio y descripción
- Los cambios se guardan directamente en MongoDB

---

## Arquitectura del sistema

El proyecto está dividido en módulos Maven independientes organizados en tres capas:

```
┌─────────────────────────────────────────┐
│           MesaListaPresentacion         │  Capa de Presentación
│  InicioSesion, ListaRestaurantes,       │
│  AdministrarAreas, AdministrarMenu...   │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│              Capa de Negocio            │
│  SubSistemaUsuario   SubSistemaPagos    │
│  SubSistemaReservaciones                │
│  SubSistemaRestaurantes                 │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│             Capa de Persistencia        │
│  MesaListaDAOs   MesaListaMappers       │
│  MesaListaDTO    MesaListaPersistencia  │
└─────────────────────────────────────────┘
```

---

## Patrones de diseño implementados

| Patrón | Dónde | Propósito |
|---|---|---|
| **Singleton** | `Conexion`, `ControlOperaciones` | Una sola instancia compartida en toda la app |
| **Facade** | `UsuarioFacade`, `ReservacionesFacade`, `PagosFacade`, `RestaurantesFacade` | Simplificar el acceso a cada subsistema |
| **Adapter** | `PayPalAdapter` | Adaptar la API de PayPal a la interfaz interna `IPasarelaPago` |
| **Builder** | `ReservacionBuilder` | Construir objetos `Reservacion` con múltiples atributos |
| **DAO** | `ReservacionDAO`, `UsuarioDAO`, `RestauranteDAO`, `VentaDAO` | Centralizar el acceso a MongoDB |
| **DTO + Mapper** | Todos los DTOs e `IMapper<D>` | Transferir datos entre capas sin exponer entidades internas |

---

## Base de datos

- Motor: **MongoDB 7.0**
- Base de datos: `mesaLista`
- Colecciones: `usuarios`, `restaurantes`, `reservaciones`, `ventas`
- Conexión: `mongodb://localhost:27017`

### Datos generados automáticamente al iniciar

**Restaurantes:**
- Rincón el Asador — Áreas: Terraza e Interior
- El Deshuesadero — Área: General
- Mariscos el Rey — Área: Palapa

**Usuarios administradores:** `rincon/123`, `deshuesadero/456`, `mariscos/789`

---

## Estructura del repositorio

```
MesaLista/
├── MesaListaDTO/           # Data Transfer Objects
├── MesaListaPersistencia/  # Entidades, Conexión, Builder
├── MesaListaDAOs/          # Acceso a MongoDB (patrón DAO)
├── MesaListaMappers/       # Conversión Document ↔ DTO
├── SubSistemaUsuario/      # Facade + Repository de usuarios
├── SubSistemaReservaciones/# Facade + Repository de reservaciones
├── SubSistemaRestaurantes/ # Facade + Repository de restaurantes
├── SubSistemaPagos/        # Facade + Adapter PayPal
└── MesaListaPresentacion/  # Pantallas Swing + ControlOperaciones
```
