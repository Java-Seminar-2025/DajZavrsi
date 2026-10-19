# Travelino
Travel fino with Travelino
- Travelino is a web application that allows users to easily find travelling destination within their budget
- Travelino is especially useful for travellers and journey men who have a tight budget but want to have amazing experience travelling
- To start using Travelino, user first has to create an account after that he just puts in the budget he wants to travel with and the destination and he should get the results
- For any help you can contact our support and maintainer Paulo Božulić. Josip Grgat, Duje Budiša
  
# Project Specification
- Backend:	Java + Spring Boot
- Frontend:	React
- Database:	MySQL
- AI Integration:	OpenAI API / Ollama (local LLM)
- Flight Search: API	Skyscanner
- Accommodation: API	Booking.com 
- Attractions & Reviews: API	Tripadvisor 
- Communication:	REST API (JSON)
- Version Control:	Git + GitHub


# Building
Requires Docker with the buildx CLI plugin. Run `docker buildx build . -t travelino`.

# Running
After building, run it like any other docker image: `docker run --rm -p 8080:8080 travelino`.
