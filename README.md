# A Lightweight Online Code Runner for Rapid Testing and Output

## Overview

This project is a lightweight online code runner designed for rapid testing and output. It utilizes the Monaco Editor to provide a seamless coding experience in the browser. Users can write Java code and execute it on the backend, with the results returned immediately for quick feedback.

## Features

- **Monaco Editor Integration:** A powerful code editor in the browser with syntax highlighting and auto-completion for Java.
- **Java Code Execution:** Write and run Java code directly from your browser.
- **Instant Output:** Receive immediate feedback on your code execution results.
- **No Authentication Required:** Get started quickly without the need for user accounts or authentication.

## Sreenshots
![Image](https://github.com/user-attachments/assets/c6060ff7-ebeb-41cd-921e-487628e51f12)
![Image](https://github.com/user-attachments/assets/d877e415-bcd7-4a6d-9059-909ea3054793)

## Technologies Used

- **Frontend:**
  -  Angular
  -  Monaco Editor
    
- **Backend:**
  - Java     
  - Spring Boot
    
- **API:**
  - RESTful API for communication between the frontend and backend
  - Endpoints
    - `POST /api/v1/execute`   

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 17 for springboot backend
- Node.js and npm (for frontend development)

### Installation

1. **Clone the repository:**

   ```bash
   git clone https://github.com/Justice-Molefi/justcode.git
   cd justcode
   ```

2. **Setup:**
   - Build and run the Spring Boot application:
     ```bash
     ./mvnw spring-boot:run
     ```
   - Access the request-handler API at `http://localhost:8080`
   - Access the code-execution module at `http://localhost:8081`



## Future Enhancements

- Add support for other languages.

## Contributing

Contributions are welcome! If you'd like to contribute to this project, please fork the repository and submit a pull request with your changes.

## License

This project is licensed under the [MIT License](LICENSE).

## Contact

For questions or collabs, feel free to reach out to me at [nkopane.mj@gmail.com].
