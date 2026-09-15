# Lexer — Token & TokenType

## What the Lexer Does

The `Lexer` scans raw SQL text and breaks it into a list of `Token` objects. Each token carries:

- `type` (`TokenType`) — what kind of symbol it is
- `value` (`String`) — the actual text from the input
- `position` (`int`) — where it appeared in the source

This is the first stage of the VEIL pipeline: **text → tokens → AST**.

---

## TokenType (enum)

Defines every category of symbol the lexer recognizes.

| TokenType      | Meaning             | Example in SQL         |
| -------------- | ------------------- | ---------------------- |
| `SELECT`       | SQL keyword         | `SELECT`               |
| `FROM`         | SQL keyword         | `FROM`                 |
| `WHERE`        | SQL keyword         | `WHERE`                |
| `IDENTIFIER`   | Table / column name | `users`, `age`, `name` |
| `NUMBER`       | Numeric literal     | `18`, `42`             |
| `STRING`       | String literal      | `'hello'`              |
| `GREATER_THAN` | `>` operator        | `>`                    |
| `EQUALS`       | `=` operator        | `=`                    |
| `LESS_THAN`    | `<` operator        | `<`                    |
| `COMMA`        | `,` separator       | `,`                    |
| `SEMICOLON`    | `;` terminator      | `;`                    |

---

## Token (class)

A single lexical unit produced by the lexer.

### Fields

- `type` (`TokenType`) — category of the token
- `value` (`String`) — the raw text (e.g., `"SELECT"`, `"users"`, `"18"`)
- `position` (`int`) — index in the input string where the token starts

### Methods

- `getType()` — returns `TokenType`
- `getValue()` — returns the text value
- `getPosition()` — returns the start index
- `toString()` — pretty-prints the token for debugging

---

## How They Work Together

1. `Lexer.tokenize("SELECT age FROM users WHERE age > 18;")` runs.
2. It skips whitespace.
3. It reads words (`SELECT`, `age`, `FROM`, `users`, `WHERE`, `age`) and creates `Token(TokenType.SELECT, "SELECT", 0)`, etc.
4. It reads symbols (`>`, `;`) and creates `Token(TokenType.GREATER_THAN, ">", ...)`.
5. The result is a `List<Token>` that the `Parser` turns into an AST.
