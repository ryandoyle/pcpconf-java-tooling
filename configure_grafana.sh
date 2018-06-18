#!/usr/bin/env bash
curl -X POST -H "Content-Type: application/json" http://admin:secret@localhost:3000/api/datasources -d '{
  "name":"graphite",
  "type":"graphite",
  "url":"http://graphite:80",
  "access":"proxy",
  "isDefault" : true,
  "basicAuth":false
}'