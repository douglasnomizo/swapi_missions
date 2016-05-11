(ns clojure-swapi.domain.api)

(defn by
  [type collection]
  (group-by #(get % (name type)) collection))

(defn people-by
  [type people]
  (comp (by type people)))
