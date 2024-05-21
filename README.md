# Spring Boot "Anti-Fraud-System" learning project

This is a Spring Boot(ver 3.2.0) service for validating transactions based on a simple and arbitrary set of rules.

## About this project
This is a RESTful API for validating and storing transactions. Users with MERCHANT roles can submit a transaction
request, which can be evaluated to ALLOWED, PROHIBITED, or MANUAL PROCESSING depending on the heuristics implemented. 
Heuristics consider properties such as transactions limits, transaction frequency, region, etc. Users with SUPPORT 
roles can override any types of transaction.

## Requirements



