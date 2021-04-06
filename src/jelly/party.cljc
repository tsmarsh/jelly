(ns jelly.party
  (:require [jelly.core :as c]
            [clojure.data.json :as json]
            [clojure.java.io :as io]))

(defn party [b-data]
  (map-indexed (fn [i, prt] (assoc prt :id i))
               (reduce (fn [sts row]
                         (conj sts {:name (:party row)})) #{} b-data)))

(def parties (party c/base-data))
(def parties-by-name (c/create-idx parties :name))

(defn create-files []
  (map (fn [a]
         (let [file-name (str "./data/parties/" (:id a))]
           (io/make-parents file-name)
           (spit file-name (json/write-str a))))
       parties))