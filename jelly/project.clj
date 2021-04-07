(defproject jelly "0.1.0-SNAPSHOT"
  :description "Builds the data for the donut competition"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/data.csv "1.0.0"]
                 [org.clojure/data.json "2.0.2"]]
  :repl-options {:init-ns jelly.core})
