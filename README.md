# President election application

### Simplified president election backend application with [Spring Boot](https://spring.io/projects/spring-boot)

## Requirements
* [JDK 21](https://www.oracle.com/java/technologies/downloads/#java21)
* [Maven 4](https://maven.apache.org/)

## Running the application locally

### Clone the repository:
```bash
git clone https://github.com/alojine/election-application.git
```

### Build the project using Meven:
```
cd election-application
mvn clean install
```

### Run the application:
```
mvn spring-boot:run
```


## Endpoints:

### GET ```http://localhost:8080/api/v1/candidates```
*  Provides information about the candidates: **firstname**, **lastname**, **votingNumber** and short **agenda**. 

### POST ```http://localhost:8080/api/v1/votes```
* Allows Voter to make a post request in order to vote for a candidate.
* Voter can attempt only one vote.
* Voter cannot change his vote after the vote has been submitted.
* There are five regions to choose from **CALIFORNIA, TEXAS, FLORIDA, OHIO, INDIANA**. Region must be written using **CAPITAL** letters only.
* Use provided request body bellow to make a vote!
```
  {
  "name": "nameOfTheVoter",
  "region": "REGION",
  "candidateNumber": "1"
  }
```

### GET ```http://localhost:8080/api/v1/results```
* Picks a single winner if there was a clean lead. Meaning that candidate has more than 50% of voters.
* If there is no such candidate then two candidates with the highest voting percentage are selected.

### GET ```http://localhost:8080/api/v1/results/general```
* Provides an overall distribution of candidates an amount of voters percentage they have received.

### GET ```http://localhost:8080/api/v1/results/regions```
* Displays a distribution list of voting in different regions. Distribution list contains **region** and a list of candidates with how much **percentage** they have received **within that region**.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.