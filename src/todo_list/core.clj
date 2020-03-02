(ns todo-list.core
  (:require [ring.adapter.jetty :as webserver]
            [ring.middleware.reload :refer [wrap-reload]]
            [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [not-found]]))

(def operands {"+" + "-" - "*" * ":" /})

(defn welcome
  "a ring handler to process all requests sent to the webapp"
  [request]
  (if (= "/" (:uri request))
    {:status 200
     :headers {}
     :body  "<h1>Hello, Clojure World2</h1>
        <p>Welcome to your first Clojure app, I now update automatically!</p>
        <p>I now use defroutes to manage incoming requests.</p>"
     }
  )
)

(defn goodbye
  "A song to wish you goodbye"
  [request]
  {:status 200
   :headers {}
   :body "<h1>Walking back to happiness</h1>
          <p>Walking back to happiness with you</p>
          <p>Said, Farewell to loneliness I knew</p>
          <p>Laid aside foolish pride</p>
          <p>Learnt the truth from tears I cried</p>"}
)

(defn about
  "Information about the website developer"
  [request]
  {:status 200
   :headers {}
   :body "I am an awesome Clojure developer, well getting there..."}
  )

(defn request-info
  "View the information contained in the request, useful for debugging"
  [request]
  {:status 200
   :headers {}
   :body (pr-str request)}
  )

(defn hello
  "A simple personalised greeting showing the use of variable path elements"
  [request]
  (let [name (get-in request [:route-params :name])]
    {:status 200
     :headers {}
     :body (str "Hello " name ". I got your name from the web URL")
    }))

(defn calculator
  "A very simple calculator that can add, divide, subtract and multiply"
  [request]
  (let [a (Integer. (get-in request [:route-params :a]))
        b (Integer. (get-in request [:route-params :b]))
        op (get-in request [:route-params :op])
        f (get operands op)]
    
  (if f
    {:status 200
     :headers {}
     :body (str "Calculated result: " (f a b))}
    {:status 404
     :headers {}
     :body "Sorry, unknown operator. I only recognise + - * : (: is for division)"
     }
    ))
  )

(defroutes app
  (GET "/" [] welcome)
  (GET "/goodbye" [] goodbye)
  (GET "/about" [] about)
  (GET "/request-info" [] request-info)
  (GET "/hello/:name" [] hello)
  (GET "/calculator/:op/:a/:b" [] calculator)
  (not-found "<h1>This is not the page you are looking for!</h1>
    <p>Sorry, the page you requested was not found!</p>"))

(defn -main
  "simple web server using Ring & Jetty"
  [port-number]
  (webserver/run-jetty
   app
   {:port  (Integer. port-number)
    :join? false}))

(defn -dev-main
  "A very simple web server using Ring & Jetty,
   called via dthe development profile of Leiningen
   which reloads code changes using ring middleware wrap-reload"
  [port-number]
  (webserver/run-jetty
   (wrap-reload #'app)
   {:port (Integer. port-number)
    :join? false})
)