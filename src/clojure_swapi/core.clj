(ns clojure-swapi.core
  (:require [clj-http.client :as client])
  (:require [clojure.data.json :as json])
  (:gen-class))

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

(defn by
  [type collection]
  (group-by #(get % (name type)) collection))

(defn people-by
  [type people]
  (comp (by type people)))


(defn keywords
  [my-map]
  (into {}
  (for [[k v] my-map]
    [(keyword k) v])))

(defn unknown-as-100
  [mass]
  (if (= mass "unknown") 100 (read-string mass)))

(defn update-mass
  [person]
  (update person "mass" unknown-as-100))

(defn update-species
  [person]
  (update person "species"
    (fn [species]
      (vec
        (map #(get % "name") (map mget-json species))))))

(defn ceil-division
  [numerator denominator]
  (Math/ceil (/ numerator denominator)))


(defn -main
  [& args]

  (def identification-list '(1, 2, 5, 5, 62, 71, 72))
  (def allience-tank (vehicle "72"))
  (def people (map #(select-keys % ["name", "species", "mass"]) (map person identification-list)))
  (def updated-people-mass (map update-mass people))
  (def updated-people-species (map update-species people))
  (def by-species (people-by :species updated-people-species))

  (clojure.pprint/pprint "List by species with name, species and mass properties")
  (clojure.pprint/pprint by-species)
)
