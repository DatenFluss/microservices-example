# 🚀 Microservices Demo System

This project demonstrates a microservices architecture using Spring Boot 3 and ActiveMQ Artemis for message queuing. The system processes client data through multiple services, each handling a specific aspect of the data enrichment process.

## 🏗️ System Architecture

The system consists of the following microservices:

1. **Client Service** (Port 8080) 🌐
   - Receives HTTP requests with client IDs
   - Validates session IDs (SID) in request headers
   - Initiates the data processing workflow
   - Sends messages to Personal Data Service

2. **Personal Data Service** (Port 8081) 👤
   - Receives client IDs from Client Service
   - Mocks external service calls to fetch personal data
   - Enriches data with personal information
   - Forwards enriched data to Contact Service

3. **Contact Service** (Port 8082) 📞
   - Receives enriched data from Personal Data Service
   - Mocks external service calls to fetch contact information
   - Adds contact list data
   - Forwards data to Financial Service

4. **Financial Service** (Port 8083) 💳
   - Receives data from Contact Service
   - Mocks external service calls to fetch financial information
   - Adds card numbers and other financial data
   - Forwards complete data to Storage Service

5. **Storage Service** (Port 8084) 💾
   - Receives complete client data
   - Persists data to SQLite database using JPA
   - Implements data access layer

## 🛠️ Technical Stack

- ☕ Java 17
- 🍃 Spring Boot 3.2.3
- 📨 ActiveMQ Artemis 2.32.0
- 🗄️ SQLite
- 🐳 Docker & Docker Compose
- 🧪 JUnit 5 & Mockito for testing

## ⚡ System Improvements

1. **Resilience** 💪
   - Implemented Dead Letter Queues (DLQ) for failed message handling
   - Added retry mechanisms for transient failures
   - Used Circuit Breakers for external service calls
   - Implemented graceful degradation

2. **Monitoring & Logging** 📊
   - Comprehensive logging with correlation IDs
   - Structured logging format
   - Different log levels for different environments
   - Performance metrics logging

3. **Error Handling** ⚠️
   - Global exception handling
   - Specific error responses
   - Validation error handling
   - Transaction management

4. **Security** 🔒
   - Session ID validation
   - Service-to-service authentication
   - Secure configuration management
   - Input validation

## 🚀 Running the System

1. Build all services:
   ```bash
   ./mvnw clean package
   ```

2. Start the system using Docker Compose:
   ```bash
   docker-compose up --build
   ```

3. Test the API:
   ```bash
   curl -X POST http://localhost:8080/api/clients/123/process \
        -H "X-Session-ID: your-32-character-session-id-here"
   ```

## 🧪 Testing

Each service includes unit tests for critical components:
- Service layer tests
- Message handling tests
- Data validation tests
- Error handling tests

## 📊 Monitoring

The system can be monitored through:
- 🔍 ActiveMQ Artemis Console (http://localhost:8161)
- 📝 Application logs
- ❤️ Health endpoints (/actuator/health)

## ⚠️ Error Handling

The system implements several error handling mechanisms:
1. Message retry with exponential backoff
2. Dead Letter Queues for failed messages
3. Circuit breakers for external service calls
4. Comprehensive error logging

## 🔮 Future Improvements

1. 🔍 Add service discovery
2. 🔄 Implement distributed tracing
3. 📈 Add metrics collection
4. ⚡ Implement cache layer
5. 📚 Add API documentation
6. 🚦 Implement rate limiting
7. 🌐 Add service mesh capabilities 