
## Module 1 — Domain & DDD Deep Dive

### 1. Service Boundary

Start here because many microservice problems are actually boundary problems.

1. What exactly is a service boundary?
2. What business capability should belong inside one service?
3. How do we discover a good service boundary?
4. What signals indicate that two responsibilities belong to the same service?
5. What signals indicate that they should be separated?
6. Is a service boundary the same thing as a bounded context?
7. Is one bounded context always one microservice?
8. What is the relationship between organizational boundaries and service boundaries?
9. How does data ownership help determine service boundaries?
10. Why should two microservices normally not share the same database?
11. What happens when a service boundary is too large?
12. What happens when it is too small?
13. What is a distributed monolith?
14. How can bad service boundaries create a distributed monolith?
15. How do transactional requirements influence service boundaries?
16. How do business changes reveal whether a boundary was designed correctly?
17. How would I identify service boundaries in an Order Management system?

---

## 2. Bounded Context

1. What is a bounded context?
2. Why is a bounded context necessary in complex domains?
3. What exactly is bounded inside the context?
4. How does meaning change between bounded contexts?
5. Why can the same term have different meanings in different contexts?
6. How is bounded context different from microservice?
7. How is bounded context different from subdomain?
8. Can one bounded context contain multiple aggregates?
9. Can a microservice contain multiple bounded contexts?
10. Can multiple microservices implement one bounded context?
11. How do teams discover bounded contexts?
12. What role does business language play?
13. How does data ownership work across bounded contexts?
14. How should bounded contexts communicate?
15. What problems occur when boundaries are unclear?

Good example to explore:

`Customer` in:

* Sales Context
* Billing Context
* Shipping Context
* Support Context

Ask yourself: is Customer really the same object everywhere?

---

## 3. Ubiquitous Language

1. What is ubiquitous language?
2. Why is language considered part of software architecture in DDD?
3. Who defines the ubiquitous language?
4. Should developers use business terminology directly in code?
5. Should class names reflect domain terminology?
6. Should API terminology reflect domain terminology?
7. How is ubiquitous language related to bounded context?
8. Can one term mean different things in different bounded contexts?
9. What happens when technical terminology leaks into domain discussions?
10. How do ambiguous terms reveal hidden domain problems?
11. How should the language evolve when business rules change?
12. How can developers, product owners, and domain experts keep the language synchronized?

---

# 4. Domain Model

1. What is a domain model?
2. What does a domain model represent?
3. Is a domain model just database entities?
4. What is the difference between a domain model and a persistence model?
5. What is the difference between an anemic and rich domain model?
6. Where should business rules live?
7. What does behavior-rich modeling mean?
8. Why should domain models protect business rules?
9. Should domain objects depend on Spring?
10. Should domain objects depend on repositories?
11. Should domain objects know about HTTP, Kafka, JSON, JPA, etc.?
12. How do entities, value objects, aggregates, services, events, and invariants combine to form the model?
13. What makes a domain model maintainable?
14. When is sophisticated domain modeling unnecessary?

---

# 5. Entity

1. What is a domain entity?
2. What makes something an entity rather than a value object?
3. Why is identity important?
4. Does an entity's identity depend on its database primary key?
5. Can an entity change over time while maintaining the same identity?
6. What constitutes entity equality?
7. Should entities expose setters?
8. How should entity state transitions be controlled?
9. Where should entity-specific business rules live?
10. What should an entity constructor guarantee?
11. Can entities exist outside aggregates?
12. What is the difference between a DDD entity and a JPA `@Entity`?
13. Should every database table become a domain entity?
14. How should entity identity be generated?
15. UUID vs sequence vs domain-specific ID: what are the trade-offs?

Example:

```text
Order
Customer
Account
Subscription
```

Then ask:

Why is `Order` an entity but `Money` probably isn't?

---

# 6. Value Object

1. What is a value object?
2. Why does a value object not require identity?
3. How is equality determined?
4. Why should value objects normally be immutable?
5. What business rules should a value object enforce?
6. Why is `Money` better than using `BigDecimal` everywhere?
7. Why is `EmailAddress` sometimes better than `String`?
8. When should primitive values be replaced with domain-specific value objects?
9. Can a value object contain behavior?
10. Can a value object contain another value object?
11. Should value objects be persisted separately?
12. What is the difference between Entity and Value Object?
13. What are the performance/design costs of introducing many value objects?

Important examples:

```java
Money
EmailAddress
Address
OrderId
CustomerId
DateRange
Percentage
Quantity
```

---

# 7. Aggregate

This one deserves a major deep dive.

