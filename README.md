# Howold
This project is an assignment.
The name of the repo is not very clear of what the application does rs.

## Running locally
The application is a spring boot aplication with maven as package manager. So to run it locally, just execute the command
```
$ mvn spring-boot:run
```
It will install, build and run the application.

## API endpoints
There are the following endpoints available:
- /people
  - query param `sort-by=issues` available (not mandatory). If none set there will be no sorting.
- /people/{id}

### /people
This endpoint responds with every person.
When `sort-by=issues` is passed, the response is sorted by the fields "issues" in ascending order.

Example of response for route `/people`:
```json
[
    {
        "id": "6",
        "firstname": "Alberto",
        "lastname": "Oliveira",
        "birthdate": "1996-07-18",
        "issues": 9
    },
    {
        "id": "2",
        "firstname": "Roberta",
        "lastname": "Dolores",
        "birthdate": "1992-08-07",
        "issues": 1
    },
    {
        "id": "",
        "firstname": "",
        "lastname": "",
        "birthdate": "",
        "issues": null
    },
    {
        "id": "3",
        "firstname": "Juan",
        "lastname": "1987-02-19",
        "birthdate": "17",
        "issues": null
    },
    ...
]
```

Example of response for `/people?sort-by=issues`:
```json
{
        "id": "61",
        "firstname": "Joaquim",
        "lastname": "Severino",
        "birthdate": "1966-06-14",
        "issues": 0
    },
    {
        "id": "2",
        "firstname": "Roberta",
        "lastname": "Dolores",
        "birthdate": "1992-08-07",
        "issues": 1
    },
    {
        "id": "1",
        "firstname": "Brian",
        "lastname": "",
        "birthdate": "1987-04-05",
        "issues": 5
    },
    {
        "id": "6",
        "firstname": "Alberto",
        "lastname": "Oliveira",
        "birthdate": "1996-07-18",
        "issues": 9
    },
    ...
```

### /people/{id}
This endpoint responds with the information saved of the person and calculates it's age, based on their's birthdate.

Example of response for route `/people/{id}`:
```json
{
    "id": "2",
    "firstname": "Roberta",
    "lastname": "Dolores",
    "birthdate": "1992-08-07",
    "issues": 1,
    "age": 29
}
```

### Data source
As proposed in the assignment, the data should be read from a csv file. It currently reads from the file located in `src/main/resources/static/database.csv`. Therefore, if any data is to be loaded, it should be added there.

The available headers are:
- id
- firstname
- lastname
- birthdate
- issues

They don't need to be in a specific order in the .csv file.

## Assumptions and Decisions
Since it's an assignment, every aspect of the code will probably be evaluated. This section aims at explaining some assumptions and the decisions they led to.
- **The CSV file will not change during the execution of the application**: it was assumed in order to keep data cached in memory. If the CSV file does not change, the application should not reload it everytime a request comes in. Although it was assumed, I understand that it might not be very common to have an immutable datasource for and API. In a scenario where the CSV might change, there are tools that might help handling its data, like Apache Spark.
- **The CSV file size can be handled in memory by the application**: this is also an assumption that might lead to poor behavior in production environments. For a file that contains too much rows, handling and filtering it in memory may lead to performance issues and memory errors, like being out of memory. This is another case that I would use a tool like Apache Spark to handle. Also because it gives a lot of potential to what can be done with the done with the data (like joins for example).
- **The CSV file may contain incomplete data**: Since CSV files are text files, there are no constraints in it to guarantee that the data contained in it is always the way we expect to. For example, there might be people that do not have some information, even the ID. When this happens, data will be handled as null and the fields will not be used for filtering, for example. The most important is that the **application won't break in case there are information missing**.
- **The age is always calculated**: The is no caching of the age. This decision was taken in order to have no need for a refresh mechanism for the age. If the ages were cached, if the cached was loaded in a day and the application stayed up until the next day, there might be some ages that would change and the application would not be able to refresh them, leading to a bug.
- **The project structure is based in Domains**: The package structure of the project is based on domains that are meaningful for the project. For example, Person is an domain. The project does not follows MVC project structures. The Domain structure is something that I've been using and I find it more intuitive and well-organized. That is why there is the packages "person" and "csv" in the root package. But the project might more subdomains in case it had more classes. I decided not to create subpackages, in order to keep the project structure a little simpler. The structure has influences of the Domain Driven Design but does not follow it strictly.


## Next steps
Unfortunaly I could not do everything that I wish for the assignment. So I would like to share the top improvements that I see for the application:
- **Treating every data read from CSV as text**: Some data that comes in the CSV might be wrong or misplaced. There might be a row where the birthdate and the firstname are switched, for example, if you consider that the CSV file can be a result of many different sources. So, in order to make the application **more resilient**, I would read every field as an String and validate before parsing and possibly have null where the values are not valid, like for an invalid date.
- **Allowing to read the CSV file dynamically**: When creating an application to be used widely, it is best to make it the most parametrized possible. The application could be used by many other developers if it would allow to pass as parameter where the file is. Even allowing to use an remote csv file.
- **Logging**: logging is an essential part of observability of the applications. I focused on the functionalities and didn't take the time to add proper logs to the API. There could be a logback config to log correlation and flow ids. There could be filters that always log information about incoming requests and outgoing responses.