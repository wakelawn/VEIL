# VEIL — SQL Analyzer & Optimizer

> A Java-based SQL analysis engine that parses SQL queries, builds execution plans, detects inefficiencies, and applies optimization techniques.

---

## 🧠 Backend Architecture

VEIL processes every SQL query through a multi-stage pipeline:

```text
SQL Query
    │
    ▼
┌──────────────┐
│    Lexer     │  SQL → Tokens
└──────┬───────┘
       ▼
┌──────────────┐
│    Parser    │  Tokens → AST
└──────┬───────┘
       ▼
┌──────────────┐
│   Semantic   │  Validate meaning
│   Analyzer   │
└──────┬───────┘
       ▼
┌──────────────┐
│ Query Planner│  AST → Logical Plan
└──────┬───────┘
       ▼
┌──────────────┐
│  Optimizer   │  Improve query plan
└──────┬───────┘
       ▼
┌──────────────┐
│ Cost Model   │  Estimate execution cost
└──────┬───────┘
       ▼
┌──────────────┐
│    EXPLAIN   │  Human-readable plan
└──────┬───────┘
       ▼
     JSON API
       │
       ▼
HTML + CSS + JavaScript
```

---

# 📁 Backend Structure

```text
backend/
└── src/
    ├── main/
    │   └── java/
    │       └── com/
    │           └── veil/
    │
    │               ├── VeilApplication.java
    │               │
    │               ├── lexer/
    │               ├── parser/
    │               ├── ast/
    │               ├── semantic/
    │               ├── catalog/
    │               ├── planner/
    │               ├── optimizer/
    │               ├── cost/
    │               ├── explain/
    │               ├── api/
    │               ├── service/
    │               ├── model/
    │               └── exception/
    │
    └── test/
```

---

# 🔤 Lexer

### `TokenType.java`

Defines all tokens understood by VEIL.

```text
SELECT
FROM
WHERE
JOIN
GROUP_BY
ORDER_BY
IDENTIFIER
NUMBER
STRING
EQUALS
GREATER_THAN
LESS_THAN
COMMA
SEMICOLON
...
```

**Responsibility:** Define the SQL vocabulary.

---

### `Token.java`

Represents a single token.

```text
Token
├── type
├── value
└── position
```

Example:

```text
IDENTIFIER("users", 14)
```

**Responsibility:** Store token information and source position.

---

### `Lexer.java`

Converts raw SQL into tokens.

```text
SELECT name FROM users
        ↓
SELECT
IDENTIFIER(name)
FROM
IDENTIFIER(users)
```

**Responsibility:**

- Tokenize SQL
- Recognize keywords
- Recognize identifiers
- Recognize literals
- Recognize operators
- Track source positions
- Handle whitespace/comments

---

# 🌳 Parser

### `Parser.java`

Converts tokens into an Abstract Syntax Tree.

```text
Tokens
   ↓
Parser
   ↓
AST
```

Example:

```sql
SELECT name
FROM users
WHERE age > 18;
```

Produces:

```text
SelectStatement
├── ColumnReference(name)
├── TableReference(users)
└── BinaryExpression
    ├── age
    ├── >
    └── 18
```

**Responsibility:** Understand SQL grammar and construct the AST.

---

### `ParseException.java`

Represents SQL syntax errors.

Example:

```text
SELECT FROM users

ERROR:
Expected column after SELECT
```

**Responsibility:** Provide meaningful parser errors.

---

# 🌲 AST

The AST is the structured representation of the SQL query.

---

### `ASTNode.java`

Base abstraction for all AST nodes.

```text
ASTNode
├── Statement
├── Expression
├── Join
└── ...
```

**Responsibility:** Common interface for AST elements.

---

### `Statement.java`

Base abstraction for SQL statements.

Future structure:

```text
Statement
├── SelectStatement
├── InsertStatement
├── UpdateStatement
└── DeleteStatement
```

**Responsibility:** Represent executable SQL statements.

---

### `SelectStatement.java`

Represents a complete `SELECT` query.

Contains:

```text
columns
from
joins
where
groupBy
orderBy
limit
```

