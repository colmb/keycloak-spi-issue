mvn clean package
scp -i ~/.ssh/puppet.pem ./ear-module/target/cura-keycloak-provider-bundle-1.0.0-SNAPSHOT.ear centos@ec2-34-220-158-105.us-west-2.compute.amazonaws.com:/home/centos
