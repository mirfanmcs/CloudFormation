This repository contains sample CloudFormation scripts. 

# VPC-EC2
This CloudFormation teamplate will create VPC and deploy transaction-service microservice on EC2 instance from S3. It will also create Autoscaling Group and Load Balancer to make application highly available. 

Application will be deployed using `AWS::CloudFormation::Init`.

Subsequent application version update will require updating stack which will deploy new version of application on an existing EC2 instance without restarting or replacing EC2 instance. 
