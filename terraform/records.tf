resource "cloudflare_record" "dai_root" {
  zone_id = data.cloudflare_zone.main.id
  type    = "A"
  name    = "dai.heig"
  value   = "185.144.38.63"
  proxied = false
}

resource "cloudflare_record" "whoami" {
  zone_id = data.cloudflare_zone.main.id
  type    = "A"
  name    = "whoami.dai.heig"
  value   = "185.144.38.63"
  proxied = false
}

resource "cloudflare_record" "api" {
  zone_id = data.cloudflare_zone.main.id
  type    = "A"
  name    = "api.dai.heig"
  value   = "185.144.38.63"
  proxied = false
}
