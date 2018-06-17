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

### Demo 2 - Application-based
Start the app if it's not started
```bash
./gradlew startWithoutAgent
```


### Demo 3 - Graphite/Grafana
Start the app if it's not started
```bash
./gradlew startWithoutAgent
```

Start the containers
```bash
docker-compose up -d
```
Start publishing metrics
```bash
./pcp2graphite.py -t 1sec -h localhost mmv.pcpcoin
```

Start Gatling
```bash
./gradlew gatlingRun
```