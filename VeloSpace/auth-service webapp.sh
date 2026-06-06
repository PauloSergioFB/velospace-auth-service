# Variáveis
RESOURCE_GROUP_NAME="rg-velospace"
LOCATION="brazilsouth"

WEBAPP_NAME="velospace-auth-rm559914"
APP_SERVICE_PLAN="velospace-auth"
APP_INSIGHTS_NAME="ai-velospace-auth"

ACR_NAME="acrvelospace559914"
IMAGE_NAME="velospace-auth-service"
IMAGE_TAG="latest"
DOCKER_IMAGE="$ACR_NAME.azurecr.io/$IMAGE_NAME:$IMAGE_TAG"

GITHUB_REPO_NAME="PauloSergioFB/velospace-auth-service"
BRANCH="main"

# Variáveis de Ambiente
JWT_SECRET="QKEhHpmTNka+QWaRehdmhi+wDFC5xqIhjF8G9+zYbrxhCyDidECyc0mJ5HWw1X2gS4mvA7xJyGCfpoh7zDYE9Q=="
ORACLE_DB_URL="jdbc:oracle:thin:@(description= (retry_count=20)(retry_delay=3)(address=(protocol=tcps)(port=1522)(host=adb.sa-saopaulo-1.oraclecloud.com))(connect_data=(service_name=g88477b81fd7bc9_valospaceoracle_high.adb.oraclecloud.com))(security=(ssl_server_dn_match=yes)))"
ORACLE_DB_USER="ADMIN"
ORACLE_DB_PASSWORD="Abacaxi1234!"
ACTIVEMQ_BROKER_URL="tcp://velospace-activemq-broker.brazilsouth.azurecontainer.io:61616"
ACTIVEMQ_USER="admin"
ACTIVEMQ_PASSWORD="abacaxi"
MONGODB_URI="mongodb://admin:abacaxi@velospace-mongodb.brazilsouth.azurecontainer.io:27017/velospace_refs?authSource=admin"

# Criar ACR
az acr create \
  --resource-group "$RESOURCE_GROUP_NAME" \
  --name "$ACR_NAME" \
  --sku Basic \
  --admin-enabled true

# Buildar imagem e enviar para o ACR
az acr build \
  --registry "$ACR_NAME" \
  --image "$IMAGE_NAME:$IMAGE_TAG" \
  .

# Criar Application Insights
az monitor app-insights component create \
  --app "$APP_INSIGHTS_NAME" \
  --location "$LOCATION" \
  --resource-group "$RESOURCE_GROUP_NAME" \
  --application-type web

# Criar Plano de Serviço
az appservice plan create \
  --name "$APP_SERVICE_PLAN" \
  --resource-group "$RESOURCE_GROUP_NAME" \
  --location "$LOCATION" \
  --sku B1 \
  --is-linux

# Criar Web App
az webapp create \
  --name "$WEBAPP_NAME" \
  --resource-group "$RESOURCE_GROUP_NAME" \
  --plan "$APP_SERVICE_PLAN" \
  --deployment-container-image-name "$DOCKER_IMAGE"

# Pegar credenciais do ACR
ACR_USERNAME=$(az acr credential show \
  --name "$ACR_NAME" \
  --query username \
  --output tsv)

ACR_PASSWORD=$(az acr credential show \
  --name "$ACR_NAME" \
  --query "passwords[0].value" \
  --output tsv)

# Configurar container no Web App
az webapp config container set \
  --name "$WEBAPP_NAME" \
  --resource-group "$RESOURCE_GROUP_NAME" \
  --docker-custom-image-name "$DOCKER_IMAGE" \
  --docker-registry-server-url "https://$ACR_NAME.azurecr.io" \
  --docker-registry-server-user "$ACR_USERNAME" \
  --docker-registry-server-password "$ACR_PASSWORD"

# Recuperar a String de Conexão do Application Insights
CONNECTION_STRING=$(az monitor app-insights component show \
  --app "$APP_INSIGHTS_NAME" \
  --resource-group "$RESOURCE_GROUP_NAME" \
  --query connectionString \
  --output tsv)

# Configurar as Variáveis de Ambiente necessárias do App e do Application Insights
az webapp config appsettings set \
  --name "$WEBAPP_NAME" \
  --resource-group "$RESOURCE_GROUP_NAME" \
  --settings \
    WEBSITES_PORT="8080" \
    APPLICATIONINSIGHTS_CONNECTION_STRING="$CONNECTION_STRING" \
    ApplicationInsightsAgent_EXTENSION_VERSION="~3" \
    XDT_MicrosoftApplicationInsights_Mode="Recommended" \
    XDT_MicrosoftApplicationInsights_PreemptSdk="1" \
    JWT_SECRET="$JWT_SECRET" \
    DB_URL="$ORACLE_DB_URL" \
    DB_USER="$ORACLE_DB_USER" \
    DB_PASSWORD="$ORACLE_DB_PASSWORD" \
    ACTIVEMQ_BROKER_URL="$ACTIVEMQ_BROKER_URL" \
    ACTIVEMQ_USER="$ACTIVEMQ_USER" \
    ACTIVEMQ_PASSWORD="$ACTIVEMQ_PASSWORD" \
    MONGODB_URI="$MONGODB_URI"

# Conectar Web App ao App Insights
az monitor app-insights component connect-webapp \
  --app "$APP_INSIGHTS_NAME" \
  --web-app "$WEBAPP_NAME" \
  --resource-group "$RESOURCE_GROUP_NAME"

# Reiniciar Web App
az webapp restart \
  --name "$WEBAPP_NAME" \
  --resource-group "$RESOURCE_GROUP_NAME"
