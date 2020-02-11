# backend-code-challenge
REST microservice that list the languages used by trending public repos on GitHub ,For every language,it calculates the attributes below 
* Number of repos using this language
* The list of repos using the language

### Endpoints
* trending repositories => http://localhost:8080/repositories/trending
* repositories by language =>  http://localhost:8080/repositoriesbylang/{language}
 for language like c# which has special characher , we should pass the name as URL ENCODED (c# => c%23)

     
  ### Explination for what is missing
   when i use this endpoint https://api.github.com/repositories , the languages used by each repo are defined in an other URI that i should  fetch too ,and  without the use of a cash system my ip address get banned as i exceeded the limits (I done 101 request for each try)
   so i used this endpoint instead 'https://api.github.com/search/repositories?q=created' as it returns field that has the language used by the each repo , ( i calculate the language by each repo based on the repos retured by the this endpoint)
  
  the second brach uses the first endpoint "https://api.github.com/repositories" if you want to have a look at what i have done on it 
  
     
