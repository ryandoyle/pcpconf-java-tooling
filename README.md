# Java tooling demo for PCPCONF2018

### Demo1 - Agent
Start the sample application with the agent. The Gradle command will block until this is SIGINT'ed
```bash
./gradlew startWithAgent
```
Check what metrics are available
```bash
pminfo mmv.pcpcoin
```

Start monitoring basic metrics
```bash
pmchart -c pmchart-views/demo1 & 
```
Start the load test
```bash
./gradlew gatlingRun
```

### Demo 2.1 - Basic metrics
Start the app
```bash
./gradlew startWithoutAgent
```
Start the load test
```bash
./gradlew gatlingRun
```
Check what metrics are available
```bash
pminfo -f mmv.pcpcoin.customer.new
```
View the metric
```bash
pmchart -c pmchart-views/demo2 & 
```

### Demo 2.2 - Advanced Metrics


### Demo 2.3 - Integrations


### Demo 3 - Graphite/Grafana
Start the app and load test
```bash
./gradlew startWithoutAgent
./gradlew gatlingRun
```

Setup monitoring infrastructure
```bash
docker-compose up -d
sleep 10
./configure_grafana.sh
./pcp2graphite.py -t 1sec -h localhost mmv.pcpcoin & 

```
Import the dashboard
* Open http://localhost:3030
* Login with admin/secret
* Click on the menu icon in the top left corner and navigate to **Dashboards** > **Import**
* Upload `grafana_dashboard.json` in the project root dir 