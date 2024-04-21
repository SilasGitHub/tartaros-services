docker tag tartaros-services-financial:latest silasdockerhub/tartaros-services:financial
docker tag tartaros-services-activity:latest silasdockerhub/tartaros-services:activity
docker tag tartaros-services-google:latest silasdockerhub/tartaros-services:google
docker tag tartaros-services-discovery:latest silasdockerhub/tartaros-services:discovery
docker tag tartaros-services-ui:latest silasdockerhub/tartaros-services:ui
docker tag tartaros-services-gateway:latest silasdockerhub/tartaros-services:gateway
docker tag tartaros-services-auth:latest silasdockerhub/tartaros-services:auth
docker push silasdockerhub/tartaros-services:financial
docker push silasdockerhub/tartaros-services:activity
docker push silasdockerhub/tartaros-services:google
docker push silasdockerhub/tartaros-services:discovery
docker push silasdockerhub/tartaros-services:ui
docker push silasdockerhub/tartaros-services:gateway
docker push silasdockerhub/tartaros-services:auth