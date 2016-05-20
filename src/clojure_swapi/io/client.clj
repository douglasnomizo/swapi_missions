(ns clojure-swapi.io.client
  (:require [clj-http.client :as client])
  (:require [clojure.data.json :as json]))

(def resources #{ "films", "people", "planets", "species", "starships", "vehicles" })
(def swapi "http://swapi.co/api/")
(def people_resource (str swapi "people/"))
(def species_resource (str swapi "species/"))
(def vehicles_resource (str swapi "vehicles/"))
(def http-get #(client/get %))

(defn get-json
  [url]
  (json/read-str (:body (http-get url))))

(def mget-json (memoize get-json))

(defn person
  [identifier]
  (mget-json (str people_resource identifier)))

(defn vehicle
  [identifier]
  (mget-json (str vehicles_resource identifier)))

(defn species
  [identifier]
  (mget-json (str species_resource identifier)))


; (clojure.pprint/pprint (map (fn [e] (json/read-str (:body @e))) (map #(http/get %) urls) ))
