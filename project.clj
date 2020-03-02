(defproject todo-list "1.0.0.RELEASE-SNAPSHOT"
  :description "my first clojure app"
  :url "https://github.com/AndreasZinkl/clojure-todo-list-example"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [
                 [org.clojure/clojure "1.10.1"]
                 [ring "1.8.0"]
                 [compojure "1.6.1"]
                 ]
  :min-lein-version "2.0.0"
  :uberjar-name "todo-list.jar"
  :repl-options {:init-ns todo-list.core}
  :main todo-list.core
  :profiles {:dev
             {:main todo-list.core/-dev-main}})
  