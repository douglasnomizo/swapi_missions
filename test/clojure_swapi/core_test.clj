(ns clojure-swapi.core-test
  (:require [clojure.test :refer :all]
            [clojure-swapi.core :refer :all]))

(def people-list '(1,42,5,5,1,45,33,50,55,66,67,68,80))

(def get-fn #( {} ))

(deftest split-by-species
    (is (= () (people-by :species people-list))))
