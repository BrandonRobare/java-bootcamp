# Lab 11 AAA template — activate Ravi

## Test name (style only)
activate_prospectRavi_setsStatusActive

## AAA

| Phase | What you write |
| ----- | -------------- |
| Arrange | Customer CUS-1002 Ravi Singh status PROSPECT |
| Act | Call CustomerService.activateCustomer("CUS-1002") (conceptual — trigger updateStatus/activate) |
| Assert | Customer CUS-1002 status is ACTIVE; note correlation lab-request-001 for later logging |