**Responsibility:** Store the complete SELECT query structure.

---

### `Expression.java`

Base abstraction for SQL expressions.

Examples:

```text
age > 18
salary * 2
name = 'Raj'
COUNT(id)
```

**Responsibility:** Represent SQL expressions.

---

### `BinaryExpression.java`

Represents expressions with two operands.

```text
age > 18
```

becomes:

```text
BinaryExpression
├── left: age
├── operator: >
└── right: 18
```

**Responsibility:** Represent comparisons and logical/arithmetic operations.

---

### `LiteralExpression.java`

Represents constant values.

```text
18
50000
"Raj"
true
NULL
```

**Responsibility:** Store literal values.

---

### `ColumnReference.java`

Represents a column reference.

```text
users.name
```

Stores:

```text
table = users
column = name
```

**Responsibility:** Represent column access.

---

### `TableReference.java`

Represents a table reference.

```sql
FROM users u
```

Stores:

```text
table = users
alias = u
```

**Responsibility:** Represent tables and aliases.

---

### `JoinClause.java`

Represents SQL joins.

```sql
JOIN orders
ON users.id = orders.user_id
```

Stores:

```text
joinType
leftTable
rightTable
condition
```

**Responsibility:** Represent JOIN operations.

---

### `WhereClause.java`

Represents the `WHERE` condition.

```sql
WHERE age > 18
```

**Responsibility:** Store filtering expressions.

---

### `GroupByClause.java`

Represents:

```sql
GROUP BY department
```

**Responsibility:** Store grouping expressions.

---

### `OrderByClause.java`

Represents:

```sql
ORDER BY salary DESC
```

**Responsibility:** Store sorting expressions and direction.

---

# 🧩 Semantic Analysis

Parsing checks syntax.

Semantic analysis checks whether the query **makes sense**.

---

### `SemanticAnalyzer.java`

Validates:

- Tables
- Columns
- Aliases
- Data types
- Joins
- Expressions
- Aggregations
- `GROUP BY`

Example:

```sql
SELECT fake_column
FROM users;
```

Result:

```text
Syntax:     ✓ Valid
Semantics:  ✗ Unknown column 'fake_column'
```

**Responsibility:** Validate query meaning.

---

### `SymbolTable.java`

Tracks identifiers available during query analysis.

```text
users
├── id
├── name
└── age

orders
├── id
├── user_id
└── total
```

Also tracks aliases:

```text
u → users
o → orders
```

**Responsibility:** Resolve tables, columns, and aliases.

---

### `TypeChecker.java`

Validates expression types.

Example:

```sql
WHERE age > 'hello'
```

Potential result:

```text
Type Error:
INTEGER cannot be compared with STRING
```

**Responsibility:** Perform type validation.

---

# 🗄️ Catalog

The catalog represents the database metadata known to VEIL.

---

### `Schema.java`

Represents a database schema.

```text
Schema
├── users
├── orders
└── products
```

**Responsibility:** Manage database-level metadata.

---

### `Table.java`

Represents table metadata.

```text
users
├── rows: 100000
├── id
├── name
├── age
└── email
```

**Responsibility:** Store table information and statistics.

---

### `Column.java`

Represents column metadata.

```text
name
type
nullable
cardinality
```

**Responsibility:** Describe individual columns.

---

### `Index.java`

Represents available indexes.

```text
users(age)
orders(user_id)
```

**Responsibility:** Provide index information to the optimizer and cost model.

---

# 📐 Query Planner

---

### `QueryPlanner.java`

Converts the AST into a logical query plan.

```text
AST
 ↓
QueryPlanner
 ↓
LogicalPlan
```

Example:

```sql
SELECT name
FROM users
WHERE age > 18;
```

becomes:

```text
Projection(name)
      │
      ▼
Filter(age > 18)
      │
      ▼
TableScan(users)
```

**Responsibility:** Translate SQL meaning into relational operations.

---

### `LogicalPlan.java`

Represents what the query wants to accomplish without committing to a physical execution strategy.

**Responsibility:** Store the logical relational plan.

---

