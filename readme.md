## build local image
### in root folder of this project
$mvn clean install
## run side components
### in root folder of this project postgresql & solr
$docker-compose up
## run the application
### in root folder of this project 
$mvn spring-boot:run

## integration tests
import POSTMAN collection
-> "/recipes.postman_collection.json" in root folder of this project 

create an environment

##run in order: 
###Register
1/register
1/authenticate -> this will set authToken param to use in following requests, pls check Tests tab

const responseJson = pm.response.json();
var authToken = responseJson.jwttoken;
pm.environment.set("authToken", authToken)

1/ping

###Ingredients
check already inserted ingredients, use their ids for recipe creation
2/get all ingedients
or create new ingredients and refer their ids
2/create an Ingredient

###Recipes
3/save 
3/get (with params)
3/update