1. What is an aggregate?
2. Why do aggregates exist?
3. What problems do aggregates solve?
4. What defines an aggregate boundary?
5. How is aggregate boundary related to transaction boundary?
6. Which objects belong inside an aggregate?
7. Which objects should remain outside?
8. What does consistency boundary mean?
9. Why should aggregates generally be small?
10. Can an aggregate contain multiple entities?
11. Can it contain value objects?
12. Can one aggregate directly reference another aggregate?
13. Why is referencing another aggregate by ID usually preferred?
14. What business rules should be enforced inside the aggregate?
15. What happens when aggregates become extremely large?
16. How does aggregate design affect concurrency?
17. How does aggregate design affect database locking?
18. How does aggregate design change in distributed systems?
19. How do we maintain consistency between aggregates?
20. When do domain events become necessary?

Example:

```text
Order
 ├── OrderItem
 ├── ShippingAddress
 └── Money
```

Question:

Which one should be the aggregate root, and why?

---

# 8. Aggregate Root

1. What is an aggregate root?
2. Why must every aggregate have a root?
3. What responsibilities belong to the root?
4. Why should external objects interact through the root?
5. How does the root protect invariants?
6. Can internal entities be modified directly?
7. Should repositories exist for every entity or only aggregate roots?
8. Can an aggregate root reference another aggregate root?
9. How does an aggregate root control lifecycle?
10. What makes a good aggregate root API?
11. Why is this:

```java
order.setStatus("PAID");
```

often worse than:

```java
order.markAsPaid();
```

12. How does aggregate-root design prevent invalid states?

---

# 9. Invariant

This concept connects almost everything.

1. What is an invariant?
2. What is the difference between an invariant and validation?
3. What is the difference between validation and business rules?
4. Who is responsible for protecting invariants?
5. Should invariants always be true?
6. Can an aggregate temporarily enter an invalid state?
7. What is a transactional invariant?
8. What is a cross-aggregate invariant?
9. How are cross-service invariants handled?
10. What happens when strong consistency isn't possible?
11. How does eventual consistency affect invariants?
12. Should database constraints also enforce invariants?
13. What happens when business invariants exist only in application services?

Example:

```text
Order total cannot be negative.

A shipped order cannot be cancelled.

Account balance must never violate the allowed credit limit.
```

Ask:

Where should each rule live?

---

# 10. Repository

1. What is a repository in DDD?
2. Why does repository abstraction exist?
3. What should a repository represent?
4. Should every entity have a repository?
5. Why are repositories usually defined around aggregate roots?
6. Should repository interfaces belong to domain or infrastructure?
7. What operations should repositories expose?
8. Should repositories expose generic CRUD operations?
9. Is `save()` always enough?
10. Should repositories return domain entities?
11. Should repositories return DTOs?
12. Should domain logic exist inside repositories?
13. What is the difference between repository and DAO?
14. What is the relationship between Spring Data Repository and DDD Repository?
15. How can JPA accidentally influence domain modeling?
16. What happens when lazy loading leaks into domain logic?

---

# 11. Domain Service

1. What is a domain service?
2. Why does a domain service exist?
3. When does logic not naturally belong to an entity or value object?
4. What type of business logic belongs in a domain service?
5. Should domain services be stateless?
6. Can domain services access repositories?
7. Can domain services call external services?
8. What dependencies are acceptable?
9. How is a domain service different from an application service?
10. How is it different from an infrastructure service?
11. How do we prevent domain services from becoming dumping grounds?
12. When should behavior be moved from a domain service back into an aggregate?

Example:

```text
PricingService
CurrencyConversionPolicy
CreditEligibilityPolicy
TransferService
```

But always ask:

Could this behavior naturally belong to an existing domain object instead?

---

# 12. Application Service

1. What is an application service?
2. What responsibility does the application layer have?
3. Should application services contain business rules?
4. What does orchestration mean?
5. How should application services coordinate aggregates?
6. Should they manage transactions?
7. Should they call repositories?
8. Should they call external APIs?
9. Should they publish events?
10. Should they perform authorization?
11. What is the difference between controller and application service?
12. What is the difference between domain service and application service?
13. What does a thin application service look like?
14. What does an overly intelligent application service look like?
15. How does the application service fit with Clean/Hexagonal Architecture?

An excellent comparison to document:

```text
Controller
     ↓
Application Service
     ↓
Domain Model
     ↓
Repository abstraction
     ↓
Infrastructure
```

---

# 13. Domain Event

1. What is a domain event?
2. What does a domain event represent?
3. Why should domain events normally use past tense?

For example:

```text
OrderPlaced
PaymentCompleted
CustomerRegistered
OrderCancelled
```

not:

```text
PlaceOrder
CompletePayment
```

Then investigate:

4. When should an aggregate generate a domain event?
5. Who publishes the event?
6. Who consumes the event?
7. Should the aggregate publish directly to Kafka?
8. What's the difference between domain event and integration event?
9. Should domain events leave the bounded context?
10. What information should an event contain?
11. Event ID, aggregate ID, timestamp, version: which metadata is useful?
12. How are events persisted?
13. What happens if database commit succeeds but event publishing fails?
14. How does the Transactional Outbox Pattern solve this?
15. How do consumers handle duplicate events?
16. What is idempotency?
17. What is event ordering?
18. What does at-least-once delivery mean for domain events?
19. How do schema changes affect old consumers?

