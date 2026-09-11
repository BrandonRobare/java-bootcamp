# Lab 13 - Plan Collections and Versioning

## Query Parameters

page (default 0, 0-based), size (default 20, max 100), status as an optional filter (ACTIVE | PROSPECT, e.g. `?status=ACTIVE`), and sort as `field,direction` (e.g. `fullName,asc`, default `customerId,asc` so paging is deterministic and page 2 never repeats or skips a row). With the lab fixtures, `GET /api/v1/customers?page=0&size=20&status=ACTIVE` returns Amina (CUS-1001) only, and the unfiltered page contains both Amina and Ravi (CUS-1002, PROSPECT).

## Response Envelope

A page object, not a bare array: `content` (array of customer objects) plus `page`, `size`, `totalElements`, and `totalPages`. `totalPages` is what lets the UI know a page 2 exists without a second request: with 2 fixtures and size 20, `totalElements: 2, totalPages: 1`, so the pager shows no next button.

## Version Choice

URI versioning, `/api/v1/customers` (already in the OpenAPI contract). Reason: it is visible in the URL, so it shows up in server logs, browser tabs, and curl output for free, and both v1 and v2 endpoints can be served side by side during a transition window. A header version like `Accept: application/vnd.northstar.v2+json` hides the version from logs and makes manual testing with curl clunky.

## Breaking Change

Responses. Breaking: renaming or removing a field, or changing a field's type. Non-breaking: adding an optional field.

Requests. Breaking: adding a required field or parameter, or tightening validation on an existing one. Non-breaking: adding an optional field or query parameter.

Debugged with the `name` to `fullName` rename: any client that reads `name` breaks silently (fields come back undefined) on the renamed contract, while clients reading `fullName` work fine. That is exactly why the rename counts as breaking. If it ships inside `/api/v1`, existing v1 consumers break; the fix is either to keep `name` (and add `fullName` alongside, non-breaking) or to ship `fullName` only under `/api/v2`.

## Scope

Pre-lab only: do not finish the full lab in this exercise.
