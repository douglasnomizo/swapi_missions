(ns clojure-swapi.core
  (:require [clj-http.client :as client])
  (:require [clojure.data.json :as json])
  (:gen-class))

(def swapi "http://swapi.co/api/")
(def people (str swapi "people/"))
(def vehicles (str swapi "vehicles/"))

(defn get-json
  [url]
  (json/read-str (:body (client/get url))))

(defn person 
  [identifier]
  (get-json (str people identifier)))

(def mperson (memoize person))

(defn by
  [type collection]
  (group-by #(get % (name type)) collection))

(defn people-by
  [type identification-list]
  (comp (by type (map mperson identification-list))))

(defn vehicle
  [identifier]
  (get-json (str vehicles identifier)))

(defn -main
  [& args]
  (def identification-list '(1,2,5))
  (clojure.pprint/pprint (people-by :species identification-list)))
