(ns jelly.people
  (:require [jelly.core :as c]
            [clojure.data.json :as json]
            [clojure.java.io :as io]))


(defn candidate [b-data]
  (map-indexed (fn [i, cnd] (assoc cnd :id i))
               (reduce (fn [sts row]
                         (conj sts {:name (:candidate row)})) #{} b-data)))

(def candidates (candidate c/base-data))

(def candidates-by-name (c/create-idx candidates :name))

(def candiates-by-id (c/create-idx candidates :id))

(defn create-files []
  (map (fn [a]
         (let [file-name (str "./data/people/" (:id a))]
           (io/make-parents file-name)
           (spit file-name (json/write-str a))))
       candidates))