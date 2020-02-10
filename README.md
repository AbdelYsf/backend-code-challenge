# backend-code-challenge
REST microservice that list the languages used by trending public repos on GitHub ,For every language,it calculates the attributes below 
* Number of repos using this language
* The list of repos using the language

### Endpoints
* trending repositories => http://localhost:8080/repositories/trending
* repositories by language =>  http://localhost:8080/repositoriesbylang/{language}
 for language like c# which use a characher , we should pass the name as URL ENCODED (c%23)
 #### side note 
 for all repositories i had to use his https://api.github.com/repositories
     but i did not pay attention at the start
    so i used this endpoint 'https://api.github.com/search/repositories?q=created'
     which returns some repos (the functionality demanded is applied on these repos )
     
