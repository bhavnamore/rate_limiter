Spring Boot + Redis Rate Limiter & Caching

A practical backend project built using Spring Boot and Redis, showcasing two essential production features:

  Distributed API Rate Limiting
  API Response Caching
  Clean and modular architecture using Spring Interceptors and Services

This project provides a solid foundation for understanding how Redis enhances performance and scalability in real-world systems.



Key Features
1. Distributed Rate Limiting
  
  Rate limiting is implemented using Redis’ atomic INCR and EXPIRE commands.
  Allows 5 requests per 60 seconds (configurable)
  Enforced via a Spring Interceptor
  Works across multiple application instances
  Protects APIs from abuse and excessive traffic

 2. Redis-Based API Caching

  Frequently accessed data (e.g., /products) is cached in Redis:
  Key: products:all
  TTL: 60 seconds
  Uses the cache-aside pattern
  Reduces processing time and server load

 3. Clean Architecture

  A clear separation of concerns:

  Controller → Service → Redis
  Controllers stay light
  Services handle caching and business logic

Rate limiting remains centralized in an interceptor
