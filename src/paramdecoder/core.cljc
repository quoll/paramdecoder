(ns paramdecoder.core
  (:require [clojure.string :as s])
  #?(:clj (:import [java.net URLDecoder])))


(defn decode
  "Decodes strings containing URLs into strings"
  [url]
  #?(:clj (URLDecoder/decode url)
     :cljs (let [u (s/replace url "+" " ")]
             (js/decodeURIComponent u))))

(defn param-decode
  "Decodes a URL or URL query string into a parameter map"
  [url & {keyfn :keyfn :or {keyfn keyword}}]
  (let [query-fragment (if-let [qoffset (s/index-of url \?)]
                         (subs url (inc qoffset))
                         url)
        query (if-let [foffset (s/index-of query-fragment \#)]
                (subs query-fragment 0 foffset)
                query-fragment)]
    (if (s/includes? query "=")
      (reduce
       (fn [m param]
         (if-let [es (s/index-of param \=)]
           (let [k (subs param 0 es)
                 v (subs param (inc es))]
             (assoc m (keyfn (decode k)) (decode v)))
           (assoc m (keyfn (decode param)) true)))
       {}
       (s/split query #"&"))
      {(keyfn (decode query)) true})))
