# 1. Define the cloud provider constraints
terraform {
  required_version = ">= 1.5.0"
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

# 2. Configure the AWS Provider pointing to your local US region
provider "aws" {
  region = "us-east-1"
}

# 3. Create a secure AWS ECR Repository to host your Docker images
resource "aws_ecr_repository" "reg_analyzer_repo" {
  name                 = "federal-regulations-analyzer"
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = true
  }

  tags = {
    Environment = "Production"
    Project     = "RegTech Analytics"
  }
}

# 4. Create an S3 Bucket for caching raw federal eCFR XML datasets
resource "aws_s3_bucket" "regulatory_data_storage" {
  bucket        = "molla-federal-regulations-data-lake"
  force_destroy = false

  tags = {
    Environment = "Production"
    Purpose     = "eCFR XML Payload Storage"
  }
}

# 5. Block all public access to the S3 bucket to enforce data security
resource "aws_s3_bucket_public_access_block" "private_storage_block" {
  bucket = aws_s3_bucket.regulatory_data_storage.id

  block_public_acls       = true
  block_public_policy     = true
  ignore_public_acls      = true
  restrict_public_buckets = true
}

# 6. Output variables to print resource locations after running execution plans
output "ecr_repository_url" {
  description = "The target private registry URL for pushing your Docker container image."
  value       = aws_ecr_repository.reg_analyzer_repo.repository_url
}

output "s3_bucket_arn" {
  description = "The Amazon Resource Name for your file ingestion engine rules."
  value       = aws_s3_bucket.regulatory_data_storage.arn
}
