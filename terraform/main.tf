terraform {
  required_providers {
    cloudflare = {
      source = "cloudflare/cloudflare"
      version = "4.21.0"
    }
    sops = {
      source  = "carlpett/sops"
      version = "1.0.0"
    }
  }

  backend "local" {
    path = "terraform.tfstate"
  }

  required_version = ">= 1.6.0"
}

provider "sops" {}

provider "cloudflare" {
  api_token = data.sops_file.cloudflare_credentials.data["api_token"]
}

locals {
  account_id = "527a1646886baabcff6c203136861c85"
}

data "sops_file" "cloudflare_credentials" {
  source_file = "${path.module}/secrets/credentials.sops.yaml"
}
