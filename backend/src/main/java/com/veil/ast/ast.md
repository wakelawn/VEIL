SELECT name, salary
FROM employees e
WHERE salary > 50000
GROUP BY department
ORDER BY salary DESC;

All implimented

SelectStatement
│
├── columns
│   ├── u.name
│   └── o.total
│
├── from
│   └── users u
│
├── joins
│   └── JoinClause
│       ├── INNER
│       ├── orders o
│       └── u.id = o.user_id
│
├── where
│   └── o.total > 1000
│
├── groupBy
│   └── none
│
└── orderBy
    └── o.total DESC



                     ASTNode
                /       \
               /         \
        Statement      Expression
             │              │
             ▼              ▼
     SelectStatement   BinaryExpression
                       ColumnReference
                       LiteralExpression

                       # AST — Abstract Syntax Tree

The AST is the structured representation of an SQL query.

After the Lexer converts SQL into tokens, the Parser will convert those
tokens into AST objects.

## SQL Processing

SQL Query
   ↓
Lexer
   ↓
Tokens
   ↓
Parser
   ↓
AST
   ↓
Semantic Analysis
   ↓
Query Planning
   ↓
Optimization

---