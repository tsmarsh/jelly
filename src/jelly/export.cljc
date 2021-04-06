(ns jelly.export
  (:require [clojure.java.io :as io]
            [clojure.data.json :as json]))

(defn to_id [kw]
  (let [nspace (namespace kw)
        prefix (if (= nspace "jelly")
                 ""
                 (str nspace "_"))]
    (str prefix (name kw))))

(defn create-files [fname data]
  (do
    (map (fn [a]
           (let [file-name (str fname (:jelly/id a))]
             (io/make-parents file-name)
             (spit file-name (json/write-str a :key-fn to_id))
             file-name))
         data)))

(defn create-district-files [data]
  (map (fn [a]
         (let [file-name (str "./data/state/" (get-in a [:jelly/state :state/po]) "/district/" (:jelly/id a))]
           (io/make-parents file-name)
           (spit file-name (json/write-str a :key-fn to_id))
           file-name))
       data))


(defn export [db]
  (let [data [{:fname "./data/affiliations/" :data (:db/affiliations db)}
              {:fname "./data/parties/" :data (:db/parties db)}
              {:fname "./data/candidates/" :data (:db/candidates db)}
              {:fname "./data/votes/" :data (:db/votes db)}]]

    (map (fn [{fname :fname d :data}] (create-files fname d)) data)
    #_(create-district-files (:db/districts db))))