### `PhysicalPlan.java`

Represents how the query should actually execute.

Example:

```text
IndexScan(users_age_idx)
```

instead of:

```text
TableScan(users)
```

Possible operations:

```text
TableScan
IndexScan
Filter
Projection
HashJoin
NestedLoopJoin
Sort
Aggregate
```

**Responsibility:** Represent executable strategies.

---

### `PlanNode.java`

Base abstraction for query-plan operations.

```text
PlanNode
├── TableScan
├── IndexScan
├── Filter
├── Projection
├── HashJoin
├── Sort
└── Aggregate
```

**Responsibility:** Provide the common structure for execution-plan nodes.

---

# ⚡ Optimizer

The optimizer transforms an expensive plan into a more efficient one.

---

### `Optimizer.java`

Controls the optimization pipeline.

```text
Logical Plan
     ↓
Optimization Rules
     ↓
Optimized Plan
```

**Responsibility:** Apply optimization rules and produce the best available plan.

---

### `OptimizationRule.java`

Common interface for optimizer rules.

```text
OptimizationRule
      ↓
      ├── PredicatePushdown
      ├── ProjectionPruning
      ├── ConstantFolding
      └── JoinReordering
```

**Responsibility:** Define a common optimization-rule contract.

---

### `PredicatePushdown.java`

Moves filters closer to the underlying table.

Before:

```text
Join
 ↓
Filter
```

After:

```text
Join
├── Filter
│   └── Scan
└── Scan
```

**Goal:** Reduce the number of rows processed by later operators.

---

### `ProjectionPruning.java`

Removes columns that aren't required.

```text
SELECT name
```

Instead of reading:

```text
id
name
age
email
address
phone
```

the plan attempts to read only:

```text
name
```

**Goal:** Reduce unnecessary data processing.

---

### `ConstantFolding.java`

Evaluates constant expressions during optimization.

```text
10 + 8
   ↓
18
```

**Goal:** Remove unnecessary runtime computation.

---

### `JoinReordering.java`

Attempts to choose a more efficient join order.

```text
A JOIN B JOIN C
```

may become:

```text
A JOIN C JOIN B
```

depending on estimated costs.

**Goal:** Reduce intermediate result sizes and join cost.

---

# 💰 Cost Model

---

### `CostEstimator.java`

Estimates the cost of a query plan.

Factors can include:

```text
Rows
CPU
I/O
Memory
Selectivity
Join cardinality
Indexes
Sorting
```

Example:

```text
TableScan = 500
Filter    = 20
HashJoin  = 200

Total = 720
```

**Responsibility:** Estimate plan execution cost.

---

### `Statistics.java`

Stores data used by the cost estimator.

Example:

```text
users
rows = 1,000,000

age
distinct values = 80

orders
rows = 5,000,000
```

**Responsibility:** Provide table and column statistics.

---

### `Cost.java`

Represents calculated execution cost.

```text
Cost
├── cpuCost
├── ioCost
├── memoryCost
└── totalCost
```

**Responsibility:** Encapsulate cost calculations.

---

# 📋 EXPLAIN

---

### `ExplainGenerator.java`

Converts the physical plan into human-readable output.

Example:

```text
Hash Join
├── Index Scan: users
└── Filter: orders.total > 1000
    └── Table Scan: orders
```

**Responsibility:** Generate EXPLAIN information.

---

### `PlanFormatter.java`

Formats execution plans into different representations.

Possible formats:

```text
TEXT
TREE
JSON
```

**Responsibility:** Convert internal plans into frontend-friendly representations.

---

# 🌐 API

---

### `QueryController.java`

Receives requests from the frontend.

```text
POST /api/analyze
```

Request:

```json
{
  "query": "SELECT name FROM users WHERE age > 18"
}
```

**Responsibility:** HTTP/API layer only.

It should NOT contain optimizer logic.

---

### `HealthController.java`

Provides:

```text
GET /api/health
```

Response:

```json
{
  "status": "UP"
}
```

**Responsibility:** Check whether the VEIL backend is running.

---

# ⚙️ Services

---

### `QueryAnalysisService.java`

