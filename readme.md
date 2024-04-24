
## Tartaros Services
This is a Java-based project using Maven and Spring Boot. It's an authentication service that handles OAuth process with Google and generates a JWT (JSON Web Token) for use in other services.

## Prerequisites

- Java 11 or higher
- Docker/Kubernetes

## Installation

1. Clone the repository:
```
git clone https://github.com/SilasGitHub/project-name.git
```
2. Navigate into the project directory:
```
cd tartaros-services
```
3. Obtain Google API credentials through the following steps:
    - Create a Google Cloud account https://cloud.google.com/apigee/docs/hybrid/v1.4/precog-gcpaccount
    - Create a Google Cloud project https://developers.google.com/workspace/guides/create-project
    - Create a service account https://developers.google.com/workspace/guides/create-credentials#service-account
    - Create JSON key https://developers.google.com/workspace/guides/create-credentials#create_credentials_for_a_service_account
4. Store the obtained JSON credentials as /tartaros-services/services/google-service/src/main/resources/cred.json
5. Follow the steps in https://developers.google.com/identity/gsi/web/guides/get-google-api-clientid to obtain Google client ID and Google client secret
6. Generate a public-private key pair through a public-private key tool, for example https://cryptotools.net/rsagen


# Run using Docker
1. Create a file named .env in /tartaros-services
2. Add the following lines in .env with information obtained from step 5 and 6 in the previous section
    - JWT_PUBLIC_KEY=<your_public_key>
    - JWT_PRIVATE_KEY=<your_private_key>
    - GOOGLE_CLIENT_ID=<your_client_id>
    - GOOGLE_CLIENT_SECRET=<your_client_secret>
3. Add the following line of code to line 62 of \tartaros-services\services\auth-service\src\main\java\tartaros\authservice\authentication
```
System.out.println(sub);
(As we currently use mock data instead of the Google Workspace API, the subject ID must be obtained manually through debugging)
```
4. Run the project from /tartaros-services from terminal
```
docker compose up --build
```
5. Navigate to localhost:8080 in your browser
6. Attempt to log in using your Google account
7. View the output of the auth service to obtain your subject ID
8. Add the following line to `getWorkSpace()` function in `\tartaros-services\services\auth-service\src\main\java\tartaros\authservice\authentication`
```
result.add(new WorkspaceUser(UUID.randomUUID(), <your_subject_id>, <your_name>, <role>));
```
role can be true for admin and false for regular member
9. Remove line 62 from `\tartaros-services\services\auth-service\src\main\java\tartaros\authservice\authentication`
10. Relaunch the project using
```
docker compose up --build
```
12. Navigate to localhost:8080 in your browser
13. Log in using the same method, now with access to our application
14. Once you are done, use CTRL+C in the terminal to stop the application

   
# Run using kubernetes
1. Navigate to /tartaros-services/deployment and open the `secrets_template.yaml` file and rename it to `secrets.yaml`
2. Fill out the secrets.yaml file with the required information. All values must be base64 encoded
3. Add the following line of code to line 62 of `\tartaros-services\services\auth-service\src\main\java\tartaros\authservice\authentication`
```
System.out.println(sub);
```
(As we currently use mock data instead of the Google Workspace API, the subject ID must be obtained manually through debugging)
4. Start a terminal in the deployment folder, and run
```
minikube start
```
5. In the terminal use the following command to build the images in minikube
```
./minikube_push.sh
```
6. Start the cluster from the terminal using the following command
./start.sh
7. Use tunneling to expose the loadBalancers in minikube
minikube tunnel
8. Follow steps 5, 6, 7, 8 and 9 from "#Run using Docker" section
9. Update, reload and relaunch the application using the following commands
```
./stop.sh
./minikube_push.sh
./start.sh
```
10. Navigate to localhost:8080 in your browser
11. Log in using the same method, now with access to our application
12. Once you are done, use CTRL+C in the terminal to stop the application

## Testing
1. Run the project as described in the previous sections
2. Open Postman
3. From the top left, select file -> import and import the file `/testing/Tartaros services.postman_collection.json`
4. Navigate to localhost:8080 in your browser
5. Log in to your account
6. Press "F12" and obtain your cookie with name "jwt"
7. Return to Postman and open a request you wish to perform
8. In the top right of the request, press "Cookies"
9. Add a cookie replacing <Cookie_n>=<value> with
        <Cookie_n> - "jwt"
        <value> - token obtained in step 6
10. Proceed to perform any request you wish to issue by selecting a request and hitting "Send"

