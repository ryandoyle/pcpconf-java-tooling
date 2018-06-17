#!/usr/bin/env bash
while true; do
    curl -XPOST -H "Content-Type: application/json" -d '{"from": 123, "to": 456, "amount": 4500}' http://localhost:8080/transaction
    echo ""
done
