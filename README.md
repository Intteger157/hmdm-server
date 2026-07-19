# Headwind MDM - a platform for corporate Android applications

Headwind MDM is a Mobile Device Management platform for Android devices, designed for corporate app developers and IT managers.

(c) 2020 h-mdm.com

[https://h-mdm.com](https://h-mdm.com)

## This fork

Public fork: [https://github.com/Intteger157/hmdm-server](https://github.com/Intteger157/hmdm-server)

Companion Remote (Janus / web-admin) repo:
[https://github.com/Intteger157/h-mdm-remote-control](https://github.com/Intteger157/h-mdm-remote-control)

Compared to stock upstream, this tree packages extra server plugins into the WAR and includes launcher / remote-control agent sources for a full self-hosted stack (MDM + Remote on one VPS via HAProxy).

### Plugins bundled in the custom WAR

Building `server` packages these plugins into `server/target/launcher.war` (see `server/pom.xml` / `plugins/pom.xml`):

| Plugin | What it does |
|--------|----------------|
| `deviceremote` | Unattended remote control (opens Headwind Remote viewer) |
| `devicelocation` | Device location on the map |
| `devicereset` | Factory reset, reboot, lock / unlock |
| `deviceinfo` | Extended device information |
| `deviceinventory` | Hardware / inventory reporting |
| `devicelog` | Application / device logs in the panel |
| `push` | Push / MQTT-related delivery |
| `messaging` | Messaging |
| `audit` | Audit trail |
| `platform` / `xtra` | Plugin platform + extras |

Yes: **one Maven build of this repository produces a WAR with Remote, Location, Reboot/Lock/Reset, and the other plugins above** — you do not need to assemble plugins by hand.

Related sources in this repo (not inside the WAR itself):

| Path | Purpose |
|------|---------|
| `plugins/deviceremote/` | MDM plugin UI + REST |
| `plugins/deviceremote/apuppet-android/` | Android remote agent (`com.hmdm.control`) |
| `android-launcher/` | Launcher with remote-control integration |
| `plugins/deviceremote/h-mdm-remote-control/` | Optional local checkout of the Remote server (prefer the [standalone GitHub repo](https://github.com/Intteger157/h-mdm-remote-control)) |

## Features

 - Enrollment to Android 7+ devices through scanning a QR-code
 - Work in "Application mode" without enrollment
 - Customize the mobile desktop design and available applications
 - Automatic deployment of applications through the web panel
 - Mobile device management: groups, configurations, device status
 - Setup the available mobile device capabilities (GPS, Wi-Fi, Bluetooth etc.)
 - Manage the automatic OS update mode on the mobile device
 - Extensible platform design allowing the custom plugin development
 - Collection of application logs in the web panel
 - Centralized configuration of corporate applications

The *Enterprise edition* of the platform has more features:

 - Restriction of mobile user functions ("kid's shell" for corporate users)
 - Disable to change the mobile device settings
 - Kiosk mode (COSU, single-task mode)
 - Sending images from mobile device to server
 - Cloud-based or self-hosted server setup
 - Premium support of enterprise users
 - Custom plugin development services

The enterprise edition may be ordered on the [project website](https://h-mdm.com).

## Quick start

Headwind MDM control panel is cross-platform (it is written in Java and uses Tomcat web server). However the best OS for the deployment of Headwind MDM control panel is Ubuntu Linux.

 - Clone the project and build it (see [Build custom WAR](#build-custom-war) below, or `BUILD.txt`)
 - Install the web panel to the server (installer script or Docker)
 - Open the web panel and follow the hints to generate a QR code
 - Perform the factory reset on your Android device, tap 7 times on the welcome screen
 - Follow the instructions to scan a QR code and enroll the mobile agent

## Build custom WAR

On a build machine or on the MDM VPS (Docker Maven avoids installing JDK/Maven on the host):

```bash
git clone https://github.com/Intteger157/hmdm-server.git
cd hmdm-server

docker run --rm \
  -v "$(pwd)":/usr/src/mymaven \
  -v "$HOME/.m2":/root/.m2 \
  -w /usr/src/mymaven \
  maven:3.8.6-openjdk-11 \
  mvn clean package -pl server -am -DskipTests
```

Artifact:

```text
server/target/launcher.war
```

### Deploy into Headwind MDM Docker

Typical layout: sources in `~/hmdm-server`, compose project in `~/hmdm-docker` with `volumes/webapps/ROOT.war` mounted into Tomcat.

```bash
cd ~/hmdm-docker
docker compose stop hmdm
rm -rf volumes/webapps/ROOT
cp ~/hmdm-server/server/target/launcher.war volumes/webapps/ROOT.war
chmod 644 volumes/webapps/ROOT.war
docker compose start hmdm
```

After boot, open **Plugins** — bundled plugins should register (Liquibase). Enable what you need.

Without Docker, see classic Tomcat steps in `BUILD.txt` / `INSTALL.txt`.

## MDM + Remote on one server (HAProxy)

Use **two DNS names** on one public IP, for example:

- `mdm.example.com` → Headwind MDM (Tomcat behind Docker)
- `remote.example.com` → Headwind Remote (Janus + web-admin)

```
Internet :80 / :443
        │
   HAProxy (TLS terminate)
        │
        ├── Host: remote.* ──► https://127.0.0.1:9443  (Remote docker nginx)
        └── Host: mdm.*    ──► https://127.0.0.1:8443  (MDM docker Tomcat)
```

Janus still needs public `8089/tcp`, `8989/tcp`, and UDP `10000-10500`.

### 1. Install / run MDM (Docker)

Follow your usual `hmdm-docker` setup so MDM HTTPS listens on **localhost only**, e.g. `127.0.0.1:8443` (not public `:443`). Deploy the custom WAR from this repo (section above).

### 2. Install Headwind Remote

```bash
git clone https://github.com/Intteger157/h-mdm-remote-control.git
cd h-mdm-remote-control
```

In `config.yaml` (when MDM already owns public 443 / HAProxy will own 80+443):

```yaml
---
hostname: "remote.example.com"
email: "admin@example.com"
web_https_port: 9443
web_http_listen: "127.0.0.1:8080"
nat: true
public_ip: "YOUR.PUBLIC.IP"
```

```bash
sudo ./install.sh
cat deploy/dist/credentials/janus_api_secret
```

### 3. Enable HAProxy single-port edge

Scripts live in the Remote repo:

```bash
cd ~/h-mdm-remote-control
cp scripts/single-port/config.env.example scripts/single-port/config.env
nano scripts/single-port/config.env   # REMOTE_DOMAIN, MDM_DOMAIN, paths, email
chmod +x scripts/single-port/*.sh
sudo scripts/single-port/setup-single-port.sh
```

Already on nginx stream SNI? Migrate:

```bash
sudo scripts/single-port/migrate-nginx-to-haproxy.sh
```

Full details: [h-mdm-remote-control / scripts/single-port/README.md](https://github.com/Intteger157/h-mdm-remote-control/blob/master/scripts/single-port/README.md)

### 4. Wire Remote into MDM

1. **Plugins → Remote control → Settings**
   - URL: `https://remote.example.com/web-admin/` (no `:9443` when HAProxy fronts 443)
   - Secret: value from `janus_api_secret`
2. Roll out launcher + `com.hmdm.control` with the same server URL/secret (or set via configuration).

More Remote install notes: [INSTALL-UBUNTU.md](https://github.com/Intteger157/h-mdm-remote-control/blob/master/INSTALL-UBUNTU.md)

## Contributing

Headwind MDM is a platform making corporate app development easier. We are happy to get more powerful plugins related to mobile device management.

Please contact us on the [project website](https://h-mdm.com) if you'd like to:

 - develop a public plugin for Headwind MDM
 - suggest a feature
 - order the custom development
 - report a bug
