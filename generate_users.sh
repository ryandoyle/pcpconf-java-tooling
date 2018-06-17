#!/usr/bin/env bash
while true; do
    curl -XPOST -H "Content-Type: application/json" -d '{"name": "My Name"}' http://localhost:8080/customer
    echo ""
done