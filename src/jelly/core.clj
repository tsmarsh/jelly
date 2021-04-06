(ns jelly.core
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]
            [clojure.data.json :as json]))

(defn csv-data->maps [csv-data]
  (map zipmap
       (->> (first csv-data)
            (map keyword)
            repeat)
       (rest csv-data)))

(def csv-data (with-open [reader (io/reader (io/resource "house.csv"))]
                (doall
                  (csv/read-csv reader))))

(def base-data (csv-data->maps csv-data))

(defn create-idx [coll idx]
  (reduce (fn [index item]
            (assoc index (idx item) item)) {} coll))


(defn find-by [kword id coll]
  (->> coll
       (filter #(= (kword %) id))
       first))

(defn find-by-id [id coll]
  (find-by :id id coll))


