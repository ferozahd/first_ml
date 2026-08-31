MICROSERVICE BOUNDARIES — QUESTION SET

1. What is a microservice boundary, and why is defining the correct boundary important in a microservices architecture?

2. What characteristics indicate that a microservice boundary has been designed correctly?

3. How does the Single Responsibility Principle apply when defining a microservice boundary?

4. How is a microservice's responsibility different from a technical layer or technical function?

5. What is a business capability, and how can business capabilities help identify microservice boundaries?

6. Why is designing services around business capabilities generally preferred over designing them around technical functions?

7. What is high cohesion in microservices, and why should closely related business functionality stay inside the same service?

8. What is loose coupling between microservices, and why is it important?

9. What is the relationship between high cohesion and loose coupling when defining service boundaries?

10. What are the common signs that two microservices are too tightly coupled?

11. When should two pieces of functionality belong to the same microservice, and when should they be separated into different services?

12. Why should a microservice normally own its own database?

13. What problems can occur when multiple microservices directly share the same database?

14. How should one microservice access data owned by another microservice without directly accessing its database?

15. Are there situations where the Database-per-Service pattern becomes difficult to implement? How should those situations be handled?

16. Why is independent deployment an important characteristic of a properly bounded microservice?

17. What kinds of dependencies can prevent one microservice from being deployed independently?

18. If two services must always be deployed together, what might this indicate about their service boundaries?

19. What does service ownership mean in a microservices architecture?

20. What should a microservice own besides its source code—for example, business logic, database, APIs, deployment, and operational responsibilities?

21. What is a service contract, and why is it important for communication between microservices?

22. What information should normally be considered part of a microservice's public contract?

23. Why should internal implementation details of a microservice not be exposed to other services?

24. How can changes to a service contract create coupling between microservices?

25. What is API versioning, and why might a microservice need to version its API?

26. What are the common API versioning strategies used in microservices?

27. When should an API create a new version instead of modifying the existing version?

28. What is backward compatibility in microservices?

29. Why is backward compatibility important when multiple consumers depend on the same service?

30. What types of API changes are usually backward-compatible, and what types are breaking changes?

31. How can a team evolve an API without immediately breaking existing consumers?

32. How do API versioning and backward compatibility support independent deployment?

33. How can synchronous communication between services affect microservice boundaries and coupling?

34. How can asynchronous communication or domain events reduce coupling between bounded services?

35. What is a distributed monolith, and how can poorly designed microservice boundaries create one?

36. What are the major warning signs that microservice boundaries are too small or too fine-grained?

37. What are the warning signs that a microservice is too large and should potentially be split?

38. How would you decide the boundary between services such as Order, Payment, Inventory, and Shipping in an e-commerce system?

39. Suppose Order Service requires customer information. Should Order Service directly read the Customer Service database? Why or why not, and what alternatives are available?

40. Suppose changing one field in Service A requires changes in Service B, Service C, and Service D. What architectural problem does this indicate, and how would you investigate it?

41. Suppose two services participate in the same business operation but own separate databases. How would you maintain consistency without creating tight coupling?

42. How do business transactions that span multiple services influence service-boundary decisions?

43. How are microservice boundaries related to Domain-Driven Design concepts such as Bounded Contexts?

44. Is every Bounded Context necessarily one microservice? Explain the relationship and possible exceptions.

45. How would you evaluate an existing system to determine whether its current microservice boundaries are correct?

46. What trade-offs should an architect consider when deciding whether to merge two services or split one service into multiple services?

47. In a real production system, how would you balance business capability, team ownership, data ownership, deployment independence, and service coupling when defining a microservice boundary?

48. Interview Scenario: A company has 20 microservices, but almost every feature requires changes to 8–10 services. What does this tell you about the architecture, and how would you improve it?

49. Interview Scenario: Two services have separate APIs but share the same tables and frequently use cross-service joins. Are they truly independent microservices? Explain your reasoning.

50. Interview Scenario: A service has hundreds of consumers, and the team needs to change its API contract. How would you introduce the change while maintaining backward compatibility and avoiding disruption?