(ns jelly.core
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]
            [jelly.db :as db]))

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


(defn find-by [kword id coll]
  (->> coll
       (filter #(= (kword %) id))
       first))


(db/build-db base-data)

