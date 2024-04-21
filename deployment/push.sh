docker tag tartaros-services-financial:latest dockjoep/tartaros-services:financial
docker tag tartaros-services-activity:latest dockjoep/tartaros-services:activity
docker tag tartaros-services-google:latest dockjoep/tartaros-services:google
docker tag tartaros-services-discovery:latest dockjoep/tartaros-services:discovery
docker tag tartaros-services-ui:latest dockjoep/tartaros-services:ui
docker tag tartaros-services-gateway:latest dockjoep/tartaros-services:gateway
docker tag tartaros-services-auth:latest dockjoep/tartaros-services:auth
docker push dockjoep/tartaros-services:financial
docker push dockjoep/tartaros-services:activity
docker push dockjoep/tartaros-services:google
docker push dockjoep/tartaros-services:discovery
docker push dockjoep/tartaros-services:ui
docker push dockjoep/tartaros-services:gateway
docker push dockjoep/tartaros-services:auth