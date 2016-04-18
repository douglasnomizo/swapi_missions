(ns clojure-swapi.core
  (:require [clj-http.client :as client])
  (:require [clojure.data.json :as json])
  (:gen-class))

(def swapi "http://swapi.co/api/")
(def people (str swapi "people/"))

(defn person 
  [identifier]
  (json/read-str (:body (client/get (str people identifier)))))

(def mperson (memoize person))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (mperson 1)))
