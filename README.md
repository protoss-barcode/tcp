# Testing Clojure Project

Goals:

- Stronger behavioral tests against clojure and common clojure libraries.
- Improve clojure docstrings with complete sentences, examples, and documented edge cases.

## org

### clojure

#### core

Bugs:

- No type predicate function `long?` for `long` type coercion function.
- No type predicate function `short?` for `double` type coercion function.
- `float?` type predicate function returns `true` for `double` type coercion function.
- No type predicate function `bigdec?` for `bigdec` type coercion function. `decimal?` is misnamed.
- No type predicate function `biginteger?` for `biginteger` type coercion function.
- No type predicate function `bigint?` for `bigint` type coercion function.
- `*` and `*'` calculate values above `Double/MAX_VALUE` as `Double/POSITIVE_INFINITY`