This topic will naturally lead you into distributed systems.

---

# 14. Context Mapping

1. What is context mapping?
2. Why do bounded contexts need explicit relationships?
3. What information should a context map contain?
4. What is an upstream context?
5. What is a downstream context?
6. What happens when one team's model influences another team's model?
7. What are the major context-map relationships?

Deep dive later into:

```text
Partnership
Shared Kernel
Customer/Supplier
Conformist
Anti-Corruption Layer
Open Host Service
Published Language
Separate Ways
```

Then ask:

8. Which relationships create tight coupling?
9. Which relationships protect autonomy?
10. How do context maps relate to organization/team structures?
11. How do context maps translate into microservice dependencies?

---

# 15. Anti-Corruption Layer — ACL

1. What is an Anti-Corruption Layer?
2. Why is it called anti-corruption?
3. What exactly is being protected?
4. When should an ACL be introduced?
5. How does an ACL protect the domain model?
6. What is the difference between ACL and API Gateway?
7. What is the difference between ACL and Adapter?
8. What is the relationship between ACL and Hexagonal Architecture?
9. Should external DTOs enter the domain directly?
10. Where should translation happen?
11. How do we map external terminology into our ubiquitous language?
12. How does an ACL help when integrating legacy systems?
13. How does an ACL help when integrating third-party systems?
14. What are the maintenance costs of an ACL?

Example:

External system:

```json
{
  "cust_no": 8171,
  "acct_stat": "A"
}
```

Your domain:

```java
CustomerId
AccountStatus.ACTIVE
```

The ACL is responsible for protecting that translation boundary.

---

# 16. CQRS

Now we move from DDD into architectural patterns.

1. What is CQRS?
2. What does Command Query Responsibility Segregation actually mean?
3. What is a command?
4. What is a query?
5. Why shouldn't commands return complex read models?
6. Does CQRS require two databases?
7. Does CQRS require event sourcing?
8. Can CQRS exist inside one application?
9. What is logical CQRS versus physical CQRS?
10. Why might the write model differ from the read model?
11. How are read models generated?
12. Why is CQRS useful in complex domains?
13. What are its scalability advantages?
14. What consistency problems does it introduce?
15. What does eventual consistency look like from the user's perspective?
16. How do we handle stale reads?
17. When is CQRS unnecessary complexity?
18. How would you implement CQRS with Spring Boot?
19. How would Kafka fit?
20. How would Redis/Elasticsearch/read replicas fit?

Very important comparison:

```text
Traditional CRUD

Client
  ↓
One Model
  ↓
One Database
```

versus:

```text
CQRS

            → Command → Write Model
Client
            → Query   → Read Model
```

---

# 17. Event Sourcing

This should probably be one of the final topics in the module.

1. What is event sourcing?
2. How is it different from normal state persistence?
3. Instead of storing current state, what exactly do we store?
4. How is aggregate state reconstructed?
5. What is an event stream?
6. What is aggregate versioning?
7. What is optimistic concurrency?
8. What is event replay?
9. What is a snapshot?
10. Why are snapshots useful?
11. Does event sourcing require CQRS?
12. Does CQRS require event sourcing?
13. What is the difference between domain events and event-sourced events?
14. Can stored events ever be modified?
15. How do we handle incorrect historical events?
16. How do we evolve event schemas?
17. What is event upcasting?
18. How do we delete sensitive data under an immutable event history?
19. How does event sourcing support auditability?
20. How does it support temporal queries?
21. What are its debugging benefits?
22. What are its operational disadvantages?
23. How does event sourcing increase architectural complexity?
24. When is event sourcing genuinely worth using?
25. When is it massive overengineering?

---

There is also one very important set of comparison notes I recommend creating. These comparisons usually expose whether you truly understand the concepts or merely know their definitions:

```text
Service Boundary vs Bounded Context

Subdomain vs Bounded Context

Entity vs Value Object

Entity vs JPA Entity

Aggregate vs Aggregate Root

Aggregate Boundary vs Transaction Boundary

Invariant vs Validation

Domain Service vs Application Service

Repository vs DAO

Domain Event vs Integration Event

Command vs Domain Event

Bounded Context vs Microservice

ACL vs Adapter

CQRS vs CRUD

CQRS vs Event Sourcing

Domain Model vs Persistence Model

Strong Consistency vs Eventual Consistency
```

And I would study this module in this exact order:

```text
01. Ubiquitous Language
        ↓
02. Domain Model
        ↓
03. Bounded Context
        ↓
04. Service Boundary
        ↓
05. Entity
        ↓
06. Value Object
        ↓
07. Invariant
        ↓
08. Aggregate
        ↓
09. Aggregate Root
        ↓
10. Repository
        ↓
11. Domain Service
        ↓
12. Application Service
        ↓
13. Domain Event
        ↓
14. Context Mapping
        ↓
15. Anti-Corruption Layer
        ↓
16. CQRS
        ↓
17. Event Sourcing
```