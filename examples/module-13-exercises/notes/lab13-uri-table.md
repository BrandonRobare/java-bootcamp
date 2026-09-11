# Lab 13 - Design the Resource URI Table

## Name the Nouns

Resources are plural nouns taken from the CRM domain (`Customer` entity, `CustomerStatus` enum):

- `customers`: top-level collection
- `interactions`: nested under a single customer
- `status`: nested under a single customer (maps to `CustomerStatus`)

## URI Table

| Resource | Kind | URI |
| --- | --- | --- |
| Customer collection | collection | `/api/customers` |
| Customer item | item | `/api/customers/CUS-1001` |
| Customer interactions | sub-resource | `/api/customers/CUS-1001/interactions` |
| Customer status | sub-resource | `/api/customers/CUS-1001/status` |

Fixtures: `CUS-1001` Amina Khan ACTIVE · `CUS-1002` Ravi Singh PROSPECT · `CUS-9999` not found.

## Fix a Bad URI

Bad: `/api/customer/CUS-1001/getInteractions`

Two problems:

1. `customer` is singular: collections are plural nouns.
2. `getInteractions` is a verb in the path: the HTTP method is the verb.

Fixed: `GET /api/customers/CUS-1001/interactions`

Predict: with `/getCustomer` and `/updateCustomer`, delete and search each add another verb path (`/deleteCustomer`, `/searchCustomers`) and the endpoint list grows forever. With nouns they are just `DELETE /api/customers/CUS-1001` and `GET /api/customers?status=ACTIVE`: same resource, different method.

## Scope

Design only: no controller and no OpenAPI YAML written yet.

## Scope
Pre-lab only: do not finish the full lab in this exercise.
