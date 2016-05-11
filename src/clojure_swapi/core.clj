(ns clojure-swapi.core
  (:require [clojure-swapi.io.client :as swapi])
  (:require [clojure-swapi.domain.api :as core])
  (:gen-class))


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
        (map #(get % "name") (map swapi/mget-json species))))))

(defn ceil-division
  [numerator denominator]
  (Math/ceil (/ numerator denominator)))


(defn -main
  [& args]

  (def identification-list '(1, 2, 5, 5, 62, 71, 72))
  (def allience-tank (swapi/vehicle "72"))
  (def people (map #(select-keys % ["name", "species", "mass"]) (map swapi/person identification-list)))
  (def updated-people-mass (map update-mass people))
  (def updated-people-species (map update-species people))
  (def by-species (core/people-by :species updated-people-species))

  (clojure.pprint/pprint "List by species with name, species and mass properties")
  (clojure.pprint/pprint by-species)
)
