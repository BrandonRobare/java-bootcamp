# Package plan (Exercise 2)

| Type | Fully qualified name | Folder path |
| ---- | -------------------- | ----------- |
| Entity | `com.northstar.crm.entity.Customer` | `src/main/java/com/northstar/crm/entity/` |
| Request DTO | `com.northstar.crm.dto.CustomerRequest` | `src/main/java/com/northstar/crm/dto/` |
| Response DTO | `com.northstar.crm.dto.CustomerResponse` | `src/main/java/com/northstar/crm/dto/` |
| Repository | `com.northstar.crm.repository.CustomerRepository` | `src/main/java/com/northstar/crm/repository/` |
| Service | `com.northstar.crm.service.CustomerService` | `src/main/java/com/northstar/crm/service/` |
| Controller | `com.northstar.crm.controller.CustomerController` | `src/main/java/com/northstar/crm/controller/` |
| Config | `com.northstar.crm.config.AppConfig` | `src/main/java/com/northstar/crm/config/` |
| Exception | `com.northstar.crm.exception.CustomerNotFoundException` | `src/main/java/com/northstar/crm/exception/` |

## Rules

- **Package segments are lowercase.** `com.Northstar.CRM` compiles on macOS and Windows because their filesystems are case-insensitive, then fails on a Linux CI box where `Northstar/` and `northstar/` are different directories.
- **Declaration matches the folder path exactly**, `.` swapped for `/`. `package com.northstar.crm.service;` must sit in `src/main/java/com/northstar/crm/service/`.
- **No default package.** Classes in the unnamed package cannot be imported from any named package, so nothing else in the project can use them.
- **Reverse-domain root** (`com.northstar.crm`) keeps class names from colliding with a library's.
- **Package names describe a responsibility** — `repository`, `service`, `exception` — never `misc` or `utils`.

## Bad names, corrected

| Bad | Correct | Why |
| --- | ------- | --- |
| `com.Northstar.CRM.Service` | `com.northstar.crm.service` | Uppercase segments break on case-sensitive filesystems |
| `utils` holding customer business rules | `service` | `utils` names no concern, so everything drifts into it |
| `customer_service.java` | `CustomerService.java` | Classes are PascalCase; the file must match the public class name |
| Declaration says `service`, file sits in `services/` | Make both identical | Compiler rejects the mismatch under `src/main/java` |

## Pass check

- Eight FQCNs correct — Pass
- DTO path matches its declaration — Pass
- Segments lowercase and meaningful — Pass
