# Ouijpers: Un juego entre la vida y la muerte

Este repositorio contiene el código fuente de **Ouijpers**, una implementación basada en un juego inspirado en "el ahorcado". Aquí, el objetivo es sobrevivir a un desafío demoníaco, recuperar las partes perdidas del brazo de RDJ y salvar nuestras almas en el proceso. Este proyecto combina el uso de tecnologías modernas como **Firebase Firestore** para datos en tiempo real y **MySQL** para la persistencia del juego.

## **Descripción del proyecto**
El juego se desarrolla en tres rondas de dificultad creciente, con palabras que los jugadores deben adivinar letra por letra. Cada error cuesta intentos, y cada éxito otorga puntos. El destino del jugador y de RDJ depende de su habilidad para superar los desafíos planteados por los espíritus demoníacos.

## **Características principales**
- **Persistencia en Firebase Firestore** para almacenar y actualizar jugadores en tiempo real.
- **Base de datos MySQL** para gestionar los juegos, incluyendo rondas, intentos y palabras a adivinar.
- **Actualización en tiempo real** y **API REST** desarrollada en **Spring Boot**.
- **Servicios y controladores** que cumplen con los principios de diseño SOLID y arquitectura limpia.

---

## **El lore de Ouijpers**
La historia comienza cuando RDJ nos encomienda una misión para recuperar las partes de su brazo, en manos de espíritus demoníacos que juegan con ellas. Los espíritus proponen un juego: “Ouijpers”, un desafío que pone en riesgo nuestras almas si no logramos adivinar sus palabras dentro de un límite de intentos. Solo ganando este juego diabólico podremos recuperar el brazo perdido y nuestras almas.

---

## **Modelo del juego**
### **Jugador**
La clase Jugador maneja la información básica del jugador:
- `String nombre`: el nombre del jugador.
- `int puntaje`: el puntaje acumulado del jugador.

### **Juego**
Un juego consta de:
- Tres rondas con palabras de dificultad creciente.
- Hasta 6 intentos por ronda.
- Puntos otorgados:
  - 1 punto por letra correcta.
  - 5 puntos por palabra acertada.

---

## **Servicios**
### **JugadorService**
Gestión de jugadores utilizando **Firestore**:
- **CRUD básico.**
- Métodos adicionales:
  - `Mono<Jugador> adivinarLetra(Jugador jugador, Character letra, Juego juego)`: Actualiza el puntaje del jugador tras un intento.
  - `Mono<Integer> obtenerPuntaje(String nombre)`: Devuelve el puntaje total de un jugador.
  - `Flux<Jugador> obtenerRanking()`: Proporciona un ranking de jugadores ordenado por puntaje.

### **JuegoService**
Gestión de juegos utilizando **MySQL**:
- **CRUD básico.**
- Métodos adicionales:
  - `int cantIntentosRestantes(Long id)`: Devuelve intentos restantes de la ronda actual.
  - `String palabraAdivinando(Long id)`: Muestra la palabra a adivinar (progresivamente).
  - `String letrasEquivocadas(Long id)`: Lista letras incorrectas.
  - `String rondaActual(Long id)`: Indica la ronda actual.
  - `Jugador empezarJuego(String nombre)`: Crea un nuevo juego y jugador.

---

## **Endpoints disponibles**
El proyecto incluye controladores REST con los siguientes endpoints:

### **Jugador**
- `POST /jugador`: Crear un jugador.
- `GET /jugador/{nombre}`: Buscar un jugador por su nombre.
- `GET /jugador/ranking`: Obtener el ranking de jugadores.
- `PUT /jugador/{nombre}/adivinarLetra/{letra}`: Adivinar una letra y actualizar el puntaje.
- `GET /jugador/{nombre}/puntaje`: Obtener el puntaje de un jugador.

### **Juego**
- `POST /juego`: Iniciar un nuevo juego.
- `GET /juego/{id}/intentosRestantes`: Consultar intentos restantes.
- `GET /juego/{id}/palabra`: Consultar la palabra que se está adivinando.
- `GET /juego/{id}/letrasEquivocadas`: Consultar letras incorrectas.
- `GET /juego/{id}/rondaActual`: Consultar la ronda actual.

---

## **Tecnologías utilizadas**
- **Java 21** con **Spring Boot 3.1.1**
- **Firebase Firestore** para almacenamiento en tiempo real.
- **MySQL** como base de datos relacional.
- **Railway** como plataforma para el despliegue.
- **Gradle** para la gestión de dependencias.

---

## **Despliegue**
Este proyecto está desplegado en Railway y puede ser accedido públicamente. Para contribuir o realizar pruebas, sigue estos pasos:

### **Configuración local**
1. Clona este repositorio:
   ```bash
   git clone https://github.com/tu-repo/ouijpers.git
   ```
2. Crea el archivo `.env` en la carpeta raiz y configuralo con las siguientes variables:
   ```env
   PORT=8080
   DB_USER=tu_usuario
   DB_PASSWORD=tu_contraseña
   DB_HOST=localhost
   DB_PORT=3306
   DB_NAME=tu_base_de_datos
   DB_KEY=json_de_firebase
   ```
3. Ejecuta la aplicación:
   ```bash
   ./gradlew bootRun
   ```

---

