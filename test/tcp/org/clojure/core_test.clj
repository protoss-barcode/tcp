(ns tcp.org.clojure.core-test
  "clojure.core with real tests"
  (:require [clojure.test :refer :all]
            [tcp.org.clojure.core :as core]))

(deftest test-*
  (testing "`(*)` returns 1"
    (is (= 1 (core/*))))
  (testing "`(* x)` returns `x`"
    (is (= 1 (core/* 1))
        "Bare long")
    (is (= (long 1) (core/* (long 1)))
        "Coerced long")
    (is (= (int 1) (core/* (int 1)))
        "Coerced int")
    (is (= (short 1) (core/* (short 1)))
        "Coerced short")
    (is (= (num 1) (core/* (num 1)))
        "Coerced num")
    (is (= (/ 22 7) (core/* (/ 22 7)))
        "Ratio")
    (is (= 1.0 (core/* 1.0))
        "Bare double")
    (is (= (float 1.0) (core/* (float 1.0)))
        "Coerced float")
    (is (= (double 1.0) (core/* (double 1.0)))
        "Coerced double")
    (is (= (bigdec 1.0) (core/* (bigdec 1.0)))
        "Coerced bigdec")
    (is (= (bigint 1) (core/* (bigint 1)))
        "Coerced bigint")
    (is (= (biginteger 1) (core/* (biginteger 1)))
        "Coerced biginteger")
    (testing " and performs no type casting"
      ;; BUG: `long?` is not present in `clojure.core`
      (is (instance? java.lang.Long (core/* 1)))
      (is (instance? java.lang.Long (core/* (long 1))))
      (is (int? (core/* (int 1))))
      ;; BUG: `short?` is not present in `clojure.core`
      (is (instance? java.lang.Short (core/* (short 1))))
      (is (number? (core/* (num 1))))
      (is (ratio? (core/* (/ 22 7))))
      (is (double? (core/* 1.0)))
      (is (double? (core/* (double 1.0))))
      ;; BUG: `float?` returns `true` for `double` values
      (is (float? (core/* (float 1.0))))
      (is (instance? java.lang.Float (core/* (float 1.0))))
      ;; BUG: `bigdec?` is not present in `clojure.core`
      ;;      `decimal?` performs the expected check at unexpected name
      (is (decimal? (core/* (bigdec 1.0))))
      ;; BUG: `biginteger?` is not present in `clojure.core`
      (is (instance? java.math.BigInteger (core/* (biginteger 1))))
      ;; BUG: `bigint?` is not present in `clojure.core`
      (is (instance? clojure.lang.BigInt (core/* (bigint 1))))))
  (testing "`(* x y)` returns the product of `x` and `y`"
    (is (= 6 (core/* 2 3)))
    (is (= 6.0 (core/* 2 3.0)))
    (is (= 6.0 (core/* 2.0 3)))
    (is (= 6.0 (core/* 2.0 3.0)))
    (is (= (bigdec 6.0) (core/* 2 (bigdec 3.0)))
        "bigdec")
    (is (= (bigint 6) (core/* 2 (bigint 3)))
        "bigint")
    (is (= (biginteger 6) (core/* 2 (biginteger 3))))
    (is (= 1 (core/* (/ 22 7) (/ 7 22)))
        "Ratio coerced to long")
    (is (= (/ 1 4) (core/* (/ 1 2) (/ 1 2)))
        "Ratio type preserved")
    (is (= 1.0 (core/* 2.0 (/ 1 2)))
        "Double type preserved over ratio"))
  (testing "`(* x y & more)` returns the product of all arguments"
    (is (= 6 (core/* 1 2 3)))
    (is (= 6.0 (core/* 1 2 3.0)))
    (is (= 6.0 (core/* 1 2.0 3)))
    (is (= 6.0 (core/* 1 2.0 3.0)))
    (is (= (bigdec 6.0) (core/* 1 2 (bigdec 3.0)))
        "bigdec")
    (is (= (bigint 6) (core/* 1 2 (bigint 3)))
        "bigint")
    (is (= (biginteger 6) (core/* 1 2 (biginteger 3))))
    (is (= 1 (core/* 1 (/ 22 7) (/ 7 22)))
        "Ratio coerced to long")
    (is (= (/ 1 4) (core/* 1 (/ 1 2) (/ 1 2)))
        "Ratio type preserved")
    (is (= 1.0 (core/* 1 2.0 (/ 1 2)))
        "Double type preserved over ratio")
    (is (= 1 (apply core/* (take 100 (repeat 1))))
        "Arity 3+"))
  (testing "Exceptional behavior"
    (is (thrown? ArithmeticException (core/* Long/MAX_VALUE 2)))
    (is (thrown? ClassCastException (core/* "foo")))
    (is (thrown? ClassCastException (core/* 1 "a")))
    (is (thrown? ClassCastException (core/* :bar)))))
