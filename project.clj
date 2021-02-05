(defproject org.clojars.quoll/paramdecoder "0.1.0-SNAPSHOT"
  :description "URL parameter decoding for Clojure/ClojureScript"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.2"]
                 [org.clojure/clojurescript "1.10.773"]]
  :plugins [[lein-cljsbuild "1.1.7"]]
  :repl-options {:init-ns paramdecoder.core}
  :cljsbuild {
    :builds {
      :dev
      {:source-paths ["src"]
       :compiler {
         :output-to "out/paramdecoder/core.js"
         :optimizations :simple
         :pretty-print true}}
      :test
      {:source-paths ["src" "test"]
       :compiler {
         :output-to "out/paramdecoder/test_memory.js"
         :optimizations :simple
         :pretty-print true}}
      }
    :test-commands {
      "unit" ["node" "out/paramdecoder/test_memory.js"]}})
