Responsible to understand the SQL querries in depth and parse them: SQL → Lexer → Tokens → Parser → SelectStatement (AST)

What handles:

- SELECT
- Columns
- Numbers
- FROM
- Table
- Alias
- u.name
- WHERE
- = > <
- AND / OR
- GROUP BY
- ORDER BY
- ASC / DESC
- JOIN
- EOF validation

Current not addressed advance sql

- COUNT(\*)
- SUM(salary)
- IN (...)
- BETWEEN ...
- LIKE ...
- NOT
- parentheses
- subqueries
- HAVING
- LIMIT

## FLOW

                 SQL Query
                     │
                     ▼
              ┌─────────────┐
              │   Lexer     │
              │             │
              │ SQL → Tokens │
              └──────┬──────┘
                     │
                     ▼
              ┌─────────────┐
              │   Parser    │
              │             │
              │ Tokens → AST │
              └──────┬──────┘
                     │
                     ▼
               SelectStatement
                     │
        ┌────────────┼────────────┐
        │            │            │
        ▼            ▼            ▼
     Columns       FROM         JOIN
        │            │            │
        ▼            ▼            ▼

Expressions TableRef JoinClause
│
├── ColumnReference
├── LiteralExpression
└── BinaryExpression
│
▼
WHERE
│
▼
BinaryExpression
│
┌──────┴──────┐
▼ ▼
left right
│ │
age 18

                     │
                     ▼
                  GROUP BY
                     │
                     ▼
                GroupByClause

                     │
                     ▼
                  ORDER BY
                     │
                     ▼
                OrderByClause
