# Exámenes de Sangre — Arquitectura Hexagonal + patrones GoF

Aplicación de escritorio JavaFX para registrar exámenes de sangre (código, paciente, costo, tipo de sangre y factor Rh), listarlos y guardarlos/recuperarlos de un archivo mediante serialización.

Es la refactorización del proyecto base [WolframDev/ParadigmasExamen2](https://github.com/WolframDev/ParadigmasExamen2). El original era un formulario JavaFX con todos sus manejadores vacíos y sin modelo. Se reorganizó con **Arquitectura Hexagonal (Ports & Adapters)** y tres patrones GoF: **Builder**, **Facade** y **Observer**.

## Funcionalidades

| Botón | Caso de uso | Resultado |
|---|---|---|
| Guardar | `RegistrarExamenUseCase` | Valida el examen y lo registra si el código no existe |
| Listar | `ListarExamenesUseCase` | Muestra los exámenes registrados en el área de mensajes |
| Serializar | `SerializarExamenesUseCase` | Guarda todos los exámenes en `examenes.dat` |
| Deserializar | `DeserializarExamenesUseCase` | Reemplaza los exámenes en memoria con los del archivo |
| Limpiar | — | Limpia el formulario |

Reglas de negocio: el código es obligatorio, único y se guarda en mayúsculas; el nombre es obligatorio; el costo debe ser mayor que 0; el tipo de sangre (A, B, AB, O) y el factor Rh son obligatorios.

## Arquitectura

```
Examen2/src/main/java/co/edu/poli/examen2/
├── dominio/                    # Núcleo: sin dependencias externas
│   ├── modelo/                 # ExamenSangre (+Builder), TipoSangre, FactorRh
│   └── excepcion/              # ExamenInvalidoException, ExamenDuplicadoException
├── aplicacion/
│   ├── puerto/entrada/         # Interfaces de los casos de uso
│   ├── puerto/salida/          # ExamenRepositoryPort, AlmacenamientoExamenesPort
│   ├── servicio/               # Implementación de los casos de uso
│   ├── fachada/                # GestionExamenesFacade (Facade)
│   └── evento/                 # PublicadorEventos, ObservadorExamen (Observer)
└── infraestructura/
    ├── entrada/javafx/         # Adaptador de entrada: App, PrimaryController
    ├── salida/memoria/         # Adaptador de salida: repositorio en memoria
    ├── salida/archivo/         # Adaptador de salida: serialización a archivo
    ├── salida/log/             # Observador de bitácora (java.util.logging)
    └── configuracion/          # ContenedorDependencias (raíz de composición)
```

Las dependencias apuntan hacia el dominio: la infraestructura conoce a la aplicación y la aplicación conoce al dominio, nunca al revés. `ContenedorDependencias` crea los adaptadores y los conecta. `App` inyecta la fachada en el controlador mediante un `controllerFactory` de `FXMLLoader`.

| Patrón | Categoría | Dónde | Para qué |
|---|---|---|---|
| Builder | Creacional | `ExamenSangre.Builder` | Crear exámenes inmutables y siempre válidos |
| Facade | Estructural | `GestionExamenesFacade` | Dar a la UI un único punto de acceso a los casos de uso |
| Observer | Comportamiento | `PublicadorEventos` / `ObservadorExamen` | Notificar los resultados a la UI y a la bitácora sin acoplar los casos de uso |

Los diagramas y los informes están en [docs/](docs) y en la wiki del repositorio:
`actividad1.pdf`, `dominio2.png`, `arquitectura2.png`, `dominio3.png` y `actividad3.pdf`. Las fuentes PlantUML están en `docs/diagramas/*.puml` y las fuentes HTML de los informes en `docs/informes/`.

## Requisitos

- JDK 17 o superior (probado con JDK 21)
- Apache Maven 3.8 o superior
- Conexión a internet la primera vez (Maven descarga JavaFX 21, JUnit 5 y JaCoCo)

No hace falta instalar JavaFX: Maven lo descarga.

## Instalación y ejecución

```bash
git clone https://github.com/<su-usuario>/ParadigmasExamen2.git
cd ParadigmasExamen2/Examen2
mvn clean javafx:run
```

El archivo `examenes.dat` se crea en el directorio desde donde se ejecuta la aplicación (`Examen2/`).

Desde un IDE: importe `Examen2` como proyecto Maven y ejecute el goal `javafx:run`. Si ejecuta `App` directamente, configure el module path de JavaFX.

## Pruebas y cobertura

```bash
cd Examen2
mvn clean verify
```

Se ejecutan 17 pruebas JUnit 5 (dominio, casos de uso, Observer y una prueba de integración con los adaptadores reales). El informe de cobertura queda en `target/site/jacoco/index.html`.

## JavaDoc

```bash
mvn javadoc:javadoc     # salida en target/reports/apidocs (o target/site/apidocs)
```

## Análisis con SonarQube

1. Inicie un servidor SonarQube, por ejemplo con `docker run -d --name sonarqube -p 9000:9000 sonarqube:lts-community`.
2. En http://localhost:9000 cree el proyecto `ParadigmasExamen2` y genere un token.
3. Ejecute:
   ```bash
   cd Examen2
   mvn clean verify sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=<TOKEN>   # en SonarQube 10+ use -Dsonar.token
   ```

El `pom.xml` ya configura la clave del proyecto y la ruta del informe de JaCoCo. La captura del resumen está en la wiki.

## Declaración de uso de IA

| Herramienta | Actividades en las que se usó |
|---|---|
| Claude Code (Anthropic, modelo Claude Opus) | Análisis del proyecto original; propuesta y codificación de la refactorización hexagonal; implementación de Builder, Facade y Observer; pruebas unitarias; JavaDoc; diagramas PlantUML; borradores de los informes `actividad1.pdf` y `actividad3.pdf`; este README. |

El autor revisó, ejecutó y validó todo el contenido generado: compilación, pruebas, ejecución de la aplicación y análisis de SonarQube.
