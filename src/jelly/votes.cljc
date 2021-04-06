(ns jelly.votes
  (:require [jelly.people :as p]
            [jelly.core :as c]
            [clojure.data.json :as json]
            [clojure.java.io :as io]))

(defn vote [b-data]
  (map-indexed (fn [i, row]
                 {
                  :id             i
                  :representative (:id (p/candidates-by-name (:candidate row)))
                  :votes          (Integer/parseInt (:candidatevotes row))
                  :district       [(:state_po row) (:district row)]
                  }) b-data))

(def votes (vote c/base-data))

(defn create-files []
  (map (fn [a]
         (let [file-name (str "./data/votes/" (:id a))]
           (io/make-parents file-name)
           (spit file-name (json/write-str a))))
       votes))
