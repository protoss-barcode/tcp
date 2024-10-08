(ns tcp.org.clojure.core
  "clojure.core with real docstrings")

(defn *
  "Returns the product of the arguments, with several special cases:
     - `(*)` returns 1.
     - `(* x)` Confirms that `x` is a subclass of `java.lang.Number` and returns `x`.
               Otherwise, it throws a ClassCastException.
     - `(* x y)` Returns the product of `x` and `y`.
                 This relies on the `multiply` method of `clojure.lang.Numbers`.
                 Therefore, it will imoplicitly coerce the return value to the most precise type.

   Additional Notes:
     - If the arguments are longs, and the result exceeds `java.land.Long/MAX_VALUE`,
       the result will not be promoted to a `java.math.BigInteger`.
       Instead, it will throw an `ArithmeticException`."
  ([] 1)
  ([x] (cast Number x))
  ([x y] (. clojure.lang.Numbers (multiply x y)))
  ([x y & more] (reduce * (* x y) more)))
