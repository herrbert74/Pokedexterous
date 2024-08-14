# Pokedexterous

An app to retrieve and display Pokemons from the https://pokeapi.co/docs/v2 API.

# Remarks from Zsolt Bertalan

## Tech

* I used commonly used libraries Coroutines, Dagger 2, AndroidX, Paging3, and Compose.
* Recently I wrote code where the presentation architecture was MVI, both professionally and in smaller projects. For 
  this project I switched to MVVM and other Google recommended practices.

## Structure

* I use a monorepo for such a tiny project, however I used a few techniques to show how I can build an app that
  scales, even if they are an overkill as they are now.
* The three main sections (module groups in a larger project) are **data**, **domain**, and **presentation**.
* **Domain** does not depend on anything and contains the api interfaces, and the model classes. I do not use use 
  cases at the moment, otherwise I follow clean architecture.
* **Data** implements the domain interfaces (repos) through the network, local and db packages or modules, and does not
  depend on anything else, apart from platform and third party libraries. Repository implementations are placed here 
  as well.
* **Presentation** layer uses the data implementations through dependency injection and the domain entities. It also contains the *navigation*, the *design* and the *ui* packages. 
* **Dependency Injection** through Dagger and Hilt. The modules are placed in their implementation packages.

## Problems dealt with

* UI according to the requirements.
* Separate DTO and Domain classes, and mapping between them to facilitate effective presentation.
* Images are cached.
* Pokemon details are saved in the database. The app uses a cache first caching strategy, and uses a coroutine flow to 
  update the ui.
* When we have the pokemon details in the database, the details page does not load anything from the network, 
  instead we load data from the database.
* In case of an error a full screen error is shown, but user can recover from an it, for example when the wifi is 
  temporarily off.
* Repository is main safe.

## Room for improvement

* Write more Unit tests and UI tests. Introduce integration tests as the code base grows.
* Caching on the main list.
* Some Compose elements might be made more effective.
* I did not have the time for a nice launcher icon.
