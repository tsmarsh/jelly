(ns jelly.affiliation
  (:require [jelly.core :as c]
            [jelly.people :as p]
            [jelly.party :as d]
            [clojure.data.json :as json]
            [clojure.java.io :as io]))


(defn affilation [b-data]
  (map-indexed (fn [i, prt] (assoc prt :id i))
               (reduce (fn [aff row] (conj aff {:candidate (:id (p/candidates-by-name (:candidate row)))
                                                :party     (:id (d/parties-by-name (:party row)))
                                                :year      (Integer/parseInt (:year row))}))
                       #{} b-data)))

(def affliations (affilation c/base-data))

(def affliations-by-year-and-name
  (reduce (fn [index aff]
            (assoc index [(:year aff) (:name (p/candiates-by-id (:candidate aff)))] aff))
          {}
          affliations))


(defn create-files []
  (map (fn [a]
         (let [file-name (str "./data/affliations/" (:id a))]
           (io/make-parents file-name)
           (spit file-name (json/write-str a))))
       affliations))