The central orchestrator.

```text
SQL
 ↓
Lexer
 ↓
Parser
 ↓
Semantic Analyzer
 ↓
Query Planner
 ↓
Optimizer
 ↓
Cost Estimator
 ↓
EXPLAIN
 ↓
AnalysisResult
```

**Responsibility:** Coordinate the complete SQL-analysis pipeline.

---

### `OptimizationService.java`

Handles optimization operations.

It can compare:

```text
Original Plan
       ↓
Optimization
       ↓
Optimized Plan
```

and calculate:

```text
Before: 840
After:  320

Improvement: 61.9%
```

**Responsibility:** Manage optimization and plan comparison.

---

# 📦 Models

---

### `QueryRequest.java`

Represents data received from the frontend.

```json
{
  "query": "SELECT * FROM users"
}
```

**Responsibility:** Request DTO.

---

### `QueryResponse.java`

Represents data returned to the frontend.

```text
valid
complexity
cost
originalPlan
optimizedPlan
suggestions
explain
```

**Responsibility:** API response DTO.

---

### `AnalysisResult.java`

Contains the complete result of SQL analysis.

```text
AST
Logical Plan
Physical Plan
Cost
Suggestions
Explanation
```

**Responsibility:** Store the complete analysis result.

---

### `OptimizationSuggestion.java`

Represents one optimization recommendation.

Example:

```text
Title:
Missing Index

Description:
Consider adding an index on users.age.

Impact:
HIGH
```

**Responsibility:** Represent actionable optimization advice.

---

# 🚨 Exceptions

---

### `InvalidQueryException.java`

Represents invalid SQL or analysis failures.

Examples:

```text
Invalid syntax
Unknown table
Unknown column
Invalid expression
Type mismatch
```

**Responsibility:** Represent expected query-processing errors.

---

### `GlobalExceptionHandler.java`

Converts backend exceptions into clean API responses.

Instead of:

```text
500 NullPointerException
```

the frontend receives:

```json
{
  "error": "Column 'agee' does not exist",
  "position": 42
}
```

**Responsibility:** Centralized API error handling.

---

# 🔄 Complete Processing Flow

```text
                    SQL QUERY
                        │
                        ▼
                  ┌──────────┐
                  │  Lexer   │
                  └────┬─────┘
                       │
                    Tokens
                       │
                       ▼
                  ┌──────────┐
                  │  Parser  │
                  └────┬─────┘
                       │
                      AST
                       │
                       ▼
             ┌──────────────────┐
             │ Semantic Analyzer │
             └────────┬─────────┘
                      │
                      ▼
               Logical Plan
                      │
                      ▼
                ┌───────────┐
                │ Optimizer │
                └─────┬─────┘
                      │
                      ▼
               Physical Plan
                      │
             ┌────────┴────────┐
             ▼                 ▼
       Cost Estimator      EXPLAIN
             │                 │
             └────────┬────────┘
                      ▼
                Analysis Result
                      │
                      ▼
                  JSON API
                      │
                      ▼
               HTML/CSS/JS
```

---

# 🎯 Core Design Principle

Each layer should have **one responsibility**:

| Layer     | Responsibility            |
| --------- | ------------------------- |
| Lexer     | SQL → Tokens              |
| Parser    | Tokens → AST              |
| AST       | Represent SQL structure   |
| Semantic  | Validate SQL meaning      |
| Catalog   | Store database metadata   |
| Planner   | AST → Logical Plan        |
| Optimizer | Improve the plan          |
| Cost      | Estimate execution cost   |
| Explain   | Explain the plan          |
| Service   | Coordinate the pipeline   |
| API       | Communicate with frontend |
| Model     | Transfer data             |
| Exception | Handle errors             |

The most important rule:

> **The API should never contain SQL parsing or optimization logic.**

The backend should behave as a clean pipeline:

```text
INPUT
  ↓
PARSE
  ↓
UNDERSTAND
  ↓
PLAN
  ↓
OPTIMIZE
  ↓
ESTIMATE
  ↓
EXPLAIN
  ↓
OUTPUT
```
