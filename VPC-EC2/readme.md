CloudFormation teamplate will create VPC and deploy transaction-service microservice on EC2 instance from S3. It will also create Autoscaling Group and Load Balancer to make application highly available. 

Application will be deployed using `AWS::CloudFormation::Init`. To deploy new version of application, change the app version (transaction-1.0) in CloudFormation script and update stack will cause new version of application to be installed without restarting or replacing EC2 instance. 

Internal setting of 1 minute in `cfn-hup.conf` tells deamon to detect meta data changes with 1 minute interval and perform updates on EC2 instance.  

Note: User Data is immutable, therefore cannot be used to deploy new version of application.  


# Create Stack 
## Using Application Load Balancer

Run following command to deploy using ALB:

`$ aws --region ap-southeast-2 --profile yourprofile cloudformation create-stack --stack-name mystack --template-body file://CloudFormation/using-alb/CF-transaction-service-deployment-ALB.yaml --parameters file://CloudFormation/using-alb/test-parameters-ALB.json --capabilities CAPABILITY_IAM`


## Using Classic Load Balancer

Run following command to deploy usng ELB:

`$ aws --region ap-southeast-2 --profile yourprofile cloudformation create-stack --stack-name mystack --template-body file://CloudFormation/using-elb/CF-transaction-service-deployment-ELB.yaml --parameters file://CloudFormation/using-elb/test-parameters-ELB.json --capabilities CAPABILITY_IAM`


## Delete Stack

Run following command to delete stack:

`$ aws --region ap-southeast-2 --profile yourprofile cloudformation delete-stack --stack-name mystack`



# Create Stack using CI/CD

Create repository name `transaction` in CodeCommit and add following files in your repository to create using ALB. Note, you can follow similar steps to create using ELB:

- `CloudFormation/using-alb/CF-transaction-service-deployment-ALB.yaml`
- `CloudFormation/using-alb/prod-codepipeline-configuration-ALB.json`
- `CloudFormation/using-alb/test-codepipeline-configuration-ALB.json`


Run following command to create stack for CodePipeline. 

`$ aws --region ap-southeast-2 --profile yourprofile cloudformation create-stack --stack-name myCodePipelineStack --template-body file://CodePipeline/CF-transaction-pipeline.yaml --parameters file://CodePipeline/parameters.json --capabilities CAPABILITY_IAM`


CodePipeline will perform the following steps:
1. Get the source from CodeCommit
2. Run pre tests to validate template and AMIs in the template
3. Create test stack 
4. Send an email for approval of test stack 
5. Approve test stack (manual step)
6. Delete test stack 
7. Create prod stack change set 
8. Send an email for approval of prod stack change set 
9. Approve prod stack change set (manual step)
10. Execute prod stack chage set 

We deployed version 1.0 of transaction service. To deploy new version 1.1 of transaction service, update version number in the `AWS::CloudFormation::Init` section in `CF-transaction-service-deployment-ALB.yaml` and check in the file in CodeCommit. 

After you check in the file in CodeCommit, CodePipeline will detect change in repository and trigger the pipeline to update the stack. After stack is updated, new version 1.1 of transaction service will be deployed on an existing EC2 instance. 

## Delete Stack

Run following command to delete stack:

Delete Prod Stack first:

`$ aws --region ap-southeast-2 --profile yourprofile cloudformation delete-stack --stack-name Prod-transaction-stack`


Delete Code Pipeline stack:

`$ aws --region ap-southeast-2 --profile yourprofile cloudformation delete-stack --stack-name myCodePipelineStack`

