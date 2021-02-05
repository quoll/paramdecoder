(ns urldecoder.core-test
  (:require [clojure.test :refer :all]
            [paramdecoder.core :refer [param-decode]]))

(deftest simple-test
  (testing "Basic URL structure"
    (is (= {:a "1"}
           (param-decode "http://ex.com/path?a=1")))
    (is (= {:arg1 "value1"
            :arg2 "value2"}
           (param-decode "http://ex.com/path?arg1=value1&arg2=value2")))
    (is (= {:a "1"}
           (param-decode "http://ex.com/path?a=1#fragment=x")))
    (is (= {:arg1 "value1"
            :arg2 "value2"}
           (param-decode "http://ex.com/path?arg1=value1&arg2=value2#fragment=x")))
    (is (= {:a "1"}
           (param-decode "a=1")))
    (is (= {:arg1 "value1"
            :arg2 "value2"}
           (param-decode "arg1=value1&arg2=value2")))
    (is (= {:a "1"}
           (param-decode "a=1#fragment=x")))
    (is (= {:arg1 "value1"
            :arg2 "value2"}
           (param-decode "arg1=value1&arg2=value2#fragment=x")))))

(deftest single-test
  (testing "unusual param structure"
    (is (= {:a true}
           (param-decode "http://ex.com/path?a")))
    (is (= {:a true}
           (param-decode "http://ex.com/path?a#fragment=x")))
    (is (= {:a true}
           (param-decode "a")))
    (is (= {:arg1 true }
           (param-decode "arg1#fragment=x")))))


(deftest string-key-test
  (testing "Basic URL structure"
    (is (= {"arg1" "value1"
            "arg2" "value2"}
           (param-decode "http://ex.com/path?arg1=value1&arg2=value2" :keyfn str)))
    (is (= {"a" true}
           (param-decode "http://ex.com/path?a" :keyfn identity)))
    (is (= {"arg1" "value1"
            "arg2" "value2"}
           (param-decode "arg1=value1&arg2=value2#fragment=x" :keyfn str)))))

(deftest code-test
  (testing "unusual param structure"
    (is (= {:arg "Δελτα"}
           (param-decode "http://ex.com/path?arg=%CE%94%CE%B5%CE%BB%CF%84%CE%B1#fragment=x")))
    (is (= {:arg "Δελτα"}
           (param-decode "http://ex.com/path?%61%72%67=%CE%94%CE%B5%CE%BB%CF%84%CE%B1#fragment=x")))
    (is (= {:arg "Δελ τα"}
           (param-decode "http://ex.com/path?%61%72%67=%CE%94%CE%B5%CE%BB+%CF%84%CE%B1#fragment=x")))
    (is (= {:arg true}
           (param-decode "%61%72%67#fragment=x")))))


