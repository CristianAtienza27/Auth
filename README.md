# Read Me First | docuten-integration

API de integración CAP <-> EUROTAX (FORECAST).

## Maven Wrapper
```sh
mvn wrapper:wrapper -Dtype=only-script -Dmaven=3.9.4
```

## Local Environment Variables (Maven local.settings.xml)
```sh
GITLAB_PRIVATE_TOKEN="" # Custom private user token generated from https://gitlab.northgateplc.es/gitlab
GITLAB_API_V4_URL="https://gitlab.northgateplc.es/gitlab/api/v4"
GITLAB_ARQ_PROJECT_ID=""
GITLAB_PROJECT_ID="" # Gitlab project id (see https://gitlab.northgateplc.es/gitlab/{namespace}/{project_name}
MVNW_OPTS="--batch-mode --quiet"
```

## Set environment
```sh
AKS_ENV="uat"
AKS_ENV="prod"
APP_NAME="forecast-integration"
```

## Use local gitlab libraries/artefacts 

Add `-s .mvn/local.settings.xml` to `mvnw` command and complete xml file with project's or group's number where artifact is located. Generating a gitlab access token is also required

```sh
./mvnw -s .mvn/local.settings.xml ...
```

Or update and use the `MVNW_OPTS` variable:

```sh
MVNW_OPTS="-s ./.mvn/local.settings.xml --batch-mode --quiet"
```

## Get pom version
```sh
POM_VERSION=$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout)
POM_VERSION=$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout | sed 's/SNAPSHOT/beta/g')
POM_VERSION=$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout | sed 's/-SNAPSHOT//g')-beta
POM_VERSION=$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout | sed 's/-RC//g')-alpha
POM_VERSION=$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout | sed 's/-SNAPSHOT//g' | sed 's/-RC//g')
echo $AKS_ENV'|'$POM_VERSION
```

## Build spring boot docker image

```sh
# Using the spring-boot-maven-plugin
./mvnw $MVNW_OPTS -DskipTests spring-boot:build-image -Dspring-boot.build-image.imageName=$APP_NAME:$POM_VERSION
```

```sh
# Using the jib-maven-plugin from Google
./mvnw $MVNW_OPTS com.google.cloud.tools:jib-maven-plugin:3.3.1:build -Dimage=$APP_NAME:$POM_VERSION
```

### Build registry image

```sh
# Using the spring-boot-maven-plugin
./mvnw $MVNW_OPTS clean package -Dmaven.test.skip=true -Dspring.profiles.active=$AKS_ENV spring-boot:build-image -Dspring-boot.build-image.imageName=acrnorthgate$AKS_ENV.azurecr.io/$APP_NAME:$POM_VERSION
```

```sh
# Using the jib-maven-plugin from Google
az acr login --name acrnorthgate$AKS_ENV
./mvnw $MVNW_OPTS clean package -Dmaven.test.skip=true -Dspring.profiles.active=$AKS_ENV jib:build -Dimage=acrnorthgate$AKS_ENV.azurecr.io/$APP_NAME:$POM_VERSION
```

## Build dockerfile image

```sh
./mvnw $MVNW_OPTS clean package -Dmaven.test.skip=true -Dspring.profiles.active=$AKS_ENV
docker build -t acrnorthgate$AKS_ENV.azurecr.io/$APP_NAME:$POM_VERSION .
```

## Run docker image

```sh
docker run -p 8090:8090 -e "SPRING_PROFILES_ACTIVE=$AKS_ENV" -t $APP_NAME:$POM_VERSION
```

## Push docker image to ACR

```sh
az acr login --name acrnorthgate$AKS_ENV
docker push acrnorthgate$AKS_ENV.azurecr.io/$APP_NAME:$POM_VERSION
```
