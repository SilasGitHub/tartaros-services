minikube image build -t localhost/activity-service:latest ../services/activity-service
minikube image build -t localhost/financial-service:latest ../services/financial-service
minikube image build -t localhost/api-gateway:latest ../services/api-gateway
minikube image build -t localhost/auth-service:latest ../services/auth-service
minikube image build -t localhost/discovery-service:latest ../services/discovery-service
minikube image build -t localhost/google-service:latest ../services/google-service
minikube image build -t localhost/ui-service:latest ../services/ui-service